package lib;

import java.util.ArrayList;

import src.CharacterPiece;

public enum Player {

    NEUTRAL(false), PLAYER1(false), PLAYER2(false);

    private boolean hasAttacked;
    private ArrayList<CharacterPiece> livePieces;
    
    Player(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
        this.livePieces = new ArrayList<CharacterPiece>();
    }
    
    /** Returns true if this player still has pieces that are alive. */
    public boolean isAlive() {
        return livePieces.size() != 0;
    }
    
    /** Records that a piece controlled by this player was added. */
    public void addPiece(CharacterPiece newPiece) {
        livePieces.add(newPiece);
    }
    
    /** Records that a piece controlled by this player was deleted. */
    public void removePiece(CharacterPiece targetPiece) {
        livePieces.remove(targetPiece);
    }
    
    /** Records that this player has attacked this turn. */
    public void recordAttack() {
        hasAttacked = true;
    }
    
    /** Ensures that this player can attack. 
     * @throws IllegalCommandException */
    public void validateAttack() throws IllegalCommandException {
        if (hasAttacked) {
            throw new IllegalCommandException("You may only attack once a turn");
        }
    }
    
    /** Epilogue for a player's turn. */
    public void endTurn() {
        hasAttacked = false;
        for (CharacterPiece piece: livePieces) {
            piece.resetStatus();
        }
    }
    
}
