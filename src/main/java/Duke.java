import java.util.Scanner;

public class Duke {
    //Prints separator component after text is printed
    public static void printSeparator() {
        System.out.println("______________________________________________________________________________");
    }
    //Handles commands entered by the user
    public static void handleUserCommand(String command) {
        printSeparator();
        System.out.println(" " + command);
        printSeparator();
    }
    public static void main(String[] args) {
        String logo = "____    __    ____  ___       __      .___________. _______ .______      \n"
                + "\\   \\  /  \\  /   / /   \\     |  |     |           ||   ____||   _  \\     \n"
                + " \\   \\/    \\/   / /  ^  \\    |  |     `---|  |----`|  |__   |  |_)  |    \n"
                + "  \\            / /  /_\\  \\   |  |         |  |     |   __|  |      /     \n"
                + "   \\    /\\    / /  _____  \\  |  `----.    |  |     |  |____ |  |\\  \\----.\n"
                + "    \\__/  \\__/ /__/     \\__\\ |_______|    |__|     |_______|| _| `._____|\n";
        String endLogo = "________              \n"
                + "___  __ )____  ______ \n"
                + "__  __  |_  / / /  _ \\\n"
                + "_  /_/ /_  /_/ //  __/\n"
                + "/_____/ _\\__, / \\___/ \n"
                + "        /____/        ";
        String userCommand;
        Scanner in = new Scanner(System.in);

        System.out.println("Hello from\n" + logo);
        printSeparator();
        System.out.println(" Hello! I'm Walter  ◕_◕");
        System.out.println(" What can I do for you?");
        printSeparator();

        //Loop infinitely until user enters "bye"
        while(true) {
            userCommand = in.nextLine();
            if(userCommand.equals("bye")) {
                break;
            }
            handleUserCommand(userCommand);
        }

        printSeparator();
        System.out.println(" I'm sad to see you go ε(´סּ︵סּ`)з. Hope to see you again soon! ／人◕ ‿‿ ◕人＼");
        System.out.println(endLogo);
        printSeparator();
    }
}
