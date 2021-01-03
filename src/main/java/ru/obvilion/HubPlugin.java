package ru.obvilion;

import arc.util.*;
import mindustry.mod.*;

import ru.obvilion.config.Config;
import ru.obvilion.config.Lang;
import ru.obvilion.utils.Loader;

import java.io.File;

public class HubPlugin extends Plugin {
    public static final File pluginDir = new File("config/mods/ObvilionHub");

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
