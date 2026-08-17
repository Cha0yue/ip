/**
 * Adds a {@link Deadline} to the task list.
 */
public class DeadlineCommand implements Command {
    private static final String FLAG_BY = "/by";

    private final String description;
    private final String by;

    /**
     * Parses {@code deadline DESCRIPTION /by WHEN}.
     *
     * @param arguments text after the command word
     * @return a deadline command
     * @throws EkudException if the description, {@code /by}, or date/time is missing
     */
    public static DeadlineCommand parse(String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("The description of a deadline cannot be empty.");
        }
        int byIndex = Parser.indexOfFlag(arguments, FLAG_BY);
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
     * Creates a command that will add a deadline.
     *
     * @param description text of the deadline to add
     * @param by          due date/time as typed by the user
     */
    private DeadlineCommand(String description, String by) {
        this.description = description;
        this.by = by;
    }

    /**
     * Adds the deadline to {@code tasks} and shows a confirmation.
     *
     * @param tasks the list to add to
     * @param ui    used to show the confirmation
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Deadline(description, by);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }
}
