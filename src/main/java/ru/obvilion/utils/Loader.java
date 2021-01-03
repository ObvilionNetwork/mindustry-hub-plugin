package ru.obvilion.utils;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.gen.Call;
import mindustry.gen.Player;

import ru.obvilion.HubPlugin;
import ru.obvilion.config.Config;
import ru.obvilion.config.Lang;
import ru.obvilion.events.EventHelper;
import ru.obvilion.events.PlayerMoveEvent;
import ru.obvilion.servers.Server;
import ru.obvilion.servers.ServerHelper;

public class Loader {
    public static void init() {
        HubPlugin.config = new Config();
        HubPlugin.config.init();

        HubPlugin.pluginDir.child("lang").mkdirs();
        HubPlugin.lang = new Lang(
            HubPlugin.config.get("language")
        );

        for (String lang : Lang.langList) {
            new Lang(lang).init();
        }

        initEvents();

        ServerHelper.init();
        AntiBuild.init();
        EventHelper.init();
    }

    public static void initEvents() {
        Events.on(EventType.PlayEvent.class, event -> {
            Vars.state.rules.waves = false;
            Vars.state.rules.revealedBlocks.addAll(
                Blocks.launchPad, Blocks.launchPadLarge, Blocks.interplanetaryAccelerator,
                Blocks.resupplyPoint, Blocks.illuminator, Blocks.scrapWall,
                Blocks.scrapWallGigantic, Blocks.scrapWallHuge, Blocks.scrapWallLarge
            );
        });

        Events.on(EventType.PlayerJoin.class, event -> {
            final Player player = event.player;
            final Rules rules = Vars.state.rules.copy();

            if (!player.admin) {
                rules.bannedBlocks.addAll(Vars.content.blocks());
            }

            Call.setRules(player.con, rules);
        });

        Events.on(PlayerMoveEvent.class, event -> {
            final Player player = event.player;
            final boolean inPortal = ServerHelper.checkAll(
                (int) player.x / 8,
                (int) player.y / 8
            );

            if (inPortal) Log.info("hello");
        });
    }
}
