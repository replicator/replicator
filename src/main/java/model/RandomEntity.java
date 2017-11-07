package model;

public class RandomEntity extends Entity {

    private static final int ADVANTAGE = 3;
    private static final int SPEED = 3;
    private static final int DEFENSE = 3;

    public RandomEntity(int x, int y) {
        super(ADVANTAGE, SPEED, DEFENSE, x, y);
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
