/**
 * Turns a line of user input into a {@link Command}.
 * Command words are the first token and are case-sensitive.
 * Unrecognized input is treated as a new task.
 */
public class Parser {
    /**
     * Parses one line of input.
     *
     * @param input raw line from the user
     * @return the command to execute
     * @throws EkudException if the line is blank or a known command has invalid arguments
     */
    public static Command parse(String input) throws EkudException {
        if (input == null || input.isBlank()) {
            throw new EkudException("Please enter a task, or a command (list, mark, unmark, bye).");
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
            case "mark" -> new MarkCommand(parseOneBasedIndex("mark", arguments));
            case "unmark" -> new UnmarkCommand(parseOneBasedIndex("unmark", arguments));
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

    /**
     * Parses the single task number required by {@code mark} and {@code unmark}.
     *
     * @param commandWord the command being parsed, used in error messages
     * @param arguments   text after the command word
     * @return the task number, starting from 1
     * @throws EkudException if the number is missing, not an integer, or extra text is present
     */
    private static int parseOneBasedIndex(String commandWord, String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("Please provide a task number, e.g. " + commandWord + " 1.");
        }
        String[] tokens = arguments.split("\\s+");
        if (tokens.length != 1) {
            throw new EkudException("The \"" + commandWord + "\" command takes exactly one task number.");
        }
        try {
            return Integer.parseInt(tokens[0]);
        } catch (NumberFormatException e) {
            throw new EkudException("Task number must be an integer, e.g. " + commandWord + " 1.");
        }
    }
}
