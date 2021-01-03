package ru.obvilion.events;

import arc.Events;
import mindustry.game.EventType;
import mindustry.gen.Groups;
import mindustry.gen.Player;

public class EventHelper {
    public static void init() {
        Events.run(EventType.Trigger.update, EventHelper::tick);
    }

    public static void tick() {
        for(Player player : Groups.player) {
            if (PlayerMoveEvent.check(player)) {
                final int oldX = PlayerMoveEvent.getPlayerX(player);
                final int oldY = PlayerMoveEvent.getPlayerY(player);

                Events.fire(new PlayerMoveEvent(oldX, oldY, player));
            }
        }

        PlayerMoveEvent.update();
    }
}
