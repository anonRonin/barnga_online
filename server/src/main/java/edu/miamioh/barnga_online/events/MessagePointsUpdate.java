package edu.miamioh.barnga_online.events;

import edu.miamioh.barnga_online.Player;

/**
 * Container class used when sending points update to client.
 *
 * TODO: The data type being sent may change
 *
 * @author Naoki Mizuno
 */
public class MessagePointsUpdate {
    public MessagePlayerId player;
    public int newPoint;

    public MessagePointsUpdate() {
    }

    public MessagePointsUpdate(Player player, int newPoint) {
        this.player = new MessagePlayerId(player);
        this.newPoint = newPoint;
    }
}
