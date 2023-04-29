package ru.obvilion.utils;

import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.net.Administration.PlayerAction;
import ru.obvilion.config.Config;

public class AntiBuild {
    public static void init() {
        Vars.netServer.admins.addActionFilter(AntiBuild::onAction);
    }

    public static boolean onAction(PlayerAction action) {
        final Player player = action.player;

        switch (Config.get("canBuild")) {
            case "player":
                return true;
            case "admin":
                return player.admin;
            default:
                return false;
        }
    }
}
