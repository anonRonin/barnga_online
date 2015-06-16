package edu.miamioh.barnga_online;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class PlayerTest extends TestCase {

    public PlayerTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(PlayerTest.class);
    }

    public void testPlayer() {
        WorldState world = new WorldState();
        BarngaOnlineConfigsDefault configs = new BarngaOnlineConfigsDefault(world);
        configs.initParams();

        // Player that belongs to Team 0
        Player p = new Player(42, 0, new Coordinates(42, 42), configs);

        assertTrue(p.appearsTo(0) == 0);
        assertTrue(p.appearsTo(1) == 2);
        assertTrue(p.appearsTo(2) == 0);
        assertTrue(p.appearsTo(3) == BarngaOnlineConfigsDefault.INVISIBLE);

        p = new Player(42, 1, new Coordinates(42, 42), configs);
        assertTrue(p.appearsTo(0) == BarngaOnlineConfigsDefault.INVISIBLE);
        assertTrue(p.appearsTo(1) == 1);
        assertTrue(p.appearsTo(2) == 1);
        assertTrue(p.appearsTo(3) == 1);

        p = new Player(42, 2, new Coordinates(42, 42), configs);
        assertTrue(p.appearsTo(0) == BarngaOnlineConfigsDefault.INVISIBLE);
        assertTrue(p.appearsTo(1) == 3);
        assertTrue(p.appearsTo(2) == 2);
        assertTrue(p.appearsTo(3) == BarngaOnlineConfigsDefault.INVISIBLE);

        p = new Player(42, 3, new Coordinates(42, 42), configs);
        assertTrue(p.appearsTo(0) == BarngaOnlineConfigsDefault.INVISIBLE);
        assertTrue(p.appearsTo(1) == 0);
        assertTrue(p.appearsTo(2) == 3);
        assertTrue(p.appearsTo(3) == 3);
    }
}
