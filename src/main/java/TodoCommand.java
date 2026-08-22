/**
 * Adds a {@link Todo} to the task list.
 */
public class TodoCommand implements TaskCreatingCommand {
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
     * Builds the todo without adding it to the list.
     * {@link Storage} uses this when loading a saved {@code todo} line.
     *
     * @return a new incomplete todo
     */
    @Override
    public Task createTask() {
        return new Todo(description);
    }

    /**
     * Adds the todo to {@code tasks}, saves the list, and shows a confirmation.
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
