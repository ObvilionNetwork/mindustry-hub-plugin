package ru.obvilion;

import arc.Events;
import arc.util.*;

import mindustry.Vars;
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

        Events.on(PlayerMoveEvent.class, event -> {
            Log.info(event);
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
