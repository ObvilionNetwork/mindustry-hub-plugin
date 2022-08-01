package ru.obvilion.effects;

import arc.files.Fi;
import arc.graphics.Color;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Timer;
import arc.util.io.PropertiesUtils;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Player;

import ru.obvilion.HubPlugin;
import ru.obvilion.utils.ResourceUtil;
import java.lang.reflect.Field;

public class EffectHelper {
    public static Seq<EffectObject> effects;
    public static ObjectMap<String, String> properties;

    public static Seq<Timer.Task> tasks = new Seq<>();
    public static Seq<String> disabled_effects = new Seq<>();

    public static void init() {
        effects = new Seq<>();

        disabled_effects = new Seq<>();

        tasks.forEach(Timer.Task::cancel);
        tasks = new Seq<>();

        final Fi effect = HubPlugin.pluginDir.child("effects.properties");
        if (!effect.exists()) ResourceUtil.copy("effects.properties", effect);

        properties = new ObjectMap<>();
        PropertiesUtils.load(
                properties, HubPlugin.pluginDir.child("effects.properties").reader()
        );

        final int size = properties.size / 4;
        if (size == 0) return;

        for (int i = 1; i < size + 1; i++) {
            if (!properties.containsKey(i + ".name")) {
                break;
            }

            final String name = properties.get(i + ".name", ""),
                    position = properties.get(i + ".position", "0,0"),
                    color = properties.get(i + ".color", "#ffffff");

            final int rotation = Integer.parseInt(
                properties.get(i + ".rotation", "0")
            );
            final int delay = Integer.parseInt(
                properties.get(i + ".delay", "2000")
            );

            final String[] positions = position.split(",");
            final float x = Float.parseFloat(positions[0]);
            final float y = Float.parseFloat(positions[1]);

            final Effect eff = getEffect(name);
            final EffectObject place = new EffectObject(eff, x, y, rotation, Color.valueOf(color));

            if (eff != null) {
                effects.add(place);
            } else {
                Log.info("Effect @ in @.name not found!", name, i);
            }

            final Timer.Task task = Timer.schedule(place::draw, 0,(float) delay / 1000);
            tasks.add(task);
        }

        Log.info("Loaded @ map effects.", effects.size);
    }

    public static void onJoin(Player player) {
        on("onJoin", player, properties.get("onJoin.position"));
    }

    public static void onLeave(Player player) {
        on("onLeave", player);
    }

    public static void onMove(Player player) {
        final String position = (player.x / 8) + "," + (player.y / 8);
        on("onMove", player, position);
    }

    public static void on(String key, Player player, String position) {
        final String[] positions = position.split(",");
        final float x = Float.parseFloat(positions[0]);
        final float y = Float.parseFloat(positions[1]);

        on(key, x, y);
    }

    public static void on(String key, Player player) {
        final int x = (int) (player.x / 8);
        final int y = (int) (player.y / 8);

        on(key, x, y);
    }

    public static void on(String key, float x, float y) {
        final String name = properties.get(key + ".name", "none");
        if (name.equals("none")) return;

        final String color = properties.get(key + ".color", "#ffffff");
        final int rotation = Integer.parseInt(
            properties.get(key + ".rotation", "0")
        );

        final Effect eff = getEffect(name);
        if (eff == null) {
            if (!disabled_effects.contains(key)) {
                Log.warn("Effect with name @ not found!", name);

                disabled_effects.add(key);
            }

            return;
        }

        final EffectObject place = new EffectObject(eff, x, y, rotation, Color.valueOf(color));

        place.draw();
    }

    public static Effect getEffect(String name) {
        Effect eff = null;
        for (Field field : Fx.class.getFields()) {
            try {
                final String effName = field.getName();
                if (!effName.equals(name)) continue;

                eff = (Effect) field.get(null);
            } catch (Exception ignored) {

            }
        }

        return eff;
    }
}
