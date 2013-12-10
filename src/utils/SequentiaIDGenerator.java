package utils;

/**
 * Generates sequential integer ID's
 * 
 * each class that needs unique sequential ID's should have its own instance
 * 
 */
public class SequentiaIDGenerator {
    private int n;

    public SequentiaIDGenerator() {
        this.n = 0;
    }

    public int getID() {
        return this.n++;
    }
}
