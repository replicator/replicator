package model;

import java.util.*;

public class Layout {
    private Entity[][] grid;
    private List<Entity> entities;
    private int timeLimit;
    private int time;


//    private Map<Integer, Entity[]> entities;
// up to nlogn time to update board

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
        entities = new ArrayList<Entity>(); // no order to who replaces who
        time = 0;
        this.timeLimit = timeLimit;

//        entities = new TreeMap<Integer, Entity[]>(); TODO: sorted later, to do entity collisions in order
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

    public boolean isGameDone() {
        if (entities.size() == 0) {
            return true;
        } else {
            return time >= timeLimit;
        }
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < getWidth() && y >= 0  && y < getHeight();
    }

    /**
     * updates the board
     * @return true if an update occurs. doesn't necessarily mean the entities do any noticeable changes
     */
    public boolean update() {
        if (!isGameDone()) {
            time++;
            for (int i = 0; i < entities.size(); i++) {
//            for (Entity e : entities) {
                Entity e = entities.get(i);
                if (e == null) {
                    System.out.println("uhoh");
                }
                Entity.Action act = e.nextAction();
                switch (act) {
                    case MOVE: // TODO: currently just deletes everything else
                        Entity.Direction orientation = e.getOrientation();
                        Coordinate currPos = new Coordinate(e.getX(), e.getY());
                        Coordinate newPos = coordAfterMove(orientation, currPos);

                        if (inBounds(newPos.x, newPos.y)) {
                            if (!insertEntity(e, newPos.x, newPos.y)) { // if there was an entity being replaced, delete it
                                i--;
                            }
                            removeEntity(currPos.x, currPos.y);
                        } // does nothing if outof bounds
                        break;
                    case NOTHING:
                        break;
                    case ROTATE_LEFT: // TODO
                        e.setOrientation(Entity.Direction.rotateLeft(e.getOrientation()));
                        break;
                    case ROTATE_RIGHT: // TODO
                        e.setOrientation(Entity.Direction.rotateRight(e.getOrientation()));
                        break;
                    default:
                        return false;
                }

            }
            return true;
        } else {
            return false;
        }
    }

    // todo: inserting a null entity
    public boolean insertEntity(Entity ent, int x, int y) {
        // true if nothing else there
        // false if it replaces
        // throw if fail
        if (ent == null) {
            return false;
        }

        if (!inBounds(x, y)) {
            throw new IllegalArgumentException("Cannot access elements outside the layout");
        } else if (grid[x][y] != null) {
            removeEntity(x, y);
            grid[x][y] = ent;
            entities.add(ent);
            return false;
        } else {
            grid[x][y] = ent;
            entities.add(ent);
            return true;
        }
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

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }


}
