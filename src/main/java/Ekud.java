import java.util.Scanner;

/**
 * Entry point for the Ekud chatbot.
 * On startup it greets the user with a dad joke, then echoes commands
 * until the user types {@code bye}.
 */
public class Ekud {
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
     * Starts the chatbot: prints a greeting (including a dad joke), then
     * reads and echoes commands until the user types {@code bye}.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        greet();
        runCommandLoop();
    }

    /**
     * Prints the chatbot banner, a short welcome message, and a startup dad joke.
     */
    private static void greet() {
        printDivider();
        System.out.print(BANNER);
        System.out.println("Hello! I'm " + NAME + ".");
        printStartupJoke();
        System.out.println();
        System.out.println("Type a command, or bye to exit.");
        printDivider();
    }

    /**
     * Prints a dad joke from API Ninjas if one was fetched.
     * If the request fails (for example the API limit is exceeded), nothing is
     * printed so the greeting continues as usual.
     */
    private static void printStartupJoke() {
        String joke = DadJokeFetcher.fetch();
        if (joke != null) {
            System.out.println(joke);
        }
    }

    /**
     * Reads commands from standard input. Each command is echoed back until
     * {@code bye} is entered, which prints a goodbye message and ends the loop.
     */
    private static void runCommandLoop() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printPrompt();
            String command = scanner.nextLine();
            if (command.equals("bye")) {
                exit();
                break;
            }
            echo(command);
        }
        scanner.close();
    }

    /**
     * Prints the given command back to the user, wrapped in dividers.
     *
     * @param command the command text to echo
     */
    private static void echo(String command) {
        printDivider();
        System.out.println(command);
        printDivider();
    }

    /**
     * Prints the goodbye message. The program then ends.
     */
    private static void exit() {
        printDivider();
        System.out.println("Bye. Hope to see you again soon!");
        printDivider();
    }

    /**
     * Prints a prompt so it is clear the chatbot is waiting for typed input.
     */
    private static void printPrompt() {
        System.out.print("> ");
        System.out.flush();
    }

    /**
     * Prints a horizontal divider to separate chatbot messages.
     */
    private static void printDivider() {
        System.out.println(DIVIDER);
    }
}
