/**
 * Turns a line of user input into a {@link Command}.
 * Command words are the first token and are case-sensitive.
 * Unrecognized input is treated as a new task
 */
public class Parser {
    /**
     * Parses one line of input.
     *
     * @param input raw line from the user
     * @return the command to execute
     * @throws EkudException if the line is blank or a known command has extra arguments
     */
    public static Command parse(String input) throws EkudException {
        if (input == null || input.isBlank()) {
            throw new EkudException("Please enter a task, or a command (list, bye).");
        }

        String trimmed = input.trim();
        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";

        return switch (commandWord) {
        case "list" -> {
            requireNoArguments("list", arguments);
            yield new ListCommand();
        }
        case "bye" -> {
            requireNoArguments("bye", arguments);
            yield new ByeCommand();
        }
        default -> new AddCommand(trimmed);
        };
    }

    /**
     * Rejects leftover text after a command that takes no arguments.
     *
     * @param commandWord the command being checked
     * @param arguments   text after the command word
     * @throws EkudException if {@code arguments} is not blank
     */
    private static void requireNoArguments(String commandWord, String arguments) throws EkudException {
        if (!arguments.isBlank()) {
            throw new EkudException("The \"" + commandWord + "\" command does not take any arguments.");
        }
    }
}
