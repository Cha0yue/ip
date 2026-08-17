/**
 * Entry point for the Ekud chatbot.
 * On startup it greets the user, then exits with a goodbye message.
 */
public class Duke {
    private static final String DIVIDER = "____________________________________________________________";
    private static final String NAME = "Ekud";
    // Credits: https://patorjk.com/software/taag/#p=testall&f=Alpha&t=EKUD&x=none&v=4&h=4&w=80&we=false
    private static final String BANNER = """
            ███████╗██╗  ██╗██╗   ██╗██████╗\s
            ██╔════╝██║ ██╔╝██║   ██║██╔══██╗
            █████╗  █████╔╝ ██║   ██║██║  ██║
            ██╔══╝  ██╔═██╗ ██║   ██║██║  ██║
            ███████╗██║  ██╗╚██████╔╝██████╔╝
            ╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝\s
            """;

    /**
     * Starts the chatbot: prints a greeting, then exits.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        greet();
        exit();
    }

    /**
     * Prints the chatbot banner and a short welcome message.
     */
    private static void greet() {
        printDivider();
        System.out.print(BANNER);
        System.out.println("Hello! I'm " + NAME + ".");
        System.out.println("What can I do for you?");
        printDivider();
    }

    /**
     * Prints the goodbye message. The program then ends.
     */
    private static void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
    }

    /**
     * Prints a horizontal divider to separate chatbot messages.
     */
    private static void printDivider() {
        System.out.println(DIVIDER);
    }
}
