package ru.obvilion.servers;

import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.io.PropertiesUtils;
import ru.obvilion.HubPlugin;
import ru.obvilion.utils.ResourceUtil;

public class ServersHelper {
    public static Seq<Server> servers;

    public static Server checkAll(int x, int y) {
        Server result = null;
        for (Server serv : servers) {
            final boolean ok = serv.check(x, y);

            if (ok) {
                result = serv;
                break;
            }
        }

        return result;
    }

    public static void init() {
        servers = new Seq<>();

        final Fi serv = HubPlugin.pluginDir.child("servers.properties");
        if (!serv.exists()) ResourceUtil.copy("servers.properties", serv);

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
                    position = all.get(i + ".position", "0,0"),
                    notIncludeOnline = all.get(i + ".notIncludeOnline", "false");

            final int port = Integer.parseInt(
                    all.get(i + ".port", "6567")
            );

            final String[] positions = position.split(",");

            final int blockX = Integer.parseInt(positions[0]);
            final int blockY = Integer.parseInt(positions[1]);

            final boolean _notIncludeOnline = Boolean.parseBoolean(notIncludeOnline);

            servers.add(
                    new Server(name, ip, port, block, blockX, blockY, _notIncludeOnline)
            );
        }

        Log.info("Loaded @ server portals.", servers.size);
    }
}
