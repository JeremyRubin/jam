package utils.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.SequentialIDGenerator;

public class SequentialIDGeneratorTest {

    @Test
    public void generatorTest() {
        SequentialIDGenerator g = new SequentialIDGenerator();
        for (int i = 0; i < 100; i++) {
            // initial value should be 0, then should continually increment up by 1
            assertEquals(i, g.getID());
        }
    }
}
