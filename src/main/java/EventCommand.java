/**
 * Adds an {@link Event} to the task list.
 */
public class EventCommand implements Command {
    private final String description;
    private final String from;
    private final String to;

    /**
     * Creates a command that will add an event.
     *
     * @param description text of the event to add
     * @param from        start date/time as typed by the user
     * @param to          end date/time as typed by the user
     */
    public EventCommand(String description, String from, String to) {
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
