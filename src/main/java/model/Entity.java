package model;

import java.util.Random;

public abstract class Entity {

    /**
     * The directions that the entity can witness
     */
    public static enum Direction {
        NORTH (0), NORTHEAST (1), EAST (2), SOUTHEAST (3), SOUTH (4), SOUTHWEST (5), WEST (6), NORTHWEST (7);
        private final int value;
        Direction(int value) {
            this.value = value;
        }

        // todo: make this + rotate pretty
        private static Direction intToDirection(int i) {
            if (i < 0) {
                i = 7;
            } else if (i > 7) {
                i = 0;
            }
            switch (i) {
                case 0: return NORTH;
                case 1: return NORTHEAST;
                case 2: return EAST;
                case 3: return SOUTHEAST;
                case 4: return SOUTH;
                case 5: return SOUTHWEST;
                case 6: return WEST;
                case 7: return NORTHWEST;
                default:
                    System.out.println("Error inToDirection");
                    return null;
            }
        }

        // rotate counter clockwise
        public static Direction rotateCCW(Direction curr) {
            return intToDirection(curr.value - 1);
        }
        // rotate clockwise
        public static Direction rotateCW(Direction curr) {
            return intToDirection(curr.value + 1);
        }
    }

    public static enum Action { // USE, REPRODUCE, OBSERVE, EAT later
        MOVE, NOTHING, ROTATE_CCW, ON_FAIL, ROTATE_CW
    }

    public static enum Species {
        SAME, EMPTY, OTHER
    }


    private static final Random r = new Random();

    private int advantage;
    private int speed;
//    private int defense;
    private Direction orientation;
    private int x;
    private int y;


    public Entity(int advantage, int speed, int defense, int x, int y) {
        Direction[] possibleStart = Direction.values();
        orientation = possibleStart[getRandom().nextInt(possibleStart.length)];
        this.advantage = advantage;
        this.speed = speed;
        this.x = x;
        this.y = y;

//        this.defense = defense;
    }

    public void setOrientation(Direction newOrient) {
        orientation = newOrient;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public Random getRandom() {
        return r;
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getAdvantage() {
        return advantage;
    }

    public int getSpeed() {
        return speed;
    }

    public String toString() {
        return "A";
    }

    // todo: make this back to ==
    // implement: public boolean areAllies(Entity other))
//    public boolean equals(Entity other) {
//        if (other == null) {
//            return false;
//        } else {
//            return this.getClass() == other.getClass();
//        }
//    }

    public boolean isAlly(Entity other) {
        if (other == null) {
            return false;
        } else {
            return this.getClass() == other.getClass();
        }
    }

//    public int getDefense() {
//        return defense;
//    }

    public abstract Action nextAction();

    // TODO: public abstract void fight();
    // TODO: fight = move for now

//    public abstract boolean move();
//    public abstract boolean rotate();
//    public abstract boolean use();
//    public abstract boolean reproduce();
//    public abstract int observe();
//    public abstract boolean eat();

}
