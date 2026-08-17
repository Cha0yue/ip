/**
 * Adds a {@link Todo} to the task list.
 */
public class TodoCommand implements Command {
    private final String description;

    /**
     * Parses {@code todo DESCRIPTION}.
     *
     * @param arguments text after the command word
     * @return a todo command
     * @throws EkudException if the description is missing
     */
    public static TodoCommand parse(String arguments) throws EkudException {
        if (arguments.isBlank()) {
            throw new EkudException("The description of a todo cannot be empty.");
        }
        return new TodoCommand(arguments.trim());
    }

    /**
     * Creates a command that will add a todo with the given description.
     *
     * @param description text of the todo to add
     */
    private TodoCommand(String description) {
        this.description = description;
    }

    /**
     * Adds the todo to {@code tasks} and shows a confirmation.
     *
     * @param tasks the list to add to
     * @param ui    used to show the confirmation
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        Task task = new Todo(description);
        tasks.add(task);
        ui.showAdded(task, tasks.size());
    }
}
