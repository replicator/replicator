package model;

/**
 * A stationary entity that spins and reproduces when possible.
 * @author Michael Shintaku
 * @see model.Entity
 */
public class TurretEntity extends Entity {

    private static final int ADVANTAGE = 3;
    private static final int SPEED = 3;
    private static final int DEFENSE = 3;

    /**
     * Creates a TurretEntity at the given coordinate, with the given advantage, speed,
     * defense, x, y, values.  The Entity has a random starting orientation
     *
     * @param coord the coordinate in the layout where this entity is being placed
     * @see Coordinate
     */
    public TurretEntity(Coordinate coord) {
        super(ADVANTAGE, SPEED, DEFENSE, coord);
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
