package walter.components;

import walter.exceptions.WalterException;
import walter.tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The UI class handles user input interface and prints messages to the user
 */
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
    public static final String MESSAGE_INTRO_GREETING = " Hello! I'm walter! :D";
    public static final String MESSAGE_INTRO_WALTER_QUERY = " What can I do for you?";
    public static final String MESSAGE_CLOSING = " I'm sad to see you go. Hope to see you again soon! :D";
    public static final String MESSAGE_DOUBLE_WHITESPACE = "   ";
    public static final String MESSAGE_LINE_SEPARATOR =
            "______________________________________________________________________________";
    public static final String MESSAGE_NOW_YOU_HAVE = " Now you have ";
    public static final String MESSAGE_IN_THE_LIST = " in the list.";
    public static final String MESSAGE_ERROR_TASK_UNAVAILABLE = "No tasks available... :D";
    public static final String MESSAGE_TASKS_IN_LIST = " Here are the tasks in your list: ";
    public static final String MESSAGE_CLEAR_CONFIRMED = "Done! All tasks have been cleared :)";

    //Exception error messages
    public static final String EXCEPTION_FILE_ERROR = "Oh no, something went wrong while creating a save file ;-;";
    public static final String EXCEPTION_INVALID_TASK_NUMBER = "Invalid task number entered... Please try again!";
    public static final String EXCEPTION_DONE_EXPECTED_INTEGER =
            "I'm sorry, I don't understand that ;-;. Please enter a number instead!";
    public static final String EXCEPTION_FILE_WRITE_ERROR = "Oh no, something went wrong while saving!";
    public static final String EXCEPTION_INVALID_DATE_FORMAT = "Please enter date in this format:\n[YYYY-MM-DD]";
    public static final String EXCEPTION_UNABLE_TO_DETERMINE_TASK = "Hmm, I could not determine the task, please "
            + "try again!";

    public static final String HELP_MENU =
            "=============================================================================\n"
            + "These are the commands that are available:\n"
            + "Notes about the command format:\n"
            + " * Words in UPPER_CASE are the parameters to be supplied by the user\n"
            + " * Items in square brackets are optional, e.g [DATE]\n"
            + "\n"
            + "COMMANDS\n"
            + "*****************************************************************************\n"
            + "help ------------------------ Displays all available commands on the terminal\n"
            + "todo DESCRIPTION ------------------------ Adds a todo task with a description\n"
            + "event DESCRIPTION /at DESCRIPTION [DATE] ----------------- Adds an event task\n"
            + "deadline DESCRIPTION /by DESCRIPTION [DATE ------------- Adds a deadline task\n"
            + "list ---------------------------- Displays all current tasks on the task list\n"
            + "find KEYWORD [KEYWORDS] ------------ Finds all tasks that contain the keyword\n"
            + "schedule DATE ----------------------------- Finds all tasks that fall on date\n"
            + "delete INDEX ------------------------ Deletes task at specified index of list\n"
            + "clear -------------------------------------- Deletes all tasks from task list\n"
            + "bye ------------------------------------------------------- Exits the program\n"
            + "*****************************************************************************\n"
            + "For more detailed information, please visit the online user guide at:\n"
            + "https://dojh111.github.io/ip/\n"
            + "=============================================================================\n";
    /**
     * Prints separator component after text is printed
     */
    public void printSeparator() {
        System.out.println(MESSAGE_LINE_SEPARATOR);
    }

    /**
     * Prints startup greet sequence
     */
    public void printStartupSequence() {
        System.out.println(MESSAGE_HELLO_FROM + WALTER_LOGO);
        printSeparator();
        System.out.println(MESSAGE_INTRO_GREETING);
        System.out.println(MESSAGE_INTRO_WALTER_QUERY);
        printSeparator();
    }

    /**
     * Prints closing sequence
     */
    public void printClosingSequence() {
        printSeparator();
        System.out.println(MESSAGE_CLOSING);
        System.out.println(END_LOGO);
        printSeparator();
    }

    /**
     * Returns read user command
     */
    public String readUserCommand() {
        Scanner in = new Scanner(System.in);

        return in.nextLine();
    }

    /**
     * Prints the confirmation messages for setTaskAsDone and deleteTask
     *
     * @param message The header message to inform the user whether action is set or delete
     * @param itemDetails Details of the item that was set or deleted
     */
    public void printSetDeleteConfirmMessage(String message, String itemDetails) {
        printSeparator();
        System.out.println(message);
        System.out.println(" " + itemDetails);
        printSeparator();
    }

    /**
     * Prints confirmation text when a new task is added
     *
     * @param tasks Array of current stored tasks
     */
    public void printTaskAddedConfirmation(ArrayList<Task> tasks) {
        printSeparator();
        System.out.println(MESSAGE_TASK_ADDED_CONFIRM);
        System.out.println(MESSAGE_DOUBLE_WHITESPACE + tasks.get(tasks.size() - 1).toString());
        System.out.println(MESSAGE_NOW_YOU_HAVE + tasks.size() + MESSAGE_IN_THE_LIST);
        printSeparator();
    }

    /**
     * Prints list of current tasks
     *
     * @param tasks Array of current stored tasks
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

    /**
     * Prints returned search results for schedule and find commands
     *
     * @param filteredTasks The ArrayList of Tasks that were filtered to contain the searchterm
     * @param filterField Searchterm
     * @param command Either "find" or "schedule"
     */
    public void printFilteredResults(ArrayList<Task> filteredTasks, String filterField, String command)
            throws WalterException {
        printSeparator();
        if (isFilteredTasksEmpty(filteredTasks, filterField, command)) {
            return;
        }
        printFilteredTaskMessage(filterField, command);
        printFilteredTasksList(filteredTasks);
        printSeparator();
    }

    /**
     * Returns true when there are empty search results
     *
     * @param filteredTasks The ArrayList of Tasks that were filtered to contain the searchterm
     * @param filterField Searchterm
     * @param command Either "find" or "schedule"
     */
    public boolean isFilteredTasksEmpty(ArrayList<Task> filteredTasks, String filterField, String command)
            throws WalterException {
        boolean isEmpty = false;
        if (filteredTasks.size() == 0) {
            switch (command) {
            case "find":
                System.out.println("I could not find any tasks with the word: " + filterField);
                isEmpty = true;
                break;
            case "schedule":
                System.out.println("You have nothing scheduled on: " + filterField);
                isEmpty = true;
                break;
            default:
                throw new WalterException(EXCEPTION_UNABLE_TO_DETERMINE_TASK);
            }
        }
        return isEmpty;
    }

    /**
     * Prints the header message for schedule and find functions
     *
     * @param filterField Searchterm
     * @param command Either "find" or "schedule"
     */
    public void printFilteredTaskMessage(String filterField, String command) throws WalterException {
        switch (command) {
        case "find":
            System.out.println("This is what I have found for: " + filterField);
            break;
        case "schedule":
            System.out.println("Here are the events you have on " + filterField + ":");
            break;
        default:
            throw new WalterException(EXCEPTION_UNABLE_TO_DETERMINE_TASK);
        }
    }

    /**
     * Prints all tasks that were filtered out
     *
     * @param filteredTasks The ArrayList of Tasks that were filtered to contain the searchterm
     */
    public void printFilteredTasksList (ArrayList<Task> filteredTasks) {
        int taskCount = 1;

        for (Task task : filteredTasks) {
            String taskNumber = " " + taskCount + ". ";
            System.out.println(taskNumber + task.toString());
            taskCount++;
        }
    }

    /**
     * Prints the help menu
     */
    public void showHelpMenu() {
        System.out.println(HELP_MENU);
    }

    /**
     * Prints error message when file creation fails
     */
    public void showLoadingError() {
        System.out.println(EXCEPTION_FILE_ERROR);
    }

    /**
     * Prints error messages from thrown WalterExceptions
     *
     * @param errorMessage The message to be displayed according to the error
     */
    public void showWalterError(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Prints error message when invalid number is entered
     */
    public void showInvalidNumberError() {
        System.out.println(EXCEPTION_INVALID_TASK_NUMBER);
    }

    /**
     * Prints error message when invalid input is entered
     */
    public void showInvalidInputError() {
        System.out.println(EXCEPTION_DONE_EXPECTED_INTEGER);
    }

    /**
     * Prints error message when file error occurs
     */
    public void showFileSaveError() {
        System.out.println(EXCEPTION_FILE_WRITE_ERROR);
    }

    /**
     * Prints error message when an invalid date format is entered
     */
    public void showInvalidDateFormatError() {
        System.out.println(EXCEPTION_INVALID_DATE_FORMAT);
    }

    /**
     * Prints confirmation message for taskList being cleared
     */
    public void printClearTaskListConfirmation() {
        printSeparator();
        System.out.println(MESSAGE_CLEAR_CONFIRMED);
    }

}
