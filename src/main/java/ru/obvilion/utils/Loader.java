package ru.obvilion.utils;

import arc.Events;
import arc.files.Fi;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Rules;
import mindustry.gen.Call;
import mindustry.gen.Player;

import ru.obvilion.config.Config;
import ru.obvilion.config.Lang;
import ru.obvilion.effects.EffectHelper;
import ru.obvilion.events.EventsHelper;
import ru.obvilion.events.PlayerMoveEvent;
import ru.obvilion.servers.Server;
import ru.obvilion.servers.ServersHelper;
import ru.obvilion.servers.ServersPinger;

public class Loader {
    public static boolean firstInit = true;

    public static void init() {
        Config.init();
        Lang.init();

        final Fi map = Vars.customMapDirectory.child("Hub.msav");
        if (!map.exists()) {
            ResourceUtil.copy("Hub.msav", map);
            Vars.maps.reload();
        }

        ServersHelper.init();
        AntiBuild.init();
        ServersPinger.init();
        EffectHelper.init();

        if (!Config.getBoolean("clickOnly")) {
            EventsHelper.init();
        }

        if (firstInit) {
            initEvents();
            firstInit = false;
        }
    }

    public static void initEvents() {
        Events.on(EventType.PlayEvent.class, event -> {
            Vars.state.rules.waves = false;

            Vars.state.rules.revealedBlocks.addAll(
                Blocks.launchPad, Blocks.interplanetaryAccelerator, Blocks.interplanetaryAccelerator,
                Blocks.illuminator, Blocks.scrapWall, Blocks.scrapWallGigantic, Blocks.scrapWallHuge,
                Blocks.scrapWallLarge
            );
        });

        Events.on(EventType.PlayerJoin.class, event -> {
            final Player player = event.player;
            final Rules rules = Vars.state.rules.copy();

            switch (Config.get("canBuild")) {
                case "player":
                    break;
                case "admin":
                    if (!player.admin) {
                        rules.bannedBlocks.addAll(Vars.content.blocks());
                    }
                    break;
                default:
                    rules.bannedBlocks.addAll(Vars.content.blocks());
                    break;
            }


            Call.setRules(player.con, rules);

            ServersPinger.update();
            ServersHelper.servers.forEach(server -> {
                final int x = (server.xPos * 8 + (server.block.size - 1) * 4);
                final int y = server.yPos * 8 + (server.block.size - 1) * 8 + 8;

                Call.label(server.name, 200000, x, y);
            });

            EffectHelper.onJoin(player);
        });

        Events.on(EventType.PlayerLeave.class, event -> {
            EffectHelper.onLeave(event.player);
        });

        Events.on(PlayerMoveEvent.class, event -> {
            final Player player = event.player;
            final Server portal = ServersHelper.checkAll(
                    (int) player.x / 8,
                    (int) player.y / 8
            );

            if (portal != null) {
                Call.connect(player.con, portal.ip, portal.port);
            }

            EffectHelper.onMove(player);
        });

        Events.on(EventType.TapEvent.class,event->{
            final Player player = event.player;
            final Server portal = ServersHelper.checkAll(
                    event.tile.x,
                    event.tile.y
            );

            if (portal != null) {
                Call.connect(player.con, portal.ip, portal.port);
            }
        });
    }
}
