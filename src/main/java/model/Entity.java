package model;

import java.util.Random;

public abstract class Entity {

    /**
     * The directions that the entity can witness
     */
    public static enum Direction {
        NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST
    }

    public static enum Action { // USE, REPRODUCE, OBSERVE, EAT later
        MOVE, NOTHING, ROTATE_LEFT, ROTATE_RIGHT
    }

    public static enum Species {
        SAME, EMPTY, OTHER
    }


    private static final Random r = new Random();

    private int advantage;
    private int speed;
//    private int defense;
    private Direction orientation;

    public Entity(int advantage, int speed, int defense) {
        Direction[] possibleStart = Direction.values();
        orientation = possibleStart[getRandom().nextInt(possibleStart.length)];
        this.advantage = advantage;
        this.speed = speed;
//        this.defense = defense;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public Random getRandom() {
        return r;
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
