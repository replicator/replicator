package model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

/**
 * @author Michael Shintaku
 * @see Layout
 */
public abstract class Entity {


    private static final Random r = new Random();

    private int advantage;
    private int speed;
    //    private int defense;
    private Direction orientation;
    private Coordinate coordinate;




    /**
     * An immutable representation of an (x, y) position in the Layout. x >= 0 && y >= 0
     * TODO: override equals and hashcode
     */
    public static class Coordinate {
        private final int x;
        private final int y;

        /**
         * Creates a Coordinate object with the x, y coordinates. x >= 0 && y >= 0
         * @param x the x-coordinate
         * @param y the y-coordinate
         */
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        /**
         * Finds and returns a new Coordinate after moving 1 step in the direction dir. Does not check bounds with
         * Layout or consider contents of Layout
         * @param dir the direction in which this Coordinate is moving 1 step in
         * @return the new Coordinate after this has taken 1 step in direction dir
         */
        public Coordinate coordAfterMove(Direction dir) {
            // todo: error handle?
            // todo: moving multiple times?
            int newY = y;
            int newX = x;
            switch(dir) {
                case NORTH:
                    newY += 1;
                    break;
                case NORTHEAST:
                    newY += 1;
                    newX += 1;
                    break;
                case EAST:
                    newX += 1;
                    break;
                case SOUTHEAST:
                    newY -= 1;
                    newX += 1;
                    break;
                case SOUTH:
                    newY -= 1;
                    break;
                case SOUTHWEST:
                    newY -= 1;
                    newX -= 1;
                    break;
                case WEST:
                    newX -= 1;
                    break;
                case NORTHWEST:
                    newX -= 1;
                    newY += 1;
                    break;
            }
            return new Coordinate(newX, newY);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof Coordinate)) {
                return false;
            }
            Coordinate cOther = (Coordinate) other;
            return this.getX() == cOther.getX() && this.getY() == cOther.getY();
        }

        // uses Joshua Bloch's formula
        @Override
        public int hashCode() {
            int result = 17;
            result = 31 * result + x;
            result = 31 * result + y;
            return result;
        }


    }

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




    /**
     * Creates an Entity at the given coordinate, with the given advantage, speed,
     * defense, x, y, values.  The Entity has a random starting orientation
     *
     * @param advantage the advantage of this Entity over other Entities
     * @param speed the speed of this Entity
     * @param defense TODO: the defense attribute of this entity
     * @param coord the coordinate in the layout where this entity is being placed
     * @see Coordinate
     */
    public Entity(int advantage, int speed, int defense, Coordinate coord) {
        orientation = startingOrientation();
        this.advantage = advantage;
        this.speed = speed;
        this.coordinate = coord;
    }

    @Override
    public final boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    // TODO: hashcode buggy for now. maybe only use equality for testing purposes
    // because you would have to remake the entity set anytime an entity in it is mutated, if it is value based equality
    // (value of entity changes, so its hashCode would, but entity set has not updated)

//    // equal entities: same class, advantage, speed, defense, coordinate, orientation, nextmove
//    // final so cannot be overriden
//    // todo: entities' fields must always implement value-based equality
//    @Override
//    public final boolean equals(Object other) {
//        // must be Entities
//        if (other == this) {
//            return true;
//        }
//        if (!(other instanceof Entity)) {
//            return false;
//        }
//
//        // must be same Entity type
//        Entity entOther = (Entity) other;
//        Class thisClass = this.getClass();
//        Class otherClass = entOther.getClass();
//        if (thisClass != otherClass) {
//            return false;
//        }
//        // all fields must be equals
//        Field[] fields = thisClass.getFields(); // TODO: I dont think this is alwayss same order
////        System.out.println("fields: ");
////        for (Field f : fields) {
////            System.out.print("\t" + f.getType().getTypeName());
////        }
//
//        Field[] otherFields = otherClass.getFields();
//        // todo: hopefully changes bug, sorting actually doesnt fml
////        Arrays.sort(fields);
////        Arrays.sort(otherFields);
//        if (fields.length != otherFields.length) {
//            return false;
//        }
//        // todo: this is buggy
//        System.out.println("fields length: " + fields.length);
//        for (int i = 0; i < fields.length; i++) {
//            try {
//                // all the fields must be the same
//                if (!(fields[i].get(this).equals(otherFields[i].get(other)))) {
//                    return false;
//                }
//            } catch (IllegalAccessException ia) {
//                System.out.println("Field inaccessible");
//                return false;
//            }
//        }
//        // todo: not reached
//        return true;
//    }

