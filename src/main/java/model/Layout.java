package model;

import java.util.*;
import model.Entity.Coordinate;
import model.Entity.Direction;
import model.Entity.Action;

public class Layout {
    private static boolean debug = true;
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


    /**
     * Creates a 2d layout with width x and height y
     * @param x the width of the layout to be created
     * @param y the height of the layout to be created
     * @param timeLimit the time limit of this game
     * @throws IllegalArgumentException if x or y are invalid sizes (x &#8804; 0 || y &#8805; 0)
     *
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





    // TODO: one entity + win amount >  other entity

    /**
     * Returns true if the game is done. A game is done when there are no more entities or the time has passed the
     * time limit
     * @return true if the game is done
     */
    public boolean isGameDone() {
        if (entities.size() == 0) {
            return true;
        } else {
            return time >= timeLimit;
        }
    }


    public boolean handleAction(Entity e, Action act) {
        switch (act) {
            case MOVE:
                Direction orientation = e.getOrientation();
                Coordinate currPos = e.getCoordinate();
                Coordinate newPos = currPos.coordAfterMove(orientation);
                if (inBounds(newPos) && !e.isAlly(getEntity(newPos))) {
                    moveEntity(e, newPos);
                    if (GameRunner.DEBUG) {
                        System.out.println("old: (" + e.getX() + ", " + e.getY() + ") to new: (" + newPos.getX() + ", " + newPos.getX() + ")");
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
                Direction old = e.getOrientation();
                Direction newOrient = Direction.rotateCCW(old);
                if (GameRunner.DEBUG) {
                    System.out.println("Rotating entity at: (" + e.getX() + ", " + e.getY() + ") from : " + old + " to " + newOrient);
                }
                e.setOrientation(newOrient);
                break;
            case ROTATE_CW: // TODO
                e.setOrientation(Direction.rotateCW(e.getOrientation()));
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
        if (debug) {
            checkRep();
        }
        if (!isGameDone()) {
            time++;
            Set<Entity> entityCpy = new HashSet<>(entities);
            for (Entity e: entityCpy) {
                if (entities.contains(e)) {
                    if (e == null) {
                        throw new NullPointerException("null Entity added to entities set");
                    }
                    if (GameRunner.DEBUG) {
                        System.out.println("Modifying entity at: (" + e.getX() + ", " + e.getY() + ")");
                    }
                    Action act = e.nextAction();
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
    // ASSUME: entity has not already been inserted?
    public boolean insertEntity(Entity ent) {
        // true if nothing else there
        // false if it replaces
        // throw if fail
        if (!inBounds(ent.getCoordinate())) {
            throw new IllegalArgumentException("Cannot access elements outside the layout");
        }
        removeEntity(ent.getCoordinate());
        grid[ent.getX()][ent.getY()] = ent;
        entities.add(ent);
        return true;
    }

    // move ent to x/y
    // An entity must already be i n the grid
    // todo: add regression test, where entities moved onto each other, the number of entities went down, but the
    // todo: representation (grid) still showed them
    public boolean moveEntity(Entity ent, Coordinate coord) {
        if (!entities.contains(ent)) {
            throw new IllegalArgumentException("Can only move entities that already exist in the grid");
        }
        if (!inBounds(coord)) {
            throw new IllegalArgumentException("Can only move entities within the grid");
        }
        // todo: could do this manually, and save some calls
        removeEntity(ent.getCoordinate()); // remove entity at prior coordinate
        ent.setCoordinate(coord);
        insertEntity(ent); // insert entity at new coordinate
        return true;
    }

    /**
     * Removes the Entity at this coordinate. Returns true if an Entity was actually removed
     * @param coord the coordinate to remove Entities from
     * @return true if an Entity was removed from the coord, false if there was no Entity at the coord
     * @throws IllegalArgumentException if coord is outside the layout bounds
     */
    public boolean removeEntity(Coordinate coord) {
        if (!inBounds(coord)) {
            throw new IllegalArgumentException("Can only remove entities in the grid");
        }
        if (getEntity(coord) == null) {
            return false;
        } else {
            int x = coord.getX();
            int y = coord.getY();
            if (GameRunner.DEBUG) {
                System.out.println(" removed at : (" + x + ", " + y + ")");
            }
            entities.remove(getEntity(coord)); // TODO: delete the entity at this location, not any entity of same type
            grid[coord.getX()][coord.getY()] = null;
            return true;
        }
    }

    /**
     * Returns the entity at this coordinate
     * @param coord where to find the Entity to return
     * @return Entity at this coordinate, or null if there is no Entity
     * @throws IllegalArgumentException if coord is outside the layout's bounds
     * @see <code>inBounds</code>
     */
    public Entity getEntity(Coordinate coord) {
        if (!inBounds(coord)) {
            throw new IllegalArgumentException("Cannot access elements outside the layout");
        }
        return grid[coord.getX()][coord.getY()];
    }

    /**
     * Returns true if the coordinate is in this Layout's bounds
     * @param coord the coordinate to test
     * @return true if the coordiante is in this Layout's bounds
     */
    public boolean inBounds(Coordinate coord) {
        int x = coord.getX();
        int y = coord.getY();
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

    public int getTime() {
        return time;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * String representation of layout
     * @return the string representation of this Layout
     */
    // TODO: make it print in better x/y format
    @Override
    public String toString() {
        StringBuilder line = new StringBuilder();
        int tabSize = 6;
        for (int i = 0; i < grid[0].length * tabSize; i++) {
            line.append("-");
        }
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
        result.append(line);
        return result.toString();
    }

    public void checkRep() {
        // entities must be placed in the grid
        // grid's entities must be in entities
        Set<Entity> gridEntities = new HashSet<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] != null) {
                    Entity temp = grid[row][col];
                    // entities in the grid at x/y should have those x/y coords
                    if (temp.getCoordinate().getX() != row || temp.getCoordinate().getY() != col) {
                        throw new IllegalStateException("Entities in the grid must have the same coordinates as that grid location");
                    }
                    gridEntities.add(temp);
                }
            }
        }
        if (gridEntities.size() != entities.size()) {
            throw new IllegalStateException("Entities in the grid and in the Entities set must be the same");
        }
        for (Entity e: gridEntities) {
            if (!entities.contains(e)) {
                throw new IllegalStateException("Entities in the grid and in the Entities set must be the same");
            }
        }
    }

}
