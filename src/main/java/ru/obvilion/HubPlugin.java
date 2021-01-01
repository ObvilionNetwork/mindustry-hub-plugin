package ru.obvilion;

import arc.Events;
import arc.util.*;

import mindustry.mod.*;
import mindustry.game.EventType.*;

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

        Events.on(WaveEvent.class, event -> {

        });

        Events.on(WorldLoadEvent.class, event -> {

        });

        Events.on(WithdrawEvent.class, event -> {

        });

        Events.on(DepositEvent.class, event -> {

        });

        Events.on(ConfigEvent.class, event -> {

        });

        Events.on(TapEvent.class, event -> {

        });
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {

    }

    @Override
    public void registerClientCommands(CommandHandler handler) {

    }
}
