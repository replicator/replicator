package model;

import org.junit.Before;
import org.junit.Test;

import static model.Entity.Direction.*;
import static org.junit.Assert.*;

import model.Entity.Coordinate;
import model.Entity.Direction;

import java.util.Arrays;

public class EntityTest {
    Entity[] inputs;

    // ToDo: equality/hashcode

    @Before
    public void setup() {
        Coordinate origin = new Coordinate(0, 0);
        inputs = new Entity[] {new RandomEntity(origin), new TurretEntity(origin)}; // distinct
        for (Entity e: inputs) {
            e.setOrientation(Direction.NORTH);
        }
    }

    //todo: test when allies can be between diff species
    @Test
    public void testIsNotAlly() {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = i + 1; j < inputs.length; j++) {
                assertFalse(inputs[i].isAlly(inputs[j])); // doing all combos of entities, that aren't themselves
            }
        }
    }

    @Test
    public void testIsSelfAlly() {
        for (int i = 0; i < inputs.length; i++) {
            assertTrue(inputs[i].isAlly(inputs[i]));
        }
    }

    @Test
    public void testIsAlly() {
        // test among other species, when there are multiple that exist
        Entity[] copy = new Entity[] {new RandomEntity(new Coordinate(1, 1)),
                new TurretEntity(new Coordinate(2, 3))};
        for (int i = 0; i < inputs.length; i++) {
            assertTrue(inputs[i].isAlly(copy[i]));
        }
    }


    // todo: override equals and hashcode
    @Test
    public void testCoordAfterMove() {
        Direction[] inputs = {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
        Coordinate[] expectedResult = {new Coordinate(0, 1), new Coordinate(1, 1),
                new Coordinate(1, 0), new Coordinate(1, -1), new Coordinate(0, -1),
                new Coordinate(-1, -1), new Coordinate(-1, 0), new Coordinate(-1, 1)};
        Coordinate origin = new Coordinate(0, 0);
        for (int i = 0; i < expectedResult.length; i++) {
            Direction nextDir = inputs[i];
            Coordinate next = origin.coordAfterMove(nextDir);
            assertEquals(expectedResult[i].getX(), next.getX());
            assertEquals(expectedResult[i].getY(), next.getY());
        }
    }

    // regression test. previously failed when Direction + rotateCCW/cw was on mutable Directions
    @Test
    public void testRotateCCW() {
        Entity.Direction[] ccwOrder = {NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST, NORTH};
        for (Entity.Direction dir : ccwOrder) {
            for (Entity e : inputs) {
                e.setOrientation(Entity.Direction.rotateCCW(e.getOrientation()));
                assertEquals(dir, e.getOrientation());
            }
        }

    }

    @Test
    public void testRotateCW() {
        Entity.Direction[] cwOrder = {NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, NORTH};
        for (Entity.Direction dir : cwOrder) {
            for (Entity e : inputs) {
                e.setOrientation(Entity.Direction.rotateCW(e.getOrientation()));
                assertEquals(dir, e.getOrientation());
            }
        }
    }
}
