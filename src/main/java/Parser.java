/**
 * Turns a line of user input into a {@link Command}.
 * Command words are the first token and are case-sensitive.
 */
public class Parser {
    private static final String FLAG_BY = "/by";
    private static final String FLAG_FROM = "/from";
    private static final String FLAG_TO = "/to";

    /**
     * Parses one line of input.
     *
     * @param input raw line from the user
     * @return the command to execute
     * @throws EkudException if the line is blank or a known command has invalid arguments
     */
    public static Command parse(String input) throws EkudException {
        if (input == null || input.isBlank()) {
            throw new EkudException("Please enter a command (todo, deadline, event, list, mark, unmark, bye).");
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
            case "todo" -> parseTodo(arguments);
            case "deadline" -> parseDeadline(arguments);
            case "event" -> parseEvent(arguments);
            default -> throw new EkudException(
                    "I don't recognise that command. Try todo, deadline, event, list, mark, unmark, or bye.");
        };
    }

    /**
     * Parses {@code todo DESCRIPTION}.
     *
     * @param arguments text after the command word
     * @return a command that will add the todo
     * @throws EkudException if the description is missing
     */
    private static Command parseTodo(String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("The description of a todo cannot be empty.");
        }
        return new TodoCommand(arguments.trim());
    }

    /**
     * Parses {@code deadline DESCRIPTION /by WHEN}.
     *
     * @param arguments text after the command word
     * @return a command that will add the deadline
     * @throws EkudException if the description, {@code /by}, or date/time is missing
     */
    private static Command parseDeadline(String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("The description of a deadline cannot be empty.");
        }
        int byIndex = indexOfFlag(arguments, FLAG_BY);
        if (byIndex < 0) {
            throw new EkudException("Please provide a deadline using /by, e.g. deadline return book /by Sunday.");
        }
        String description = arguments.substring(0, byIndex).trim();
        String by = arguments.substring(byIndex + FLAG_BY.length()).trim();
        if (description.isEmpty()) {
            throw new EkudException("The description of a deadline cannot be empty.");
        }
        if (by.isEmpty()) {
            throw new EkudException("Please provide a date/time after /by, e.g. deadline return book /by Sunday.");
        }
        return new DeadlineCommand(description, by);
    }

    /**
     * Parses {@code event DESCRIPTION /from START /to END}.
     *
     * @param arguments text after the command word
     * @return a command that will add the event
     * @throws EkudException if the description, flags, or date/times are missing
     */
    private static Command parseEvent(String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("The description of an event cannot be empty.");
        }
        int fromIndex = indexOfFlag(arguments, FLAG_FROM);
        int toIndex = indexOfFlag(arguments, FLAG_TO);
        if (fromIndex < 0 || toIndex < 0) {
            throw new EkudException(
                    "Please provide both /from and /to, e.g. event meeting /from Mon 2pm /to 4pm.");
        }
        if (fromIndex > toIndex) {
            throw new EkudException(
                    "Please put /from before /to, e.g. event meeting /from Mon 2pm /to 4pm.");
        }
        String description = arguments.substring(0, fromIndex).trim();
        String from = arguments.substring(fromIndex + FLAG_FROM.length(), toIndex).trim();
        String to = arguments.substring(toIndex + FLAG_TO.length()).trim();
        if (description.isEmpty()) {
            throw new EkudException("The description of an event cannot be empty.");
        }
        if (from.isEmpty()) {
            throw new EkudException("Please provide a start date/time after /from.");
        }
        if (to.isEmpty()) {
            throw new EkudException("Please provide an end date/time after /to.");
        }
        return new EventCommand(description, from, to);
    }

    /**
     * Returns the index of {@code flag} as its own token, or {@code -1} if absent.
     * The flag must be bounded by whitespace or the ends of the string, so
     * {@code /by} inside {@code /bye} is not treated as a match.
     *
     * @param input text to search
     * @param flag  token to find, for example {@code /by}
     * @return starting index of the flag, or {@code -1}
     */
    private static int indexOfFlag(String input, String flag) {
        int searchFrom = 0;
        while (searchFrom <= input.length() - flag.length()) {
            int found = input.indexOf(flag, searchFrom);
            if (found < 0) {
                return -1;
            }
            boolean validStart = found == 0 || Character.isWhitespace(input.charAt(found - 1));
            int after = found + flag.length();
            boolean validEnd = after == input.length() || Character.isWhitespace(input.charAt(after));
            if (validStart && validEnd) {
                return found;
            }
            searchFrom = found + 1;
        }
        return -1;
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
