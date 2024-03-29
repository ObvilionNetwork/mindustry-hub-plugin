package ru.obvilion;

import arc.files.Fi;
import arc.util.Log;
import arc.util.CommandHandler;
import mindustry.mod.Plugin;

import ru.obvilion.utils.Loader;

public class HubPlugin extends Plugin {
    public static final Fi pluginDir = new Fi("./config/mods/ObvilionHub");
    public static final String VERSION = "0.7.1";

    @Override
    public void init() {
        Loader.init();
    }

    @Override
    public void registerServerCommands(CommandHandler handler) {
        handler.register("hub", "[args...]", "ObvilionHub settings", arg -> {
            if (arg.length == 0) {
                Log.info("Too few command arguments. Usage:");
                Log.info("> hub version - Show version Obvilion Hub plugin.");
                Log.info("> hub reload - Reload all ObvilionHub config files");
                return;
            }

            if (arg[0].equals("version")) {
                Log.info("Obvilion Hub v@ by Fatonn", VERSION);
                Log.info("> Github link: https://github.com/ObvilionNetwork/mindustry-hub-plugin");
                Log.info("> Discord server link: https://discord.gg/cg82mjh");
                return;
            }

            if (arg[0].equals("reload")) {
                Loader.init();
                Log.info("Plugin ObvilionHub reloaded!");
                return;
            }

            Log.info("Command not found!");
        });
    }
}
