package ru.obvilion.servers;

import arc.Core;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.gen.Call;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.logic.LExecutor;
import ru.obvilion.config.Lang;

import java.util.concurrent.atomic.AtomicInteger;

public class ServersPinger {
    public static AtomicInteger online = new AtomicInteger(0);

    public static void init() {
        Timer.schedule(() -> {
            online.set(0);
            update();

            Timer.schedule(() -> {
                Core.settings.put("totalPlayers", online.get());
            }, 3);
        }, 3, 10);
    }

    public static void update() {
        for (Server server : ServersHelper.servers) {
            if (server.notIncludeOnline) {
                continue;
            }

            final int x = (server.xPos * 8 + (server.block.size - 1) * 4);
            final int y = server.yPos * 8 - 8;

            Vars.net.pingHost(server.ip, server.port, host -> {
                online.addAndGet(host.players);

                if (host.playerLimit == 0) {
                    Call.label(Lang.get("server.online", host.players + ""), 10, x, y);
                } else {
                    Call.label(
                        Lang.get("server.online.limit", host.players + "", host.playerLimit + ""),
                        10, x, y
                    );
                }
            }, e -> {
                Call.label(Lang.get("server.offline"), 10, x, y);
            });
        }
        
        online.addAndGet(Groups.player.size());
    }
}
