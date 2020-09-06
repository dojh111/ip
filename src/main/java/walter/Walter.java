package walter;

import walter.exceptions.WalterException;
import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

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
    public static final String BLANK_SPACE = "";
    public static final String TICK_ICON = "\u2713";

    //Printed Messages
    public static final String MESSAGE_DOUBLE_WHITESPACE = "   ";
    public static final String MESSAGE_LINE_SEPARATOR =
            "______________________________________________________________________________";
    public static final String MESSAGE_NOW_YOU_HAVE = " Now you have ";
    public static final String MESSAGE_IN_THE_LIST = " in the list.";
    public static final String MESSAGE_ERROR_TASK_UNAVAILABLE = "No tasks available... (｡◕‿‿◕｡)";
    public static final String MESSAGE_TASKS_IN_LIST = " Here are the tasks in your list: ";
    public static final String MESSAGE_TASK_MARKED = "NICE! (｡◕‿‿◕｡) I've marked the task as done!:";
    public static final String MESSAGE_TASK_ADDED_CONFIRM = " Got it, I've added this task: ";
    public static final String MESSAGE_HELLO_FROM = "Hello from\n";
    public static final String MESSAGE_INTRO_GREETING = " Hello! I'm walter.walter  ◕_◕";
    public static final String MESSAGE_INTRO_WALTER_QUERY = " What can I do for you?";
    public static final String MESSAGE_CLOSING = " I'm sad to see you go. Hope to see you again soon! :D";

    //Exception Messages
    public static final String EXCEPTION_INVALID_COMMAND = "I do not know what that means ;-;, please try again!";
    public static final String EXCEPTION_EMPTY_TODO = "Oh no! The description of the todo cannot be empty ;-;";
    public static final String EXCEPTION_INVALID_TASK_NUMBER = "Invalid task number entered... Please try again!";
    public static final String EXCEPTION_EMPTY_DONE = "Oh no... You have to enter a task number. Please try again!";
    public static final String EXCEPTION_TIMEDEVENT_INTRO = "Oh no! ;-;\nThe ";
    public static final String EXCEPTION_TIMEDEVENT_BODY =
            " command requires both description and time information in the format of: \n";
    public static final String EXCEPTION_TIMEDEVENT_DESCRIPTION = "[description] ";
    public static final String EXCEPTION_TIMEDEVENT_TIMEINFO = " [time information]";
    public static final String EXCEPTION_DONE_EXPECTED_INTEGER =
            "I'm sorry, I don't understand that ;-;. Please enter a number instead!";

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
     * @params logo  The logo for walter.walter chat bot
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
     * @params endLogo  The closing sequence logo for walter.walter
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
        String modifiedUserInput = userInput.replace(commandToRemove, BLANK_SPACE);
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
    public static void addTodoTask(Task[] tasks, String userInput, int taskCount) throws WalterException {
        String taskDescription = removeCommandFromInput(userInput, COMMAND_TODO);

        //Check for exception where user input for task is empty
        if (taskDescription.equals(BLANK_SPACE)) {
            throw new WalterException(EXCEPTION_EMPTY_TODO);
        }

        tasks[taskCount] = new Todo(taskDescription);
    }

    /**
     * Adds new timed event tasks such as events or deadlines into the task list
     *
     * @params tasks  Array of current stored tasks
     * @params userInput  Original input by user
     * @params taskCount  Current count of tasks stored
     * @params command  The command entered - Either event or deadline
     * @params eventIdentifier  Identifier to determine string information - Either /at or /by
     * */
    public static void addNewTimedEvent(Task[] tasks, String userInput, int taskCount, String command,
            String eventIdentifier) throws WalterException {
        String description;
        String timeInformation;
        boolean fieldsArePresent = true;

        String[] informationStrings = determineTaskInformation(userInput, command, eventIdentifier);

        //Check if both fields have been fulfilled
        for (String information : informationStrings) {
            if (information.equals(BLANK_SPACE)) {
                fieldsArePresent = false;
                break;
            }
        }

        //Check if additional information was given
        if (informationStrings.length < 2 || !fieldsArePresent) {
            String exceptionMessage = EXCEPTION_TIMEDEVENT_INTRO + command +
                    EXCEPTION_TIMEDEVENT_BODY +
                    EXCEPTION_TIMEDEVENT_DESCRIPTION + eventIdentifier + EXCEPTION_TIMEDEVENT_TIMEINFO;
            throw new WalterException(exceptionMessage);
        }

        //Set variables
        description = informationStrings[0].trim();
        timeInformation = informationStrings[1].trim();

        //Create new task objects
        switch (command) {
        case "event":
            tasks[taskCount] = new Event(description, timeInformation);
            break;
        case "deadline":
            tasks[taskCount] = new Deadline(description, timeInformation);
            break;
        default:
            break;
        }
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
    public static void setTaskAsDone(Task[] tasks, String[] splitUserInput) throws WalterException {
        //Determine index of task to be marked as done
        if (splitUserInput.length == 1) {
            throw new WalterException(EXCEPTION_EMPTY_DONE);
        }
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;

        //TaskNumber is valid
        tasks[taskNumber].setAsDone();
        printSeparator();
        System.out.println(MESSAGE_TASK_MARKED);
        System.out.println("  [" + TICK_ICON + "] " + tasks[taskNumber].getDescription());
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
                    setTaskAsDone(tasks, splitUserInput);
                    break;
                case "todo":
                    addTodoTask(tasks, userInput, taskCount);
                    printTaskAddedConfirmation(tasks, taskCount);
                    taskCount++;
                    break;
                case "deadline":
                    addNewTimedEvent(tasks, userInput, taskCount, COMMAND_DEADLINE, DEADLINE_IDENTIFIER);
                    printTaskAddedConfirmation(tasks, taskCount);
                    taskCount++;
                    break;
                case "event":
                    addNewTimedEvent(tasks, userInput, taskCount, COMMAND_EVENT, EVENT_IDENTIFIER);
                    printTaskAddedConfirmation(tasks, taskCount);
                    taskCount++;
                    break;
                default:
                    //Throw exception for invalid command - Break statement unreachable
                    throw new WalterException(EXCEPTION_INVALID_COMMAND);
                }

            } catch (WalterException e) {
                //Catch exceptions and print error messages unique to Walter
                System.out.println(e.getErrorMessage());

            } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                //Catch exceptions when index given is out of bounds or invalid
                System.out.println(EXCEPTION_INVALID_TASK_NUMBER);

            } catch (NumberFormatException e) {
                //Catch exception when string is given for a field which requires number
                System.out.println(EXCEPTION_DONE_EXPECTED_INTEGER);
            }
        }

        //Print closing sequence
        printClosingSequence();
    }

}
