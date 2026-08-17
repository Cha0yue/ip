/**
 * Entry point for the Ekud chatbot.
 * On startup it greets the user, then reads commands until {@code bye}.
 * Command parsing, task storage, and printing are delegated to
 * {@link Parser}, {@link TaskList}, and {@link Ui} respectively.
 */
public class Ekud {
    private final Ui ui;
    private final TaskList tasks;

    /**
     * Creates a chatbot with an empty in-memory task list.
     */
    public Ekud() {
        this.ui = new Ui();
        this.tasks = new TaskList();
    }

    /**
     * Starts the chatbot: prints a greeting (including a dad joke), then
     * reads and runs commands until the user types {@code bye}.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new Ekud().run();
    }

    /**
     * Runs the welcome screen and the main command loop.
     */
    public void run() {
        ui.showWelcome();
        boolean isRunning = true;
        while (isRunning) {
            ui.showPrompt();
            String input = ui.readCommand();
            try {
                Command command = Parser.parse(input);
                command.execute(tasks, ui);
                isRunning = !command.isExit();
            } catch (EkudException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }
}
