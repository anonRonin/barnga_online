package edu.miamioh.barnga_online.events;

import edu.miamioh.barnga_online.Coordinates;
import edu.miamioh.barnga_online.Food;

/**
 * Container class for representing a player on the field.
 *
 * @author Naoki Mizuno
 */
public class MessageFoodCoord {
    public Food food;
    public Coordinates newCoord;
    public boolean gone;

    public MessageFoodCoord() {
    }

    public MessageFoodCoord(Food food, Coordinates newCoord, boolean gone) {
        this.food = food;
        this.newCoord = newCoord;
        this.gone = gone;
    }
}
