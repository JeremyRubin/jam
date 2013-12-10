package utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates sequential integer ID's. Each object that needs to issue unique
 * sequential ID's should have its own instance.
 * 
 */
public class SequentialIDGenerator {
    private AtomicInteger n;

    /**
     * Constructor for SequentialIDGenerator, initializing IDs starting from 0.
     */
    public SequentialIDGenerator() {
        this.n = new AtomicInteger(0);
    }

    /**
     * Returns n, then increments n.
     * 
     * @return n
     */
    public int getID() {
        return this.n.getAndIncrement();
    }
}
