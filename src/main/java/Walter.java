import java.util.Scanner;
import java.util.Arrays;

/**
 * Duke is a chat bot which can help the user do multiple tasks
 */
public class Walter {
    //Identifiers and values
    public static final int ARRAY_SIZE_TASKS = 100;
    public static final String DEADLINE_IDENTIFIER = "/by";
    public static final String EVENT_IDENTIFIER = "/at";
    public static final String WHITESPACE_IDENTIFIER = " ";
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";
    public static final String REPLACEMENT_BLANK_SPACES = "";
    public static final String TICK_ICON = "\u2713";

    //Printed Messages
    public static final String MESSAGE_DOUBLE_WHITESPACE = "   ";
    public static final String MESSAGE_LINE_SEPARATOR =
            "______________________________________________________________________________";
    public static final String MESSAGE_NOW_YOU_HAVE = " Now you have ";
    public static final String MESSAGE_IN_THE_LIST = " in the list.";
    public static final String MESSAGE_ERROR_TASK_UNAVAILABLE = "No tasks available... (｡◕‿‿◕｡)";
    public static final String MESSAGE_TASKS_IN_LIST = " Here are the tasks in your list: ";
    public static final String MESSAGE_ERROR_INVALID_COMMAND = "Invalid command entered... (ㆆ _ ㆆ)";
    public static final String MESSAGE_ERROR_INVALID_NUMBER = "Invalid task number entered... (ㆆ _ ㆆ)";
    public static final String MESSAGE_TASK_MARKED = "NICE! (｡◕‿‿◕｡) I've marked the task as done!:";
    public static final String MESSAGE_TASK_ADDED_CONFIRM = " Got it, I've added this task: ";
    public static final String MESSAGE_HELLO_FROM = "Hello from\n";
    public static final String MESSAGE_INTRO_GREETING = " Hello! I'm Walter  ◕_◕";
    public static final String MESSAGE_INTRO_WALTER_QUERY = " What can I do for you?";
    public static final String MESSAGE_CLOSING = " I'm sad to see you go. Hope to see you again soon! :D";

    //Exception Messages
    public static final String EXCEPTION_INVALID_COMMAND = "I do not know what that means ;-;, please try again!";

    //ASCII art logos
    public static final String END_LOGO = "________              \n"
            + "___  __ )____  ______ \n"
            + "__  __  |_  / / /  _ \\\n"
            + "_  /_/ /_  /_/ //  __/\n"
            + "/_____/ _\\__, / \\___/ \n"
            + "        /____/        ";
    public static final String WALTER_LOGO = "____    __    ____  ___       __      .___________. _______ .______      \n"
            + "\\   \\  /  \\  /   / /   \\     |  |     |           ||   ____||   _  \\     \n"
            + " \\   \\/    \\/   / /  ^  \\    |  |     `---|  |----`|  |__   |  |_)  |    \n"
            + "  \\            / /  /_\\  \\   |  |         |  |     |   __|  |      /     \n"
            + "   \\    /\\    / /  _____  \\  |  `----.    |  |     |  |____ |  |\\  \\----.\n"
            + "    \\__/  \\__/ /__/     \\__\\ |_______|    |__|     |_______|| _| `._____|\n";

    /** Prints separator component after text is printed */
    public static void printSeparator() {
        System.out.println(MESSAGE_LINE_SEPARATOR);
    }

    /**
     * Prints startup greet sequence
     *
     * @params logo  The logo for Walter chat bot
     */
    public static void printStartupSequence() {
        System.out.println(MESSAGE_HELLO_FROM + WALTER_LOGO);
        printSeparator();
        System.out.println(MESSAGE_INTRO_GREETING);
        System.out.println(MESSAGE_INTRO_WALTER_QUERY);
        printSeparator();
    }

    /**
     * Prints closing sequence
     *
     * @params endLogo  The closing sequence logo for Walter
     */
    public static void printClosingSequence() {
        printSeparator();
        System.out.println(MESSAGE_CLOSING);
        System.out.println(END_LOGO);
        printSeparator();
    }

    /**
     * Removes the command passed into the method and replaces it with white space
     *
     * @params userInput  Original string typed by user and to be modified
     * @params commandToRemove  Command to be removed from string
     */
    public static String removeCommandFromInput(String userInput, String commandToRemove) {
        String modifiedUserInput = userInput.replace(commandToRemove, REPLACEMENT_BLANK_SPACES);
        return modifiedUserInput.trim();
    }

    /**
     * Returns a string array with the task's description and additional information
     *
     * @params userInput Original string typed by user and to be modified
     * @params commandToRemove  Command to be removed from string
     * @params identifier  Identifier token to split the string with
     **/
    public static String[] determineTaskInformation(String userInput, String commandToRemove, String identifier) {
        String modifiedString = removeCommandFromInput(userInput, commandToRemove);
        return modifiedString.split(identifier);
    }

