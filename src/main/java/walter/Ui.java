package walter;

import walter.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    //ASCII art logos
    public static final String END_LOGO = "________              \n"
            + "___  __ )____  ______ \n"
            + "__  __  |_  / / /  _ \\\n"
            + "_  /_/ /_  /_/ //  __/\n"
            + "/_____/ _\\__, / \\___/ \n"
            + "        /____/        ";
    public static final String WALTER_LOGO = "____    __    ____  ___       __      .___________. _______ .______   \n"
            + "\\   \\  /  \\  /   / /   \\     |  |     |           ||   ____||   _  \\     \n"
            + " \\   \\/    \\/   / /  ^  \\    |  |     `---|  |----`|  |__   |  |_)  |    \n"
            + "  \\            / /  /_\\  \\   |  |         |  |     |   __|  |      /     \n"
            + "   \\    /\\    / /  _____  \\  |  `----.    |  |     |  |____ |  |\\  \\----.\n"
            + "    \\__/  \\__/ /__/     \\__\\ |_______|    |__|     |_______|| _| `._____|\n";

    //General UI messages
    public static final String MESSAGE_TASK_ADDED_CONFIRM = " Got it, I've added this task: ";
    public static final String MESSAGE_HELLO_FROM = "Hello from\n";
    public static final String MESSAGE_INTRO_GREETING = " Hello! I'm walter.walter  ◕_◕";
    public static final String MESSAGE_INTRO_WALTER_QUERY = " What can I do for you?";
    public static final String MESSAGE_CLOSING = " I'm sad to see you go. Hope to see you again soon! :D";
    public static final String MESSAGE_DOUBLE_WHITESPACE = "   ";
    public static final String MESSAGE_LINE_SEPARATOR =
            "______________________________________________________________________________";
    public static final String MESSAGE_NOW_YOU_HAVE = " Now you have ";
    public static final String MESSAGE_IN_THE_LIST = " in the list.";
    public static final String MESSAGE_ERROR_TASK_UNAVAILABLE = "No tasks available... (｡◕‿‿◕｡)";
    public static final String MESSAGE_TASKS_IN_LIST = " Here are the tasks in your list: ";

    //Exception error messages
    public static final String EXCEPTION_FILE_ERROR = "Oh no, something went wrong while creating a save file ;-;";
    public static final String EXCEPTION_INVALID_TASK_NUMBER = "Invalid task number entered... Please try again!";
    public static final String EXCEPTION_DONE_EXPECTED_INTEGER =
            "I'm sorry, I don't understand that ;-;. Please enter a number instead!";
    public static final String EXCEPTION_FILE_WRITE_ERROR = "Oh no, something went wrong while saving!";
    public static final String EXCEPTION_INVALID_DATE_FORMAT = "Please enter date in this format:\n[YYYY-MM-DD]";

    /** Prints separator component after text is printed */
    public void printSeparator() {
        System.out.println(MESSAGE_LINE_SEPARATOR);
    }

    /** Prints startup greet sequence */
    public void printStartupSequence() {
        System.out.println(MESSAGE_HELLO_FROM + WALTER_LOGO);
        printSeparator();
        System.out.println(MESSAGE_INTRO_GREETING);
        System.out.println(MESSAGE_INTRO_WALTER_QUERY);
        printSeparator();
    }

    /** Prints closing sequence */
    public void printClosingSequence() {
        printSeparator();
        System.out.println(MESSAGE_CLOSING);
        System.out.println(END_LOGO);
        printSeparator();
    }

    /** Reads user command and returns command */
    public String readUserCommand() {
        Scanner in = new Scanner(System.in);

        return in.nextLine();
    }

    /**
     * Prints the confirmation messages for setTaskAsDone and deleteTask
     *
     * @param message  The header message to inform the user whether action is set or delete
     * @param itemDetails  Details of the item that was set or deleted
     */
    public void printSetDeleteConfirmMessage(String message, String itemDetails) {
        printSeparator();
        System.out.println(message);
        System.out.println(" " + itemDetails);
        printSeparator();
    }

    /**
     * Print confirmation text when a new task is added
     *
     * @params tasks  Array of current stored tasks
     * @params taskCount  Current count of tasks stored
     */
    public void printTaskAddedConfirmation(ArrayList<Task> tasks) {
        printSeparator();
        System.out.println(MESSAGE_TASK_ADDED_CONFIRM);
        System.out.println(MESSAGE_DOUBLE_WHITESPACE + tasks.get(tasks.size() - 1).toString());
        System.out.println(MESSAGE_NOW_YOU_HAVE + tasks.size() + MESSAGE_IN_THE_LIST);
        printSeparator();
    }

    /**
     * Print list of tasks when user requests
     *
     * @params tasks  Array of current stored tasks
     */
    public void printTaskList(ArrayList<Task> tasks) {
        int taskNumber = 1;
        printSeparator();
        if (tasks.size() == 0) {
            System.out.println(MESSAGE_ERROR_TASK_UNAVAILABLE);
            return;
        }
        System.out.println(MESSAGE_TASKS_IN_LIST);
        for (Task task : tasks) {
            System.out.println(" " + taskNumber + "." + task);
            taskNumber++;
        }
        printSeparator();
    }

    public static void printScheduleForDay(ArrayList<Task> tasksOnDay) {
        
    }

    public void showLoadingError() {
        System.out.println(EXCEPTION_FILE_ERROR);
    }

    public void showWalterError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void showInvalidNumberError() {
        System.out.println(EXCEPTION_INVALID_TASK_NUMBER);
    }

    public void showInvalidInputError() {
        System.out.println(EXCEPTION_DONE_EXPECTED_INTEGER);
    }

    public void showFileSaveError() {
        System.out.println(EXCEPTION_FILE_WRITE_ERROR);
    }

    public static void showInvalidDateFormatError() {
        System.out.println(EXCEPTION_INVALID_DATE_FORMAT);
    }

}
