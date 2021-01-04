package ru.obvilion.utils;

import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.Log;
import arc.util.io.PropertiesUtils;
import ru.obvilion.HubPlugin;

public class Config {
    public static final String fileName = "config.propeties";
    public static final Fi file = HubPlugin.pluginDir.child(fileName);

    private static ObjectMap<String, String> config;

    public static void init() {
        if (!file.exists()) {
            HubPlugin.pluginDir.mkdirs();
            ResourceUtil.copy(fileName, file);

            Log.info("The config file for ObvilionHub was successfully generated.");
            Log.info("Configure it in " + file.path());
        }

        config = new ObjectMap<>();
        PropertiesUtils.load(
                config, HubPlugin.pluginDir.child("servers.properties").reader()
        );
    }

    public static String get(String key) {
        return config.get(key);
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
