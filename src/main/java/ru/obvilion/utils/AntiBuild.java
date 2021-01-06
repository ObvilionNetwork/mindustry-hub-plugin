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
        final boolean canBuild = player.admin && Config.getBoolean("adminCanBuild");

        if (action.block != null || action.rotation != -1) {
            return canBuild;
        }

        return false;
    }
}
