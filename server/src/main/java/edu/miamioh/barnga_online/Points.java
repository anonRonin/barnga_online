package edu.miamioh.barnga_online;

import java.util.ArrayList;

/**
 * Class that has the information of who earned how many points.
 */
public class Points {
    /**
     * Class that has information about who earned how many points.
     */
    private class Earner {
        private Player player;
        private int amount;

        public Earner(Player player, int amount) {
            this.player = player;
            this.amount = amount;
        }
    }

    private int total;
    private ArrayList<Earner> earners;

    /**
     * Default constructor.
     */
    public Points() {
        earners = new ArrayList<Earner>();
    }

    public void addPoints(Player player, int amount) {
        earners.add(new Earner(player, amount));
        total += amount;
    }

    public void subtractPoints(Player player, int amount) {
        addPoints(player, -amount);
    }
}
