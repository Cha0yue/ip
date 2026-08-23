package ekud.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ekud.EkudException;
import ekud.command.ByeCommand;
import ekud.command.Command;
import ekud.command.CommandType;
import ekud.command.DeadlineCommand;
import ekud.command.EventCommand;
import ekud.command.FindCommand;
import ekud.command.ListCommand;
import ekud.command.MarkCommand;
import ekud.command.TaskCreatingCommand;
import ekud.command.TodoCommand;
import ekud.task.Deadline;
import ekud.task.Event;
import ekud.task.Task;
import ekud.task.Todo;

/**
 * Tests {@link Parser} command recognition, argument checks, and flag matching.
 */
public class ParserTest {
    @Test
    public void parse_todo_returnsTodoCommand() throws EkudException {
        Command command = Parser.parse("todo read book");
        assertInstanceOf(TodoCommand.class, command);
        Task task = ((TaskCreatingCommand) command).createTask();
        assertInstanceOf(Todo.class, task);
        assertEquals("[T][ ] read book", task.toString());
    }

    @Test
    public void parse_deadline_returnsDeadlineCommand() throws EkudException {
        Command command = Parser.parse("deadline return book /by 2019-12-02");
        assertInstanceOf(DeadlineCommand.class, command);
        Task task = ((TaskCreatingCommand) command).createTask();
        assertInstanceOf(Deadline.class, task);
        assertEquals("[D][ ] return book (by: Dec 02 2019)", task.toString());
    }

    @Test
    public void parse_event_returnsEventCommand() throws EkudException {
        Command command = Parser.parse("event meeting /from 2019-12-02 1400 /to 2019-12-02 1600");
        assertInstanceOf(EventCommand.class, command);
        Task task = ((TaskCreatingCommand) command).createTask();
        assertInstanceOf(Event.class, task);
        assertEquals("[E][ ] meeting (from: Dec 02 2019, 2:00 PM to: Dec 02 2019, 4:00 PM)",
                task.toString());
    }

    @Test
    public void parse_listAndBye_success() throws EkudException {
        assertInstanceOf(ListCommand.class, Parser.parse("list"));
        Command bye = Parser.parse("bye");
        assertInstanceOf(ByeCommand.class, bye);
        assertTrue(bye.isExit());
        assertFalse(Parser.parse("list").isExit());
    }

    @Test
    public void parse_mark_returnsMarkCommand() throws EkudException {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
    }

    @Test
    public void parse_find_returnsFindCommand() throws EkudException {
        assertInstanceOf(FindCommand.class, Parser.parse("find book"));
        assertInstanceOf(FindCommand.class, Parser.parse("find read book"));
    }

    @Test
    public void parse_findWithoutKeyword_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class, () -> Parser.parse("find"));
        assertEquals("Please provide a keyword, e.g. find book.", exception.getMessage());
        assertThrows(EkudException.class, () -> Parser.parse("find   "));
    }

    @Test
    public void parse_trimsWhitespace() throws EkudException {
        assertInstanceOf(ListCommand.class, Parser.parse("  list  "));
        Task task = ((TaskCreatingCommand) Parser.parse("  todo   read book  ")).createTask();
        assertEquals("read book", task.getDescription());
    }

    @Test
    public void parse_blankInput_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class, () -> Parser.parse(""));
        assertEquals("Please enter a command (" + CommandType.getHelpList() + ").", exception.getMessage());
        assertThrows(EkudException.class, () -> Parser.parse("   "));
        assertThrows(EkudException.class, () -> Parser.parse(null));
    }

    @Test
    public void parse_unknownCommand_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class, () -> Parser.parse("hello"));
        assertEquals("I don't recognize that command. Try " + CommandType.getHelpList() + ".",
                exception.getMessage());
    }

    @Test
    public void parse_commandWordsAreCaseSensitive() {
        assertThrows(EkudException.class, () -> Parser.parse("TODO read book"));
        assertThrows(EkudException.class, () -> Parser.parse("List"));
    }

    @Test
    public void parse_listWithArguments_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class, () -> Parser.parse("list all"));
        assertEquals("The \"list\" command does not take any arguments.", exception.getMessage());
    }

    @Test
    public void parse_todoWithoutDescription_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class, () -> Parser.parse("todo"));
        assertEquals("The description of a todo cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_deadlineMissingBy_throwsEkudException() {
        assertThrows(EkudException.class, () -> Parser.parse("deadline return book"));
    }

    @Test
    public void parse_eventEndBeforeStart_throwsEkudException() {
        EkudException exception = assertThrows(EkudException.class,
                () -> Parser.parse("event meeting /from 2019-12-02 1600 /to 2019-12-02 1400"));
        assertEquals("The event end date/time cannot be before the start date/time.",
                exception.getMessage());
    }

    @Test
    public void parseOneBasedIndex_missingOrInvalid_throwsEkudException() {
        EkudException missing = assertThrows(EkudException.class,
                () -> Parser.parseOneBasedIndex("mark", ""));
        assertEquals("Please provide a task number, e.g. mark 1.", missing.getMessage());

        EkudException extra = assertThrows(EkudException.class,
                () -> Parser.parseOneBasedIndex("delete", "1 2"));
        assertEquals("The \"delete\" command takes exactly one task number.", extra.getMessage());

        EkudException notNumber = assertThrows(EkudException.class,
                () -> Parser.parseOneBasedIndex("unmark", "abc"));
        assertEquals("Task number must be an integer, e.g. unmark 1.", notNumber.getMessage());
    }

    @Test
    public void parseOneBasedIndex_validNumber_success() throws EkudException {
        assertEquals(3, Parser.parseOneBasedIndex("mark", "3"));
    }

    @Test
    public void indexOfFlag_matchesWholeTokenOnly() {
        assertEquals(12, Parser.indexOfFlag("return book /by 2019-12-02", "/by"));
        // "/by" inside "/bye" must not count as the deadline flag.
        assertEquals(-1, Parser.indexOfFlag("say goodbye /bye later", "/by"));
        assertEquals(-1, Parser.indexOfFlag("no flags here", "/by"));
    }
}
