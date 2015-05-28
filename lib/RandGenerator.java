package lib;
import java.util.Random;


public class RandGenerator {

    private Random rand = new Random();
    
    /** Generates a random number between min and max excluding max.*/
    public int generateRand(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    /** Generates a random number between 0 and max excluding max. */
    public int generateRand(int max) {
        return generateRand(0, max);
    }
    
}