//    // Uses Joshua Bloch's Effective Java (2e) general recipe for hashCode functions
//    @Override
//    public int hashCode() {
//        // hashcode must be consistent, as long as equality between timesteps same
//        // if two objs are equal, they have same hashcode
//        //
//        // int result = 17;
//        long result = 17;
//        int modifier = 3;
//        // since prior value-based equality defined by the public fields
//        result = result * modifier + advantage;
//        result = result * modifier + speed;
//        result = result * modifier + orientation.value;
//        result = result * modifier + coordinate.getX();
//        result = result * modifier + coordinate.getY();
//
////        Field[] fields = this.getClass().getFields();
////        for (Field f: fields) {
////            Class t = f.getType();
////            int c = 0;
////            // Todo: must be a better way to do this than the raw type names
////            switch (t.getTypeName()) {
////                case "int":
////                    try {
////                        c = (int) f.get(this);
////                    } catch (IllegalAccessException e) {
////                        e.printStackTrace();
////                        throw new IllegalStateException("Need to fix this code, a new field has been added");
////                    }
////                    break;
////                case "model.Entity$Direction":
////                    try {
////                        c = ((Direction) f.get(this)).value;
////                    } catch (IllegalAccessException e) {
////                        e.printStackTrace();
////                        throw new IllegalStateException("Need to fix this code, a new field has been added");
////                    }
////                    break;
////                case "model.Entity$Coordinate":
////                    try {
////                        c = f.get(this).hashCode();
////                    } catch (IllegalAccessException e) {
////                        e.printStackTrace();
////                        throw new IllegalStateException("Need to fix this code, a new field has been added");
////                    }
////                    break;
////                default:
////                    System.out.println("problem name: " + f.getName());
////                    throw new IllegalStateException("Need to fix this code, a new field has been added");
////            }
////            result = 31 * result + c;
////            diagnostic stuff
////            try {
////                System.out.println("type name: " + t.getTypeName());
////                System.out.println("type: " + t + " name: " + f.getName() + " value: " + f.get(this) +  " hash: " + t.hashCode());
////                // how to get the raw type of the field, to do the formula?
////                // or a type that we know?
////            } catch (IllegalAccessException e) {
////                System.out.println("uhoh");
////                e.printStackTrace();
////            }
////            result = result * 31 + t.hashCode();
//
////        }
//        int newResult =  (int) (result % Integer.MAX_VALUE);
//        System.out.println("hashcode: " + newResult);
//        return newResult;
//        // return result;
//    }



    /**
     * todo: write this comment better
     * Used to get the starting orientation. Defaults as a random orientation.
     * @return a random orientation
     */
    public Direction startingOrientation() {
        Direction[] possibleStart = Direction.values();
        return possibleStart[getRandom().nextInt(possibleStart.length)];
    }

    /**
     * Sets this Entity's orientation to the given @param newOrient
     * @param newOrient the Orientation that this Entity is set to
     */
    public void setOrientation(Direction newOrient) {
        orientation = newOrient;
    }

    /**
     * Gets the current orientation of this Entity
     * @return the current Orientation of this Entity
     */
    public Direction getOrientation() {
        return orientation;
    }

    /**
     * Gets the Random object
     * @return the Random object
     */
    public Random getRandom() {
        return r;
    }

    /**
     * Returns the Coordinate this Entity is at
     * @return the coordinate of this entity
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setX(int newX) {
        setCoordinate(new Coordinate(newX, coordinate.y));
    }

    public void setY(int newY) {
        setCoordinate(new Coordinate(coordinate.x, newY));
    }

    public void setCoordinate(Coordinate coord) {
        coordinate = coord;
    }

    public int getX() {
        return coordinate.x;
    }

    public int getY() {
        return coordinate.y;
    }

    public int getAdvantage() {
        return advantage;
    }

    public int getSpeed() {
        return speed;
    }

    public String toString() {
        return "X";
    }


    /**
     * Returns true if other is an ally (non-enemy) with this Entity
     * @param other the Entity to compare to
     * @return true if other is an ally (non-enemy) with this Entity
     */
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

    /**
     * Used to get the next action this Entity will try to take for the next game update.
     * @return the Action for this Entity to attempt to take in the next game update
     */
    public abstract Action nextAction();

    // TODO: public abstract void fight();
    // TODO: fight = move for now

//    public abstract boolean use();
//    public abstract boolean reproduce();
//    public abstract int observe();
//    public abstract boolean eat();

}
