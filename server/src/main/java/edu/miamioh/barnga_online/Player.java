package edu.miamioh.barnga_online;

/**
 *  Container class for player information.
 *
 * @author Naoki Mizuno
 */
public class Player {
    /* Hash code of the MessagePlayerName#playerName */
    public int id;
    public int teamId;
    /* Current coordinate */
    public Coordinates coord;

    public Player() {
    }

    public Player(int id, int teamId, Coordinates coord) {
        this.id = id;
        this.teamId = teamId;
        this.coord = coord;
    }

    /* Copy constructor */
    public Player(Player other) {
        this.id = other.id;
        this.teamId = other.teamId;
        this.coord = new Coordinates(other.coord);
    }
}
