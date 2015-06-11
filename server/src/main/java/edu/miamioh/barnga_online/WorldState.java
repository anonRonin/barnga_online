package edu.miamioh.barnga_online;

import java.util.HashMap;

public class WorldState {
    /* teamId => Team, which is a HashSet of Player */
    protected HashMap<Integer, Team<Player>> teams;
    /* foodId => Food */
    protected HashMap<Integer, Food> foods;
    /* teamId => Points */
    protected HashMap<Integer, Points> points;

    protected int worldSizeX, worldSizeY;
    /* Size of the field viewable to the players on the client side */
    protected int viewSizeX, viewSizeY;

    protected boolean gameStarted;
    private int maxId = 0;

    /**
     * Default constructor.
     */
    public WorldState() {
        teams = new HashMap<Integer, Team<Player>>();
        foods = new HashMap<Integer, Food>();
        points = new HashMap<Integer, Points>();
        gameStarted = false;
    }

    /**
     * Returns the dictionary of all teams.
     *
     * @return Dictionary (key: team ID, value: HashSet of Player)
     */
    public HashMap<Integer, Team<Player>> getTeams() {
        return teams;
    }

    /**
     * Returns the corresponding team object for the given ID.
     *
     * @return the Team object. null if team doesn't exist.
     */
    public Team<Player> getTeam(int teamId) {
        return teams.get(teamId);
    }

    /**
     * Returns the dictionary of all players.
     *
     * The keys are the player's ID, and the values are the Player object.
     *
     * @return Dictionary (key: player ID, value: Player) of all players
     */
    public HashMap<Integer, Player> getPlayers() {
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for (Team<Player> t : teams.values()) {
            for (Player p : t) {
                players.put(p.id, p);
            }
        }
        return players;
    }

    /**
     * Returns the dictionary of all foods.
     *
     * The keys are the food's ID, and the values are the Food object.
     *
     * @return Dictionary (key: food ID, valu: Food) of all food
     */
    public HashMap<Integer, Food> getFoods() {
        return foods;
    }

    /**
     * Returns the dictionary of points of all teams.
     *
     * The keys are the team's ID, and the values are the total points for
     * that team.
     *
     * @return Dictionary (key: team ID, value: total team point) of all teams
     */
    public HashMap<Integer, Points> getPoints() {
        return points;
    }

    /**
     * Adds a new player to the world.
     *
     * This method will update the collection of teams if the given teamId
     * does not exist in the current collection of teams. Therefore, there is
     * no need for teams to be explicitly added.
     *
     * @param player the new player to be added
     *
     * @param teamId the team that the player belongs to
     */
    public void addPlayer(Player player, int teamId) {
        if (!teams.containsKey(teamId)) {
            teams.put(teamId, new Team<Player>(teamId));
        }

        Team<Player> playerTeam = teams.get(teamId);
        playerTeam.add(player);
    }

    /**
     * Adds a new food to the world.
     *
     * @param food the new food to be added
     */
    public void addFood(Food food) {
        foods.put(food.id, food);
    }

    /**
     * Removes a player from the world.
     *
     * This method does nothing when the given user is not found in the world.
     *
     * @param player the player to be removed
     */
    public void removePlayer(Player player) {
        int teamId = player.teamId;
        teams.get(teamId).remove(player);
    }

    /**
     * Removes a food from the world.
     *
     * This method does nothing when the given food is not found in the world.
     *
     * @param food the food to be removed
     */
    public void removeFood(Food food) {
        foods.remove(food.id);
    }

    /**
     * Adds point earned by a player to its team.
     *
     * @param player the player who earned the points
     *
     * @param amount the amount of points to be added
     */
    public void addPointsEarnedBy(Player player, int amount) {
        int teamId = player.teamId;

        // Initialize if it's first time adding points
        if (!points.containsKey(teamId)) {
            points.put(teamId, new Points());
        }

        Points teamPoints = points.get(teamId);
        teamPoints.addPoints(player, amount);
    }

    /**
     * Subtracts point lost by a player from its team.
     *
     * This method does not check whether the resulting point is going to be
     * negative.
     *
     * @param player the player who lost the points
     *
     * @param amount the amount of points to be subtracted
     */
    public void subtractPointsLostBy(Player player, int amount) {
        addPointsEarnedBy(player, -amount);
    }

    public int getWorldSizeX() {
        return worldSizeX;
    }

    public int getWorldSizeY() {
        return worldSizeY;
    }

    public void setWorldSizeX(int worldSizeX) {
        this.worldSizeX = worldSizeX;
    }

    public void setWorldSizeY(int worldSizeY) {
        this.worldSizeY = worldSizeY;
    }

    public int getViewSizeX() {
        return viewSizeX;
    }

    public int getViewSizeY() {
        return viewSizeY;
    }

    public void setViewSizeX(int viewSizeX) {
        this.viewSizeX = viewSizeX;
    }

    public void setViewSizeY(int viewSizeY) {
        this.viewSizeY = viewSizeY;
    }

    /**
     * Returns whether there *actually* is a player at a coordinate.
     *
     * Note that this method does not care about the visibility of the player.
     *
     * @return the player at the given coordinate. null if no player exists.
     */
    public Player playerAt(Coordinates coord) {
        for (Player p : getPlayers().values()) {
            if (p.coord.equals(coord)) {
                return p;
            }
        }

        return null;
    }

    /**
     * Returns whether there *actually* is a food at a coordinate.
     *
     * Note that this method does not care about the visibility of the food.
     *
     * @return the food at the given coordinate. null if no food exists.
     */
    public Food foodAt(Coordinates coord) {
        for (Food f : foods.values()) {
            if (f.coord.equals(coord)) {
                return f;
            }
        }

        return null;
    }

    /**
     * Returns a fresh ID.
     *
     * @return an int that can be used as an ID
     */
    public int getId() {
        int ret = maxId;
        maxId++;
        return ret;
    }

    /**
     * Returns whether the game is started in the world.
     */
    public boolean isGameStarted() {
        return gameStarted;
    }
}
