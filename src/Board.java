package src;
import lib.NoSuchLocationException;

public class Board {

    private int size;
    private Piece[][] boardRepresentation;
    
    /** Constructs a SIZE by SIZE board. */
    public Board(int size) {
        this.size = size;
        this.boardRepresentation = new Piece[size][size];
    }

    /** Adds PIECE to the board at the position at ROW and COLUMN. 
     * @throws NoSuchLocationException */
    public void addPiece(Piece piece, int row, int column) throws NoSuchLocationException {
        validateLocation(row, column);
        boardRepresentation[row][column] = piece;
    }
    
    /** Resets the position at ROW and COLUMN to be empty. 
     * @throws NoSuchLocationException */
    public void removePiece(int row, int column) throws NoSuchLocationException {
        validateLocation(row, column);
        boardRepresentation[row][column] = null;
    }
    
    /** Returns true if the position at ROW and COLUMN is not occupied. 
     * @throws NoSuchLocationException */
    public boolean isEmpty(int row, int column) throws NoSuchLocationException {
        validateLocation(row, column);
        return boardRepresentation[row][column] == null;
    }
    
    /** Gets the piece at the position at ROW and COLUMN. 
     * @throws NoSuchLocationException */
    public Piece getPiece(int row, int column) throws NoSuchLocationException {
        validateLocation(row, column);
        return boardRepresentation[row][column];
    }

    /** Makes sure that the location specified by ROW and COLUMN exists. 
     * @throws NoSuchLocationException */
    public void validateLocation(int row, int column) throws NoSuchLocationException {
        if (row >= size || column >= size) {
            throw new NoSuchLocationException("Row:" + row + " Column:" + column + " does not exist.");
        }
    }
    
    /** Returns true if the position at ROW and COLUMN is lethal to a Piece. */
    public boolean isLethalPosition(int row, int column) {
        if (row >= this.getSize() || column >= this.getSize() || row < 0 || column < 0) {
            return true;
        }
        return false;
    }
    
    /** Returns the size of the board. */
    public int getSize() {
        return size;
    }
    
    /** Prints a textual representation of the board. */
    public void dump() {
        System.out.print("   ");
        for (int i = 0; i < size; i++) {
            System.out.print(i + "   ");
        }
        System.out.println();
        for (int i = 0; i < size; i++) {
            System.out.print(i + "  ");
            for (int j = 0; j < size; j++) {
                Piece currPiece = boardRepresentation[i][j];
                if (currPiece == null) {
                    System.out.print("--  ");
                } else {
                    System.out.print("" + currPiece.getID() + "   ");
                }
            }
            System.out.println();
        }
    }
    
}
