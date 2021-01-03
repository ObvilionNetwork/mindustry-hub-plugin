package ru.obvilion.servers;

import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.io.PropertiesUtils;
import ru.obvilion.HubPlugin;

public class ServersHelper {
    public static Seq<Server> servers = new Seq<>();

    public static boolean checkAll(int x, int y) {
        boolean result = false;
        for (Server serv : servers) {
            final boolean ok = serv.check(x, y);

            if (ok) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static void init() {
        final ObjectMap<String, String> all = new ObjectMap<>();
        PropertiesUtils.load(
                all, HubPlugin.pluginDir.child("servers.properties").reader()
        );

        final int size = all.size / 4;
        if (size == 0) {
            Log.warn("Servers not found!");
            return;
        }

        for (int i = 1; i < size + 1; i++) {
            if (!all.containsKey(i + ".name")) {
                break;
            }

            final String name = all.get(i + ".name", "server-name"),
                    ip = all.get(i + ".ip", "localhost"),
                    block = all.get(i + ".block", "wall"),
                    position = all.get(i + ".position", "0,0");

            final int port = Integer.parseInt(
                    all.get(i + ".port", "6567")
            );

            final String[] positions = position.split(",");

            final int blockX = Integer.parseInt(positions[0]);
            final int blockY = Integer.parseInt(positions[1]);

            servers.add(
                    new Server(name, ip, port, block, blockX, blockY)
            );
        }
    }
}
