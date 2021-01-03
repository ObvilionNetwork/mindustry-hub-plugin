package ru.obvilion.utils;

import arc.Events;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.gen.Call;
import mindustry.gen.Player;

import ru.obvilion.HubPlugin;
import ru.obvilion.config.Config;
import ru.obvilion.config.Lang;

import java.io.File;

public class Loader {
    public static void init() {
        HubPlugin.config = new Config();
        HubPlugin.config.init();

        new File(HubPlugin.pluginDir, "lang").mkdir();
        HubPlugin.lang = new Lang(
            HubPlugin.config.get("language")
        );

        for (String lang : Lang.langList) {
            new Lang(lang).init();
        }

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
    }
}
