package model;

/**
 * Essentially spins and kills/reproduces when possible
 */
public class TurretEntity extends Entity {

    private static final int ADVANTAGE = 3;
    private static final int SPEED = 3;
    private static final int DEFENSE = 3;

    public TurretEntity(int x, int y) {
        super(ADVANTAGE, SPEED, DEFENSE, x, y);
    }

    @Override
    public Entity.Action nextAction() {
        return Action.ROTATE_LEFT;
    }

    // TODO: figure out why this isn't working
    @Override
    public String toString() {
        Direction curr = getOrientation();
        switch (curr) {
            case NORTH: return "|";
            case NORTHEAST: return "/";
            case EAST: return "-";
            case SOUTHEAST: return "\\";
            case SOUTH: return "|";
            case SOUTHWEST: return "/";
            case WEST: return "-";
            case NORTHWEST: return "\\";
            default: return "S";
        }
    }
}
