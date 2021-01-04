package ru.obvilion.utils;

import arc.files.Fi;
import arc.struct.ObjectMap;
import arc.util.io.PropertiesUtils;
import ru.obvilion.HubPlugin;

public class Lang {
    public static final String[] langList = { "en_US", "ru_RU" };
    public static final Fi langDir = HubPlugin.pluginDir.child("lang");

    public static String selectedLang;
    public static Fi file;

    private static ObjectMap<String, String> properties;

    public static void init() {
        generate();

        selectedLang = Config.get("lang");
        file = langDir.child(selectedLang + ".properties");

        properties = new ObjectMap<>();
        PropertiesUtils.load(
            properties, HubPlugin.pluginDir.child("servers.properties").reader()
        );
    }

    public static void generate() {
        for (String lang : langList) {
            final String langPath = "lang/" + lang + ".properties";
            final Fi file = HubPlugin.pluginDir.child(langPath);

            if (file.exists()) continue;
            ResourceUtil.copy(langPath, file);
        }
    }

    public static String get(String key, String... replace) {
        String value = properties.get(key);

        int i = 0;
        for (String to : replace) {
            value = value.replace("{" + i + "}", to);
            i++;
        }

        return value;
    }
}
