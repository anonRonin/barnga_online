package edu.miamioh.barnga_online;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import edu.miamioh.barnga_online.events.*;
import edu.miamioh.barnga_online.listeners.*;

/**
 * Unit test for simple App.
 */
public class MainTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public MainTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(MainTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testMain() {
        WorldState world = new WorldState();
        BarngaOnlineConfigsDefault configs = new BarngaOnlineConfigsDefault(world);
        configs.initParams();
        Util util = new Util(world, configs);

        // Player that belongs to Team 0
        int result;
        Player p = new Player(42, 0, new Coordinates(42, 42));
        result = util.playerVisibleAs(0, p);
        assertTrue(result == 0);
        result = util.playerVisibleAs(1, p);
        assertTrue(result == 2);
        result = util.playerVisibleAs(2, p);
        assertTrue(result == 0);
        result = util.playerVisibleAs(3, p);
        assertTrue(result == BarngaOnlineConfigsDefault.I);

        p = new Player(42, 1, new Coordinates(42, 42));
        result = util.playerVisibleAs(0, p);
        assertTrue(result == BarngaOnlineConfigsDefault.I);
        result = util.playerVisibleAs(1, p);
        assertTrue(result == 1);
        result = util.playerVisibleAs(2, p);
        assertTrue(result == 1);
        result = util.playerVisibleAs(3, p);
        assertTrue(result == 1);

        p = new Player(42, 2, new Coordinates(42, 42));
        result = util.playerVisibleAs(0, p);
        assertTrue(result == BarngaOnlineConfigsDefault.I);
        result = util.playerVisibleAs(1, p);
        assertTrue(result == 3);
        result = util.playerVisibleAs(2, p);
        assertTrue(result == 2);
        result = util.playerVisibleAs(3, p);
        assertTrue(result == BarngaOnlineConfigsDefault.I);

        p = new Player(42, 3, new Coordinates(42, 42));
        result = util.playerVisibleAs(0, p);
        assertTrue(result == BarngaOnlineConfigsDefault.I);
        result = util.playerVisibleAs(1, p);
        assertTrue(result == 0);
        result = util.playerVisibleAs(2, p);
        assertTrue(result == 3);
        result = util.playerVisibleAs(3, p);
        assertTrue(result == 3);
    }
}
