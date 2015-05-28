package src;
import lib.Direction;
import lib.Player;
import lib.IllegalCommandException;
import lib.NoSuchLocationException;
import static lib.PieceType.*;


public class CharacterPiece extends Piece {

    private boolean hasMoved;
    private boolean hasAttacked;
    
    CharacterPiece(Player controller, int row, int column, Board board) throws NoSuchLocationException {
        super(controller, row, column, board);
        this.type = CHARACTER;
        this.hasMoved = false;
        this.controller.addPiece(this);
    }

    @Override
    public void move(int row, int column, int roll) throws NoSuchLocationException, IllegalCommandException {
        validateMove(row, column, roll);
        board.removePiece(this.getRow(), this.getColumn());
        board.addPiece(this, row, column);
        this.currRow = row;
        this.currColumn = column;
        hasMoved = true;
    }

    @Override
    void validateMove(int row, int column, int roll) throws IllegalCommandException, NoSuchLocationException {
        if (!board.isEmpty(row, column)) {
            throw new IllegalCommandException("You can only move to empty locations.");
        }
        if (hasMoved) {
            throw new IllegalCommandException("You have already moved this piece this turn.");
        }
    }
    
    @Override
    public void attack(Piece target) throws IllegalCommandException, NoSuchLocationException {
        validateAttack(target);
        controller.recordAttack();
        this.hasAttacked = true;
        int[] rolls = Dice.simluateRolls();
        target.resolveAttack(this, rolls[0] + rolls[1]);
    }

    @Override
    void validateAttack(Piece target) throws IllegalCommandException {
        if (this.getController() == target.getController()) {
            throw new IllegalCommandException("You cannot attack your own piece.");
        }
        if (this.hasAttacked) {
            throw new IllegalCommandException("You have already attacked with this piece this turn.");
        }
        if (target.getRow() != this.getRow() && target.getColumn() != this.getColumn()) {
            throw new IllegalCommandException("You can only attack pieces that are aligned with your piece.");
        }
    }
    
    @Override
    public void resolveAttack(Piece attacker, int damage) throws NoSuchLocationException {
        int[] rolls = Dice.simluateRolls();
        if ((rolls[0] + rolls[1]) % 2 == 1) {
            System.out.println("Dodged!");
        } else {
            System.out.println("Hit!");
            Direction damageDirection = Direction.findRelativeDirection(
                    attacker.getRow(), attacker.getColumn(), this.getRow(), this.getColumn());
            applyDamage(damage, damageDirection);
        }
    }
    
    void applyDamage(int damage, Direction damageDirection) throws NoSuchLocationException {
        int[] newPosition = Direction.offsetInDirection(this.getRow(), this.getColumn(), damageDirection, damage);
        if (board.isLethalPosition(newPosition[0], newPosition[1])) {
            deletePiece();
        } else {
            board.removePiece(this.getRow(), this.getColumn());
            board.addPiece(this, newPosition[0], newPosition[1]);
        }
    }
    
    /** Called when this piece is removed from the game. */
    public void deletePiece() throws NoSuchLocationException {
        board.removePiece(this.getRow(), this.getColumn());
        controller.removePiece(this);
    }
    
    /** Resets the booleans that keep track of whether this piece has attacked 
     *  and moved this turn. */
    public void resetStatus() {
        hasAttacked = false;
        hasMoved = false;
    }
    
}
