package ru.obvilion;

import arc.files.Fi;
import arc.util.*;
import mindustry.mod.*;

import ru.obvilion.config.Config;
import ru.obvilion.config.Lang;
import ru.obvilion.utils.Loader;

public class HubPlugin extends Plugin {
    public static final Fi pluginDir = new Fi("./config/mods/ObvilionHub");

    public static Config config;
    public static Lang lang;

    @Override
    public void init() {
        Loader.init();
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
