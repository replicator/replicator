package model;

/**
 * An Entity that randomly moves and reproduces when possible.
 * @author Michael Shintaku
 * @see model.Entity
 */
public class RandomEntity extends Entity {

    private static final int ADVANTAGE = 3;
    private static final int SPEED = 3;
    private static final int DEFENSE = 3;

    /**
     * Creates a RandomEntity at the given coordinate, with the given advantage, speed,
     * defense, x, y, values.  The Entity has a random starting orientation
     *
     * @param coord the coordinate in the layout where this entity is being placed
     * @see Coordinate
     */
    public RandomEntity(Coordinate coord) {
        super(ADVANTAGE, SPEED, DEFENSE, coord);
    }

    @Override
    public Action nextAction() {
        Action[] possibleActions = Action.values();
        int rand = getRandom().nextInt(possibleActions.length);
//        if (possibleActions[rand] == null) {
//            System.out.println("action is null???");
//        } else {
//            System.out.println("action: " + rand);
//        }
        return possibleActions[rand];
    }

    @Override
    public String toString() {
        return "R";
    }
}
