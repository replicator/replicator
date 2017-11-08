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
        return Action.ROTATE_CCW;
    }

    @Override
    public String toString() {
        Direction curr = getOrientation();
        if (GameRunner.DEBUG) {
            switch (curr) {
                case NORTH: return "N";
                case NORTHEAST: return "NE";
                case EAST: return "E";
                case SOUTHEAST: return "SE";
                case SOUTH: return "S";
                case SOUTHWEST: return "SW";
                case WEST: return "W";
                case NORTHWEST: return "NW";
                default: return "-1";
            }
        } else {
            switch (curr) {
                case NORTH: return "|";
                case NORTHEAST: return "/";
                case EAST: return "-";
                case SOUTHEAST: return "\\";
                case SOUTH: return "|";
                case SOUTHWEST: return "/";
                case WEST: return "-";
                case NORTHWEST: return "\\";
                default: return "-1";
            }
        }

    }
}
