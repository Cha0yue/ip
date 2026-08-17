/**
 * Ends the chatbot session after printing a goodbye message.
 * This command does not take arguments.
 */
public class ByeCommand implements Command {
    /**
     * Parses {@code bye} with no extra text.
     *
     * @param arguments text after the command word
     * @return a bye command
     * @throws EkudException if extra arguments are present
     */
    public static ByeCommand parse(String arguments) throws EkudException {
        Parser.requireNoArguments("bye", arguments);
        return new ByeCommand();
    }

    private ByeCommand() {
    }

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
