package ekud.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CommandType} keyword lookup and help text.
 */
public class CommandTypeTest {
    @Test
    public void fromKeyword_knownWord_returnsType() {
        assertEquals(CommandType.TODO, CommandType.fromKeyword("todo"));
        assertEquals(CommandType.BYE, CommandType.fromKeyword("bye"));
    }

    @Test
    public void fromKeyword_unknownWord_returnsNull() {
        assertNull(CommandType.fromKeyword("hello"));
        assertNull(CommandType.fromKeyword("TODO"));
    }

    @Test
    public void getHelpList_includesAllKeywords() {
        assertEquals(
                "todo, deadline, event, list, on, mark, unmark, delete, or bye",
                CommandType.getHelpList());
    }
}
