package model;

import org.junit.Before;
import org.junit.Test;

import static model.Entity.Direction.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.Entity.Coordinate;
import model.Entity.Direction;

public class EntityTest {
    Entity[] inputs;

    @Before
    public void setup() {
        Coordinate origin = new Coordinate(0, 0);
        inputs = new Entity[] {new RandomEntity(origin), new TurretEntity(origin)};
        for (Entity e: inputs) {
            e.setOrientation(Direction.NORTH);
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
