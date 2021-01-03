package ru.obvilion;

import arc.Events;
import arc.util.*;

import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.gen.Call;
import mindustry.gen.Player;
import mindustry.mod.*;

import ru.obvilion.config.Config;
import ru.obvilion.config.Lang;
import ru.obvilion.events.PlayerMoveEvent;
import ru.obvilion.utils.Loader;

import java.io.File;

public class HubPlugin extends Plugin {
    public static final File pluginDir = new File("config/mods/ObvilionHub");

    public static Config config;
    public static Lang lang;

    @Override
    public void init() {
        Loader.init();

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

        });

        Vars.netServer.admins.addActionFilter(action -> {
            return true;
        });
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        // TODO
    }

    @Override
    public void registerClientCommands(CommandHandler handler) {
        // TODO
    }
}
