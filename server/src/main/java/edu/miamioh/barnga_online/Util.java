package edu.miamioh.barnga_online;

import java.util.HashSet;

import edu.miamioh.barnga_online.events.MessagePlayerCoord;

/**
 * A bunch of utility methods.
 *
 * @author Naoki Mizuno
 */

public class Util {
    protected WorldState world;
    protected BarngaOnlineConfigsDefault configs;

    /**
     * Prints out the given string if Constants.DEBUG is true.
     *
     * @param str String to print out
     */
    public static void debug(String str) {
        if (Constants.DEBUG) {
            System.out.println(str);
        }
    }

    /**
     * Prints out the given string in the also given format if Constants.DEBUG
     * is true.
     *
     * The format is the same as printf.
     *
     * @param fmt the format of the string
     *
     * @param args the arguments
     */
    public static void debug(String fmt, Object... args) {
        if (Constants.DEBUG) {
            System.out.printf(fmt, args);
        }
    }

    public Util(WorldState world, BarngaOnlineConfigsDefault configs) {
        this.world = world;
        this.configs = configs;
    }

    /**
     * Returns whether the given food is visible to the also given player.
     *
     * @param player the Player looking at the food
     *
     * @param food
     *
     * @return true if player can see the food, false otherwise
     */
    public boolean foodVisible(Player player, Food food) {
        return foodVisible(player.teamId, food);
    }

    /**
     * Returns whether the given food is visible to the given team.
     *
     * @param teamId the team looking at the food
     *
     * @param food
     *
     * @return true if team can see the food, false otherwise
     */
    public boolean foodVisible(int teamId, Food food) {
        Team<Player> targetTeam = world.getTeam(teamId);
        HashSet<Team<Player>> visibleTeams = configs.foodVisibleTeams(food);
        return visibleTeams.contains(targetTeam);
    }

    /**
     * Returns what the food looks like to the given player.
     *
     * @param player the player looking at the food
     *
     * @param food
     *
     * @return the team ID of the team that the food looks like to the player
     */
    public int foodVisibleAs(Player player, Food food) {
        return foodVisibleAs(player.teamId, food);
    }

    /**
     * Returns what the food looks like to the given team.
     *
     * @param team the team looking at the food
     *
     * @param food
     *
     * @return the team ID of the team that the food looks like to the team
     */
    public int foodVisibleAs(Team<Player> team, Food food) {
        return foodVisibleAs(team.iterator().next().teamId, food);
    }

    /**
     * Returns how the food looks like to the given team.
     *
     * BarngaOnlineConfigsDefault.I (== -1) is returned when food is invisible
     * to the given teamId.
     *
     * @param teamId the team that's looking at the food
     *
     * @param food the food that's being looked at
     *
     * @return what the food looks like to teamId
     */
    public int foodVisibleAs(int teamId, Food food) {
        // Useless variable assignment to conform with the comments in
        // BarngaOnlineConfigsDefault class
        int self = teamId;
        int other = food.team;

        return configs.getFoodVisibility()[self][other];
    }

    /**
     * Returns whether one can see two.
     */
    public boolean playerVisible(Player one, Player two) {
        return playerVisible(one.teamId, two);
    }

    /**
     * Returns whether players that belong to teamId can see `player'.
     */
    public boolean playerVisible(int teamId, Player player) {
        Team<Player> targetTeam = world.getTeam(teamId);
        HashSet<Team<Player>> visibleTeams = configs.playerVisibleTeams(player);
        return visibleTeams.contains(targetTeam);
    }

    /**
     * Returns whether players that belong to team can see `player'.
     */
    public boolean playerVisible(Team<Player> team, Player player) {
        return playerVisible(team.getTeamId(), player);
    }

    public int playerVisibleAs(Player one, Player two) {
        return playerVisibleAs(one.teamId, two);
    }

    /**
     * Returns how a player looks like to the given team.
     *
     * BarngaOnlineConfigsDefault.I (== -1) is returned when player is
     * invisible to the given teamId.
     *
     * @param teamId the team that's looking at the player
     *
     * @param player the player that's being looked at
     *
     * @return what the player looks like to teamId
     */
    public int playerVisibleAs(int teamId, Player player) {
        // Useless variable assignment to conform with the comments in
        // BarngaOnlineConfigsDefault class
        int self = teamId;
        int other = player.teamId;

        return configs.getPlayerVisibility()[self][other];
    }

    /**
     * Returns how a player looks like to the given team.
     *
     * BarngaOnlineConfigsDefault.INVISIBLE (== -1) is returned when player is
     * invisible to the given teamId.
     *
     * @param team the team that's looking at the player
     *
     * @param player the player that's being looked at
     *
     * @return what the player looks like to the team
     */
    public int playerVisibleAs(Team<Player> team, Player player) {
        return playerVisibleAs(team.getTeamId(), player);
    }

    /**
     * Creates a fake message with the team ID that could be different from
     * the actual team ID.
     *
     * @param team the team that will be getting the message
     *
     * @param player the player that the message is about
     */
    public MessagePlayerCoord makeFakePlayerMessage(Team<Player> team,
            Player player, Coordinates newCoord) {
        return makeFakePlayerMessage(team.getTeamId(), player, newCoord);
    }

    /**
     * Creates a fake message for `one' where the team ID may be different
     * from the actual team that `two' belongs to.
     *
     * @param one the player that will be getting the message
     *
     * @param two the player that the message is about
     */
    public MessagePlayerCoord makeFakePlayerMessage(Player one,
            Player two, Coordinates newCoord) {
        return makeFakePlayerMessage(one.teamId, two, newCoord);
    }

    public MessagePlayerCoord makeFakePlayerMessage(int teamId,
            Player player, Coordinates newCoord) {
        Player fakePlayer = new Player(player);

        Util util = new Util(world, configs);
        fakePlayer.teamId = util.playerVisibleAs(teamId, player);

        MessagePlayerCoord mes =
            new MessagePlayerCoord(fakePlayer, new Coordinates(newCoord));

        return mes;
    }
}
