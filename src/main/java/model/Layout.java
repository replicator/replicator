package model;

import java.util.*;

public class Layout {
    private Entity[][] grid;
    private Set<Entity> entities; // O(nm) time to update board, number of entities <= nm; n = grid.x, m = grid.y
    // TODO: how to do with priorities? brute force O(p * nm), p = priorities.
    // can still do linear O(nm) if we order sets of entities by their priority.
    // to order by priority, maintain TreeMap<Priority, Set<Entity>>, but log(nm) adjustments (too frequent)
    // update: nmlog(nm) adjusts + nm pass
    // how often are adjusts? at worst, very often(entities reproducing/eating each other)
    // but in 2nd option, nmlog(nm) at best for the entities. this soln depends on the entites behavior and likely to be <nmlog(nm) on avg

    // or unordered in HashMap<Priority, Set<Entity>>, sort on demand
    // or, upon updates, nmlog(nm) sort, linear pass to update, each update is O(1) with a hashtable
    // update: nmlog(nm) sort + nm pass + c adjusts. O(1) when not strictly updating

    // ordered option is < xlog(x) on avg/assuming all dont collide while unordered is == xlog(x) for x = entitiy #

//    private Map<Integer, Set<Entity>> entities;

    private int timeLimit;
    private int time;

    // TODO
    public String getNumOfEachEntity() {
        return "to be implemented";
//        return numOfEachEntity;
    }


    private class Coordinate {
        private int x;
        private int y;

        private Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }


    /**
     * Create the 2d layout with x, y size
     * @param x the width of the layout.  x > 0 or throws an IllegalArgumentException
     * @param y the height of the layout. y > 0 or throws an IllegalArgumentException
     */

    public Layout(int x, int y, int timeLimit) throws IllegalArgumentException {
        if (x < 1 || y < 1) {
            throw new IllegalArgumentException("Layout dimensions must be positive");
        }
        grid = new Entity[x][y];
//        entities = new TreeMap<>();
        entities = new HashSet<Entity>(); // no order to who replaces who
        time = 0;
        this.timeLimit = timeLimit;

//        entities = new TreeMap<Integer, Entity[]>(); TODO: sorted later, to do entity collisions in order
    }


    public Coordinate coordAfterMove(Entity.Direction dir, Coordinate current) {
        // todo: error handle
        Coordinate newCoord = new Coordinate(current.x, current.y);
        switch(dir) {
            case NORTH:
                newCoord.y += 1;
                break;
            case NORTHEAST:
                newCoord.y += 1;
                newCoord.x += 1;
                break;
            case EAST:
                newCoord.x += 1;
                break;
            case SOUTHEAST:
                newCoord.y -= 1;
                newCoord.x += 1;
                break;
            case SOUTH:
                newCoord.y -= 1;
                break;
            case SOUTHWEST:
                newCoord.y -= 1;
                newCoord.x -= 1;
                break;
            case WEST:
                newCoord.x -= 1;
                break;
            case NORTHWEST:
                newCoord.x -= 1;
                newCoord.y += 1;
                break;
        }
        return newCoord;
    }

    // TODO: one entity + win amount >  other entity
    public boolean isGameDone() {
        if (entities.size() == 0) {
            return true;
        } else {
            return time >= timeLimit;
        }
    }


