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

    /**
     * Returns the next action for this random entity. Randomnly picks one action from all possible actions.
     * @return the next action for this RandomEntity to take
     */
    @Override
    public Action nextAction() {
        Action[] possibleActions = Action.values();
        int rand = getRandom().nextInt(possibleActions.length);
        return possibleActions[rand];
    }

    /**
     * String representation of this entity
     * @return the String representation of this entity
     */
    @Override
    public String toString() {
        return "R";
    }
}
