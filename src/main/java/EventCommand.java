/**
 * Adds an {@link Event} to the task list.
 */
public class EventCommand implements TaskCreatingCommand {
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
     * Builds the event without adding it to the list.
     * {@link Storage} uses this when loading a saved {@code event} line.
     *
     * @return a new incomplete event
     */
    @Override
    public Task createTask() {
        return new Event(description, from, to);
    }

    /**
     * Adds the event to {@code tasks}, saves the list, and shows a confirmation.
     *
     * @param tasks   the list to add to
     * @param ui      used to show the confirmation
     * @param storage used to persist the updated list
     * @throws EkudException if the list cannot be saved
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EkudException {
        Task task = createTask();
        tasks.add(task);
        storage.save(tasks);
        ui.showAdded(task, tasks.size());
    }
}
