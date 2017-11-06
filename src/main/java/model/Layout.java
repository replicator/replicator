package model;

import java.util.Arrays;

public class Layout {
    private Entity[][] grid;

    /**
     * Create the 2d layout with x, y size
     * @param x the width of the layout.  x > 0 or throws an IllegalArgumentException
     * @param y the height of the layout. y > 0 or throws an IllegalArgumentException
     */

    public Layout(int x, int y) throws IllegalArgumentException {
        if (x < 1 || y < 1) {
            throw new IllegalArgumentException("Layout dimensions must be positive");
        }
        grid = new Entity[x][y];
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
                result.append(grid[row][col]);
            }
            result.append("\n");
        }
        result.append("\n").append(line);
        return result.toString();
    }

    public boolean update() {

        // update grid;
        return false;
    }


    public boolean insertEntity(Entity ent, int x, int y) {
        // true if nothing else there
        // false if it replaces
        // throw if fail
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Cannot access elements outside the layout");
        } else if (grid[x][y] != null) {
            grid[x][y] = ent;
            return false;
        } else {
            grid[x][y] = ent;
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
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
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
