/**
 * Adds an {@link Event} to the task list.
 */
public class EventCommand implements Command {
    private static final String FLAG_FROM = "/from";
    private static final String FLAG_TO = "/to";

    private final String description;
    private final String from;
    private final String to;

    /**
     * Parses {@code event DESCRIPTION /from START /to END}.
     *
     * @param arguments text after the command word
     * @return an event command
     * @throws EkudException if the description, flags, or date/times are missing
     */
    public static EventCommand parse(String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("The description of an event cannot be empty.");
        }
        int fromIndex = Parser.indexOfFlag(arguments, FLAG_FROM);
        int toIndex = Parser.indexOfFlag(arguments, FLAG_TO);
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
     * Creates a command that will add an event.
     *
     * @param description text of the event to add
     * @param from        start date/time as typed by the user
     * @param to          end date/time as typed by the user
     */
    private EventCommand(String description, String from, String to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /**
     * Adds the event to {@code tasks} and shows a confirmation.
     *
     * @param tasks the list to add to
     * @param ui    used to show the confirmation
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Event(description, from, to);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }
}
