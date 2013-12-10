package utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates sequential integer ID's
 * 
 * each class that needs unique sequential ID's should have its own instance
 * 
 */
public class SequentialIDGenerator {
    private AtomicInteger n;

    public SequentialIDGenerator() {
        this.n = new AtomicInteger(0);
    }

    public int getID() {
        return this.n.getAndIncrement();
    }
}
