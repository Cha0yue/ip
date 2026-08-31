package ekud;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ekud.storage.Storage;
import ekud.ui.Ui;

/**
 * Tests GUI-oriented replies from {@link Ekud#getResponse} without touching
 * the default save file.
 */
public class EkudTest {
    @TempDir
    Path tempDir;

    @Test
    public void getResponse_todo_returnsAddedMessage() {
        Ekud ekud = newChatbot();
        String response = ekud.getResponse("todo read book");
        assertTrue(response.contains("Got it. I've added this task:"));
        assertTrue(response.contains("[T][ ] read book"));
        assertTrue(response.contains("Now you have 1 task in the list."));
        assertEquals("AddCommand", ekud.getCommandType());
        assertFalse(ekud.isExit());
    }

    @Test
    public void getResponse_unknownCommand_returnsError() {
        Ekud ekud = newChatbot();
        String response = ekud.getResponse("blah");
        assertTrue(response.contains("I don't recognize that command"));
        assertEquals("", ekud.getCommandType());
        assertFalse(ekud.isExit());
    }

    @Test
    public void getResponse_listWhenEmpty_returnsEmptyMessage() {
        Ekud ekud = newChatbot();
        assertEquals("Your task list is empty.", ekud.getResponse("list"));
    }

    @Test
    public void getResponse_bye_setsExitAndShowsGoodbye() {
        Ekud ekud = newChatbot();
        String response = ekud.getResponse("bye");
        assertEquals("Bye. Hope to see you again soon!", response);
        assertTrue(ekud.isExit());
    }

    @Test
    public void getResponse_markThenDelete_updatesListAndStyles() {
        Ekud ekud = newChatbot();
        ekud.getResponse("todo read book");

        String marked = ekud.getResponse("mark 1");
        assertTrue(marked.contains("Nice! I've marked this task as done:"));
        assertTrue(marked.contains("[T][X] read book"));
        assertEquals("ChangeMarkCommand", ekud.getCommandType());

        String deleted = ekud.getResponse("delete 1");
        assertTrue(deleted.contains("Noted. I've removed this task:"));
        assertTrue(deleted.contains("Now you have 0 tasks in the list."));
        assertEquals("DeleteCommand", ekud.getCommandType());
        assertEquals("Your task list is empty.", ekud.getResponse("list"));
    }

    /**
     * Returns a chatbot that stores tasks in a temporary file.
     */
    private Ekud newChatbot() {
        return new Ekud(Ui.forGui(), new Storage(tempDir.resolve("ekud.txt").toString()));
    }
}
