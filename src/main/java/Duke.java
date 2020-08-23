import java.util.Scanner;
import java.util.Arrays;

public class Duke {
    //Prints separator component after text is printed
    public static void printSeparator() {
        System.out.println("______________________________________________________________________________");
    }
    //Handles commands entered by the user
    public static void handleNewTask(String[] tasks, String newTask, int taskCount) {
        tasks[taskCount] = newTask;
        printSeparator();
        System.out.println(" Added task: " + newTask);
        printSeparator();
    }
    //Print list of tasks when user requests
    public static void printTaskList(String[] tasks) {
        int taskNumber = 1;
        printSeparator();
        if(tasks.length == 0) {
            System.out.println("No tasks available...");
        }
        for(String task : tasks) {
            System.out.println(" " + taskNumber + ": " + task);
            taskNumber++;
        }
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
        boolean isFinished = true;
        String[] tasks = new String[100];
        int taskCount = 0;
        Scanner in = new Scanner(System.in);

        System.out.println("Hello from\n" + logo);
        printSeparator();
        System.out.println(" Hello! I'm Walter  ◕_◕");
        System.out.println(" What can I do for you?");
        printSeparator();

        //Loop infinitely until user enters "bye"
        while(isFinished) {
            userCommand = in.nextLine();
            switch (userCommand) {
            case "bye":
                isFinished = false;
                break;
            case "list":
                printTaskList(Arrays.copyOf(tasks, taskCount));
                break;
            default:
                //Add new task into tasks array
                handleNewTask(tasks, userCommand, taskCount);
                taskCount++;
                break;
            }
        }

        printSeparator();
        System.out.println(" I'm sad to see you go ε(´סּ︵סּ`)з. Hope to see you again soon! ／人◕ ‿‿ ◕人＼");
        System.out.println(endLogo);
        printSeparator();
    }
}
