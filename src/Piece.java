package src;
import lib.PieceType;
import lib.Player;
import lib.IllegalCommandException;
import lib.NoSuchLocationException;


public abstract class Piece {

    public static int GLOBAL_ID = 0;

    final int ID;
    Player controller;
    PieceType type;
    int currRow;
    int currColumn;
    Board board;
    
    Piece(Player controller, int row, int column, Board board) throws NoSuchLocationException {
        this.ID = nextId();
        this.controller = controller;
        this.currRow = row;
        this.currColumn = column;
        this.board = board;
        this.board.addPiece(this, row, column);
    }
    
    /** Moves this piece to position at ROW and COLUMN. 
     * @throws NoSuchLocationException 
     * @throws IllegalCommandException */
    public void move(int row, int column, int roll) throws NoSuchLocationException, IllegalCommandException {
        throw new UnsupportedOperationException("This piece does not support the move method.");
    }

    /** Ensures that this piece can move to ROW and COLUMN.
     * @throws NoSuchLocationException 
     * @throws IllegalCommandException */
    void validateMove(int row, int column, int roll) throws NoSuchLocationException, IllegalCommandException {
        throw new UnsupportedOperationException("This piece does not support the canMove method.");
    }
    
    /** Attacks TARGET. Must call recordAttack on the controller of the piece. 
     * @throws IllegalCommandException 
     * @throws NoSuchLocationException */
    public void attack(Piece target) throws IllegalCommandException, NoSuchLocationException {
        throw new UnsupportedOperationException("This piece does not support the attack method.");
    }

    /** Ensures that this piece can attack TARGET. 
     * @throws IllegalCommandException */
    void validateAttack(Piece target) throws IllegalCommandException {
        throw new UnsupportedOperationException("This piece does not support the canAttack method.");
    }
    
    /** Resolve an attack from ATTACKER with damage DAMAGE. 
     * @throws NoSuchLocationException */
    public void resolveAttack(Piece attacker, int damage) throws NoSuchLocationException {
        throw new UnsupportedOperationException("This piece does not support the resolveAttack method.");
    }

    /** Returns the next id, and increments the global id counter. */
    public int nextId() {
        int myId = GLOBAL_ID;
        GLOBAL_ID += 1;
        return myId;
    }
    
    /** Returns the ID of this piece. */
    public int getID() {
        return ID;
    }
    
    /** Returns the controller of this piece. */
    public Player getController() {
        return controller;
    }

    /** Returns the type of this piece. */
    public PieceType getType() {
        return type;
    }

    /** Returns the row this piece is on. */
    public int getRow() {
        return currRow;
    }
    
    /** Returns the column this piece is on. */
    public int getColumn() {
        return currColumn;
    }
    
}
