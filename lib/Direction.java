package lib;

public enum Direction {
    NORTH(-1, 0), NORTHEAST(-1, 1), EAST(0, 1), SOUTHEAST(1, 1), SOUTH(1, 0),
    SOUTHWEST(1, -1), WEST(0, -1), NORTHWEST(-1, -1), INVALID(0, 0);
    
    private int rowFactor;
    private int columnFactor;
    
    private Direction(int rowFactor, int columnFactor) {
        this.rowFactor = rowFactor;
        this.columnFactor = columnFactor;
    }
    
    /** Returns the the direction representing the position of ROW2
     *  and COLUMN2 relative to ROW1 and COLUMN1. */
    public static Direction findRelativeDirection(int row1, int column1, int row2, int column2) {
        if (row1 < row2) {
            if (column1 < column2) {
                return SOUTHEAST;
            } else if (column1 > column2) {
                return SOUTHWEST;
            } else {
                return SOUTH;
            }
        } else if (row1 > row2) {
            if (column1 < column2) {
                return NORTHEAST;
            } else if (column1 > column2) {
                return NORTHWEST;
            } else {
                return NORTH;
            }
        } else {
            return INVALID;
        }
    }
    
    public static int[] offsetInDirection(int row, int column, Direction direction, int offset) {
        int[] newPosition = {row + offset * direction.rowFactor, column + offset * direction.columnFactor};
        return newPosition;
    }
}
