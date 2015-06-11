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
    public Player player;
    public int newPoint;
}
