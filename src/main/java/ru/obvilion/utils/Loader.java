package ru.obvilion.utils;

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
    }
}
