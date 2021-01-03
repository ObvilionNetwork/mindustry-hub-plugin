package ru.obvilion.events;

import mindustry.gen.Groups;
import mindustry.gen.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerMoveEvent {
    public static List<String> players = new ArrayList<>();
    public static List<Integer> playersX = new ArrayList<>();
    public static List<Integer> playersY = new ArrayList<>();

    public final Player player;
    public final int oldX;
    public final int oldY;

    public PlayerMoveEvent(int oldX, int oldY, Player player) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.player = player;
    }

    public static boolean check(Player player) {
        final int id = find(player);
        if (id == -1) return false;

        return playersX.get(id) != (int) player.x || playersY.get(id) != (int) player.y;
    }

    public static int find(Player player) {
        return players.indexOf(player.uuid());
    }

    public static int getPlayerX(Player player) {
        final int id = find(player);
        if (id == -1) return (int) player.x;

        return playersX.get(id);
    }

    public static int getPlayerY(Player player) {
        final int id = find(player);
        if (id == -1) return (int) player.y;

        return playersY.get(id);
    }

    public static void update() {
        players = new ArrayList<>();
        playersX = new ArrayList<>();
        playersY = new ArrayList<>();

        for(Player player : Groups.player) {
            players.add(player.uuid());

            playersX.add((int) player.x);
            playersY.add((int) player.y);
        }
    }
}
