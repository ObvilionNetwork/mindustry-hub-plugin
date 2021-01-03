package ru.obvilion.utils;

import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Player;
import mindustry.net.Administration.PlayerAction;
import ru.obvilion.HubPlugin;

public class AntiBuild {
    public static boolean adminCanBuild = false;

    private AntiBuild() {
        throw new IllegalStateException("Utility class");
    }

    public static void init() {
        adminCanBuild = Boolean.parseBoolean(
            HubPlugin.config.get("adminCanBuild")
        );

        Vars.netServer.admins.addActionFilter(AntiBuild::onAction);
    }

    public static boolean onAction(PlayerAction action) {
        final Player player = action.player;
        final boolean canBuild = player.admin && adminCanBuild;

        if (action.block != null && !canBuild) {
            return false;
        }

        if (action.rotation != -1 && !canBuild) {
            return false;
        }

        if (action.config != null) {
            // TODO?
        }

        return true;
    }
}
