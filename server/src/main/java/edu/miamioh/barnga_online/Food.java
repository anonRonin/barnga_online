package edu.miamioh.barnga_online;

/**
 * Container class for representing foods on the field.
 *
 * @author Naoki Mizuno
 */
public class Food {
    public int id;
    /* What team it belongs to */
    public int team;
    /* Current coordinates */
    public Coordinates coord;

    public Food() {
    }

    public Food(int id, int team, Coordinates coord) {
        this.id = id;
        this.team = team;
        this.coord = coord;
    }

    /* Copy constructor */
    public Food(Food other) {
        this.id = other.id;
        this.team = other.team;
        this.coord = new Coordinates(other.coord);
    }
}
