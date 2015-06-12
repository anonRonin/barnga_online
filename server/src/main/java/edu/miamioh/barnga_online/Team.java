package edu.miamioh.barnga_online;

import java.util.HashSet;

/**
 * Class that represents a team.
 *
 * A Team is a collection of Player.
 *
 * @author Naoki MIzuno
 */
@SuppressWarnings("hiding")
public class Team<Player> extends HashSet<Player> {
    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = -7556585430178027176L;
    protected int teamId;
    /* Players that belong to this team */
    protected HashSet<Player> players;

    public Team() {
        players = new HashSet<Player>();
    }

    public Team(int teamId) {
        this();
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Checks whether the given Object is the same as this team.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Team)) {
            return false;
        }

        @SuppressWarnings("unchecked")
        Team<Player> other = (Team<Player>)obj;
        return this.teamId == other.teamId;
    }
}
