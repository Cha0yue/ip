/**
 * A user command that can be executed against the task list.
 * Each concrete command owns its own validation and behaviour so new
 * commands can be added without growing {@link Ekud}.
 */
public interface Command {
    /**
     * Carries out this command.
     *
     * @param tasks the task list to read or update
     * @param ui    used to show results to the user
     * @throws EkudException if the command cannot be executed
     */
    void execute(TaskList tasks, Ui ui) throws EkudException;

    /**
     * Returns whether the chatbot should stop after this command.
     *
     * @return {@code true} only for the exit command
     */
    default boolean isExit() {
        return false;
    }
}
