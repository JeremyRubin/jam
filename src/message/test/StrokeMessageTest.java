package message.test;

import java.awt.Color;

import junit.framework.TestCase;
import message.StrokeMessage;

import org.junit.Test;

import drawable.DrawablePath;

public class StrokeMessageTest extends TestCase {

    @Test
    public void testEquals() {
        StrokeMessage test = new StrokeMessage(1, 2, new DrawablePath(), "id", "hello", new Color(255, 100, 30, 10), 5);
        assertTrue(test.equals(new StrokeMessage().fromJSON(test.toJSON().toJSONString())));
    }
}