    public boolean handleAction(Entity e, Entity.Action act) {
        switch (act) {
            case MOVE:
                Entity.Direction orientation = e.getOrientation();
                Coordinate currPos = new Coordinate(e.getX(), e.getY());
                Coordinate newPos = coordAfterMove(orientation, currPos);
//                            if (inBounds(newPos.x, newPos.y) && e.isAlly(getEntity(newPos.x, newPos.y))) {
//                                System.out.println("NO EATING TEAMMATES");
//                                System.out.println("Attemted move: (" + e.getX() + ", " + e.getY() + ") -> (" + newPos.x + ", " + newPos.y + ")");
//                            }
                if (inBounds(newPos.x, newPos.y) && !e.isAlly(getEntity(newPos.x, newPos.y))) {
                    moveEntity(e, newPos.x, newPos.y);
                    if (GameRunner.DEBUG) {
                        System.out.println("old: (" + currPos.x + ", " + currPos.y + ") to new: (" + newPos.x + ", " + newPos.y + ")");
                    }
                    break;
                } // else fall through to default
            case ON_FAIL:
                // handleAction(?)
                System.out.println("Attempted to move out of bounds, or hit ally");
                // currently do nothing
                break;
            case NOTHING:
                break;
            case ROTATE_CCW: // TODO
                Entity.Direction old = e.getOrientation();
                Entity.Direction newOrient = Entity.Direction.rotateCCW(old);
                if (GameRunner.DEBUG) {
                    System.out.println("Rotating entity at: (" + e.getX() + ", " + e.getY() + ") from : " + old + " to " + newOrient);
                }
                e.setOrientation(newOrient);
//                e.setOrientation(Entity.Direction.rotateCCW(e.getOrientation()));
                break;
            case ROTATE_CW: // TODO
                e.setOrientation(Entity.Direction.rotateCW(e.getOrientation()));
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * updates the board
     * @return true if an update occurs. doesn't necessarily mean the entities do any noticeable changes
     */
    public boolean update() {
        if (!isGameDone()) {
            time++;
            Set<Entity> entityCpy = new HashSet<>(entities);
            for (Entity e: entityCpy) {
                if (entities.contains(e)) {
                    if (GameRunner.DEBUG)
                        if (GameRunner.DEBUG) {
                            System.out.println("Modifying entity at: (" + e.getX() + ", " + e.getY() + ")");
                        }
                    if (e == null) {
                        System.out.println(" UHOHHHHH attempting to update a null entity");
                        System.exit(1);
                    }
                    Entity.Action act = e.nextAction();
                    handleAction(e, act);
                }

            }
            return true;
        } else {
            System.out.println("numb entities: " + entities.size());
            return false;
        }
    }

    // todo: regression test. bug of entities' x/y value not being updated

    // todo: inserting a null
    // todo: definition of t/f returns
    public boolean insertEntity(Entity ent, int x, int y) {
        // true if nothing else there
        // false if it replaces
        // throw if fail
        if (!inBounds(x, y)) {
            throw new IllegalArgumentException("Cannot access elements outside the layout");
        }
        if (grid[x][y] != null) {
            removeEntity(x, y);
            grid[x][y] = ent;
            entities.add(ent);
            ent.setX(x);
            ent.setY(y);
            return false;
        } else {
            grid[x][y] = ent;
            entities.add(ent);
            ent.setX(x);
            ent.setY(y);
            return true;
        }
    }

    // move ent to x/y
    public boolean moveEntity(Entity ent, int x, int y) {
        if (!inBounds(x, y)) {
            throw new IllegalArgumentException("Can only move entities in the grid");
        }
        removeEntity(ent.getX(), ent.getY());
        insertEntity(ent, x, y);
        return true;
    }

    /**
     * True if it removed an existing entity
     * @param x
     * @param y
     * @return
     */
    public boolean removeEntity(int x, int y) {
        if (!inBounds(x, y)) {
            throw new IllegalArgumentException("Can only remove entities in the grid");
        }
        if (grid[x][y] == null) {
            return false;
        } else {
            System.out.println(" removed at : (" + x + ", " + y + ")");
            entities.remove(grid[x][y]); // TODO: delete the entity at this location, not any entity of same type
            grid[x][y] = null;
            return true;
        }
    }

    /**
     * Returns the entity at these x, y coords.
     * @param x
     * @param y
     * @return
     */
    public Entity getEntity(int x, int y) {
        if (!inBounds(x, y)) {
            throw new IllegalArgumentException("Cannot access elements outside the layout");
        }
        return grid[x][y];
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0  && y < getHeight();
    }

    public int getNumEntities() {
        return entities.size();
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    /**
     * String representation of layout
     * @return
     */
    @Override
    public String toString() {
        char[] lineChar = new char[grid[0].length];
        Arrays.fill(lineChar, '-');
        String line = new String(lineChar);

        StringBuilder result = new StringBuilder(line + "\n");

        // todo: in this order b/c of how the input[][] looks
        for (int row = 0; row < grid.length; row++) {

            for (int col = 0; col < grid[0].length; col++) {
                String output = "\t";
                if (grid[row][col] == null) {
                    output += "0";
                } else {
                    output += grid[row][col].toString();
                }
                result.append(output);
            }
            result.append("\n");
        }
        result.append("\n").append(line);
        return result.toString();
    }

}
