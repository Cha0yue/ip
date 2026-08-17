/**
 * Ends the chatbot session after printing a goodbye message.
 * This command does not take arguments; the {@link Parser} rejects extra text.
 */
public class ByeCommand implements Command {
    /**
     * Shows the goodbye message. The chatbot then exits because {@link #isExit()}
     * is {@code true}.
     *
     * @param tasks unused
     * @param ui    used to show the goodbye message
     */
    @Override
    public void execute(TaskList tasks, Ui ui) {
        ui.showGoodbye();
    }

    /**
     * Signals that the command loop should stop.
     *
     * @return {@code true}
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
