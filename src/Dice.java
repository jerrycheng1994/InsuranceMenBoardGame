package src;
import lib.RandGenerator;


public final class Dice {

    private static final RandGenerator randGen = new RandGenerator();
    
    private Dice() {
        throw new UnsupportedOperationException();
    }
    
    /** Rolls the two dice for the game. */
    public static int[] simluateRolls() {
        int[] rolls = {rollDice(3), rollDice(3)};
        return rolls;
    }
    
    /** Simulates rolling a NUMSIDES sided dice. */
    public static int rollDice(int numSides) {
        int roll = randGen.generateRand(numSides) + 1;
        System.out.println("Rolled a " + roll + ".");
        return roll;
    }
    
}
