import com.mycompany.mavenproject3.Message;
//fetching message class from message.java

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageHashIsConsistent() {
        Message msg1 = new Message("001", "+27831234567", "Where are you?", 1);
        Message msg2 = new Message("001", "+27831234567", "Where are you?", 1);

        assertEquals(msg1.getHashedMessage(), msg2.getHashedMessage(),
                "Hash should be the same for identical message content and ID.");
    }

    @Test
    public void testMessageHashIsDifferent() {
        Message msg1 = new Message("001", "+27831234567", "Hi", 1);
        Message msg2 = new Message("002", "+27831234567", "Hi", 1);

        assertNotEquals(msg1.getHashedMessage(), msg2.getHashedMessage(),
                "Hash should be different for different message IDs.");
    }

    @Test
    public void testGetters() {
        String id = "999";
        String cell = "+27831111111";
        String body = "Test message";
        int flag = 2;

        Message msg = new Message(id, cell, body, flag);

        assertEquals(id, msg.getMessageID());
        assertEquals(cell, msg.getRecipientCell());
        assertEquals(body, msg.getMessage());
        assertEquals(flag, msg.getFlag());
        assertNotNull(msg.getHashedMessage(), "Hashed message should not be null.");
    }

    @Test
    public void testHashIsDeterministic() {
        Message msg = new Message("A1", "+27123456789", "Check hash!", 1);
        String hash1 = msg.getHashedMessage();
        String hash2 = msg.getHashedMessage();

        assertEquals(hash1, hash2, "Calling getHashedMessage() repeatedly should return the same result.");
    }

    @Test
    public void testHashIsNotEmpty() {
        Message msg = new Message("123", "+27830000000", "Test", 1);
        assertFalse(msg.getHashedMessage().isEmpty(), "Hash should not be empty.");
    }
}
