package edu.miamioh.barnga_online;

import java.util.HashSet;

/**
 * Default configuration for the game.
 *
 * @author Naoki Mizuno
 */
public class BarngaOnlineConfigsDefault implements BarngaOnlineConfigs {
    public static final int WORLD_X = 30;
    public static final int WORLD_Y = 30;
    public static final int VIEW_X = 30;
    public static final int VIEW_Y = 30;
    /* public static final int WORLD_X = 3000; */
    /* public static final int WORLD_Y = 3000; */
    /* public static final int VIEW_X = 3000; */
    /* public static final int VIEW_Y = 3000; */
    /* How many points a player gets when eating a food */
    public static final int FOOD_COLLECT_POINTS = 1;
    /* Used when assigning team */
    public static final int TEAM_NUMBER = 4;
    public static final int FOOD_PER_TEAM = 10;
    private int playerCounter = 0;

    protected WorldState world;
    /* Rows => self, Cols => other teams */
    protected int[][] playerVisibility;
    protected int[][] foodVisibility;
    /* Invisible */
    public static final int INVISIBLE = -1;
    // Just an abbreviation to make the table look nicer
    public static final int I = INVISIBLE;

    BarngaOnlineConfigsDefault(WorldState world) {
        this.world = world;
        this.playerVisibility = new int[TEAM_NUMBER][TEAM_NUMBER];
        this.foodVisibility = new int[TEAM_NUMBER][TEAM_NUMBER];
    }

    @Override
    public void initParams() {
        world.setWorldSizeX(WORLD_X);
        world.setWorldSizeY(WORLD_Y);

        world.setViewSizeX(VIEW_X);
        world.setViewSizeY(VIEW_Y);

        /* Visibility table */
        if (TEAM_NUMBER != 4) {
            // Everything is visible
            for (int i = 0; i < TEAM_NUMBER; i++) {
                for (int j = 0; j < TEAM_NUMBER; j++) {
                    playerVisibility[i][j] = j;
                    foodVisibility[i][j] = j;
                }
            }
        }
        else {
            /* Player visibility */
            int[][] playerVisibilityCopy = {
                {0, I, I, I},   // Team 0: Doesn't see any other team
                {2, 1, 3, 0},   // Team 1: Sees different from actual
                {0, 1, 2, 3},   // Team 2: Sees all other teams
                {I, 1, I, 3},   // Team 3: Only sees some teams
            };
            playerVisibility = playerVisibilityCopy;

            /* Food visibility */
            int[][] foodVisibilityCopy = {
                {0, I, 2, I},   // Team 0: Sees some of other teams' food
                {I, 1, I, I},   // Team 1: All food of their own
                {0, 1, 2, 3},   // Team 2: Sees food for all other teams
                {3, 3, 3, 3},   // Team 3: Everything looks the same
            };
            foodVisibility = foodVisibilityCopy;
        }

        // Place food on world
        int foodCounter = 0;
        for (int i = 0; i < TEAM_NUMBER; i++) {
            for (int j = 0; j < FOOD_PER_TEAM; j++) {
                int x = (int)(Math.random() * WORLD_X);
                int y = (int)(Math.random() * WORLD_Y);
                Coordinates coord = new Coordinates(x, y);
                Food food = new Food(foodCounter, i, coord);

                world.addFood(food);
                Util.debug("Generated food at %s\n", food.coord.toString());

                foodCounter++;
            }
        }
    }

    /**
     * Randomnly assign a new starting point for the player.
     */
    @Override
    public Coordinates initialCoordinates(long playerId, int teamId) {
        int x = (int)(Math.random() * WORLD_X);
        int y = (int)(Math.random() * WORLD_Y);
        Coordinates coord = new Coordinates(x, y);
        return coord;
    }

    @Override
    public int assignTeam(int playerId) {
        return playerCounter++ % TEAM_NUMBER;
    }

    @Override
    public boolean gameStarts() {
        return world.getPlayers().size() >= 4;
    }

    @Override
    public boolean gameEnds() {
        // Finish game when all foods are taken
        return world.getFoods().size() == 0;
    }

    @Override
    public void bumpPlayerCallback(Player bumper, Player bumpee) {
        Util.debug("Bump! Player");
    }

    @Override
    public void bumpFoodCallback(Player player, Food food) {
        Util.debug("Bump! Food");
    }

    @Override
    public void eatFoodCallback(Player player, Food food) {
        world.addPointsEarnedBy(player, FOOD_COLLECT_POINTS);
        world.removeFood(food);

        Util.debug("Player %d of Team %d ate Food ID %d\n",
                player.id, player.teamId, food.id);
    }

    @Override
    public HashSet<Food> generateFood(Player player, Food food) {
        // Do nothing
        return null;
    }

    @Override
    public boolean playerMovable(Player player, Coordinates newCoord) {
        if (player == null || newCoord == null) {
            return false;
        }

        boolean outOfBounds = newCoord.x >= WORLD_X || newCoord.x < 0
            || newCoord.y >= WORLD_Y || newCoord.y < 0;
        boolean someoneThere = world.playerAt(newCoord) != null;
        return !outOfBounds && !someoneThere;
    }

    @Override
    public boolean foodEatable(Player player, Food food) {
        if (player == null || food == null) {
            return false;
        }

        // Eatable if visible
        Util util = new Util(world, this);
        return util.foodVisible(player, food);
    }

    @Override
    public HashSet<Team<Player>> foodVisibleTeams(Food food) {
        HashSet<Team<Player>> ret = new HashSet<Team<Player>>();

        for (int i = 0; i < foodVisibility.length; i++) {
            // Can see food (but may not appear as that team's food)
            if (foodVisibility[i][food.team] != INVISIBLE) {
                ret.add(world.getTeam(i));
            }
        }

        return ret;
    }

    @Override
    public HashSet<Team<Player>> playerVisibleTeams(Player player) {
        HashSet<Team<Player>> ret = new HashSet<Team<Player>>();

        for (int i = 0; i < playerVisibility.length; i++) {
            // Can see team (but may not appear as that team)
            if (playerVisibility[i][player.teamId] != INVISIBLE) {
                ret.add(world.getTeam(i));
            }
        }

        return ret;
    }

    public int[][] getPlayerVisibility() {
        return playerVisibility;
    }

    public int[][] getFoodVisibility() {
        return foodVisibility;
    }
}
