package ru.obvilion;

import arc.Events;
import arc.util.*;

import mindustry.mod.*;
import mindustry.game.EventType.*;

import ru.obvilion.utils.Config;
import java.io.File;

public class HubPlugin extends Plugin {
    public static final File pluginDir = new File("config/mods/ObvilionHub");
    public static final Config config = new Config();

    @Override
    public void init() {
        config.init();

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
