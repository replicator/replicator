package model;

public class RandomEntity extends Entity {

    public RandomEntity(int advantage, int speed, int defense) {
        super(advantage, speed, defense);
    }

    @Override
    public Action nextAction() {
        Action[] possibleActions = Action.values();
        int rand = getRandom().nextInt(possibleActions.length);
        return possibleActions[rand];
    }

    @Override
    public String toString() {
        return "R";
    }
}
