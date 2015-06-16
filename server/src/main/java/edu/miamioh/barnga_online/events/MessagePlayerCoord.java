package edu.miamioh.barnga_online.events;

import edu.miamioh.barnga_online.Player;
import edu.miamioh.barnga_online.Coordinates;

/**
 * Container class for representing a player on the field.
 *
 * @author Naoki Mizuno
 */
public class MessagePlayerCoord {
    public MessagePlayerId player;
    public Coordinates newCoord;

    public MessagePlayerCoord() {
    }

    public MessagePlayerCoord(Player player, Coordinates newCoord) {
        this.player = new MessagePlayerId(player);
        this.newCoord = newCoord;
    }
}