    /**
     * Adds user input into the task list
     *
     * @params tasks  Array of current stored tasks
     * @params userInput  Original input by user
     * @params taskCount  Current count of tasks stored
     */
    public static void addTodoTask(Task[] tasks, String userInput, int taskCount) {
        tasks[taskCount] = new Todo(removeCommandFromInput(userInput, COMMAND_TODO));
    }

    /**
     * Adds new deadline task into the task list
     *
     * @params tasks  Array of current stored tasks
     * @params userInput  Original input by user
     * @params taskCount  Current count of tasks stored
     */
    public static void addDeadlineTask(Task[] tasks, String userInput, int taskCount) {
        String description;
        String by;
        String[] informationStrings = determineTaskInformation(userInput, COMMAND_DEADLINE, DEADLINE_IDENTIFIER);
        description = informationStrings[0].trim();
        by = informationStrings[1].trim();
        tasks[taskCount] = new Deadline(description, by);
    }

    /**
     * Adds new event task into the task list
     *
     * @params tasks  Array of current stored tasks
     * @params userInput  Original input by user
     * @params taskCount  Current count of tasks stored
     * */
    public static void addEventTask(Task[] tasks, String userInput, int taskCount) {
        String description;
        String at;
        String[] informationStrings = determineTaskInformation(userInput, COMMAND_EVENT, EVENT_IDENTIFIER);
        description = informationStrings[0].trim();
        at = informationStrings[1].trim();
        tasks[taskCount] = new Event(description, at);
    }

    /**
     * Print confirmation text when a new task is added
     *
     * @params tasks  Array of current stored tasks
     * @params taskCount  Current count of tasks stored
     */
    public static void printTaskAddedConfirmation(Task[] tasks, int taskCount) {
        int numberOfTasks = taskCount + 1;
        printSeparator();
        System.out.println(MESSAGE_TASK_ADDED_CONFIRM);
        System.out.println(MESSAGE_DOUBLE_WHITESPACE + tasks[taskCount]);
        System.out.println(MESSAGE_NOW_YOU_HAVE + numberOfTasks + MESSAGE_IN_THE_LIST);
        printSeparator();
    }

    /**
     * Print list of tasks when user requests
     *
     * @params tasks  Array of current stored tasks
     */
    public static void printTaskList(Task[] tasks) {
        int taskNumber = 1;
        printSeparator();
        if (tasks.length == 0) {
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
     * Sets isDone of selected task to true
     *
     * @params tasks  Array of current stored tasks
     * @params splitUserInput  Array of strings after original user input has been split by whitespace
     * @params taskCount  Current count of tasks stored
     */
    public static void setTaskAsDone(Task[] tasks, String[] splitUserInput, int taskCount) {
        //Determine index of task to be marked as done - this part can pass in broken array straight (To change)
        if (splitUserInput.length == 1) {
            System.out.println(MESSAGE_ERROR_INVALID_COMMAND);
            printSeparator();
            return;
        }
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;
        printSeparator();

        //Check if taskNumber is out of bounds
        if (taskNumber < 0 || taskNumber > taskCount - 1) {
            System.out.println(MESSAGE_ERROR_INVALID_NUMBER);
            printSeparator();
            return;
        }

        //TaskNumber is valid
        tasks[taskNumber].setAsDone();
        System.out.println(MESSAGE_TASK_MARKED);
        System.out.println("  [" + TICK_ICON + "] " + tasks[taskNumber].description);
        printSeparator();
    }

    public static void main(String[] args) {
        //Initialise variables
        String userInput;
        String[] splitUserInput;
        Task[] tasks = new Task[ARRAY_SIZE_TASKS];
        int taskCount = 0;
        boolean isFinished = true;
        Scanner in = new Scanner(System.in);

        //Print startup sequence
        printStartupSequence();

        //Loop infinitely until user enters "bye"
        while (isFinished) {
            userInput = in.nextLine();
            splitUserInput = userInput.split(WHITESPACE_IDENTIFIER);
            try {
                switch (splitUserInput[0]) {
                case "bye":
                    isFinished = false;
                    break;
                case "list":
                    printTaskList(Arrays.copyOf(tasks, taskCount));
                    break;
                case "done":
                    setTaskAsDone(tasks, splitUserInput, taskCount);
                    break;
                case "todo":
                    addTodoTask(tasks, userInput, taskCount);
                    printTaskAddedConfirmation(tasks, taskCount);
                    taskCount++;
                    break;
                case "deadline":
                    addDeadlineTask(tasks, userInput, taskCount);
                    printTaskAddedConfirmation(tasks, taskCount);
                    taskCount++;
                    break;
                case "event":
                    addEventTask(tasks, userInput, taskCount);
                    printTaskAddedConfirmation(tasks, taskCount);
                    taskCount++;
                    break;
                default:
                    //Throw exception for invalid command - Break statement unreachable
                    throw new WalterException();
                }
            } catch (WalterException e) {
                System.out.println(EXCEPTION_INVALID_COMMAND);
            }
        }

        //Print closing sequence
        printClosingSequence();
    }

}
