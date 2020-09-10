package walter;

import walter.exceptions.WalterException;
import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

/**
 * Duke is a chat bot which can help the user do multiple tasks
 */
public class Walter {
    //Identifiers and values
    public static final String DEADLINE_IDENTIFIER = "/by";
    public static final String EVENT_IDENTIFIER = "/at";
    public static final String WHITESPACE_IDENTIFIER = " ";
    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";
    public static final String BLANK_SPACE = "";

    //Printed Messages
    public static final String MESSAGE_DOUBLE_WHITESPACE = "   ";
    public static final String MESSAGE_LINE_SEPARATOR =
            "______________________________________________________________________________";
    public static final String MESSAGE_NOW_YOU_HAVE = " Now you have ";
    public static final String MESSAGE_IN_THE_LIST = " in the list.";
    public static final String MESSAGE_ERROR_TASK_UNAVAILABLE = "No tasks available... (｡◕‿‿◕｡)";
    public static final String MESSAGE_TASKS_IN_LIST = " Here are the tasks in your list: ";
    public static final String MESSAGE_TASK_MARKED = "NICE! (｡◕‿‿◕｡) I've marked the task as done!:";
    public static final String MESSAGE_TASK_DELETED = "Alright! I've removed this task from the list:";
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

    public static void addTodoTask(ArrayList<Task> tasks, String userInput) throws WalterException, IOException {
        String taskDescription = removeCommandFromInput(userInput, COMMAND_TODO);

        //Check for exception where user input for task is empty
        if (taskDescription.equals(BLANK_SPACE)) {
            throw new WalterException(EXCEPTION_EMPTY_TODO);
        }

        tasks.add(new Todo(taskDescription));

        writeToFile(tasks);
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
    public static void addNewTimedEvent(ArrayList<Task> tasks, String userInput, String command,
            String eventIdentifier) throws WalterException, IOException {
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
            tasks.add(new Event(description, timeInformation));
            break;
        case "deadline":
            tasks.add(new Deadline(description, timeInformation));
            break;
        default:
            break;
        }

        writeToFile(tasks);
    }

    /**
     * Print confirmation text when a new task is added
     *
     * @params tasks  Array of current stored tasks
     * @params taskCount  Current count of tasks stored
     */
    public static void printTaskAddedConfirmation(ArrayList<Task> tasks) {
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
    public static void printTaskList(ArrayList<Task> tasks) {
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
     * Checks for invalid command and throws WalterException
     */
    public static void checkForValidInput(String[] splitUserInput) throws WalterException {
        if (splitUserInput.length == 1) {
            throw new WalterException(EXCEPTION_EMPTY_DONE);
        }
    }

    /**
     * Sets isDone of selected task to true
     *
     * @params tasks  Array of current stored tasks
     * @params splitUserInput  Array of strings after original user input has been split by whitespace
     * @params taskCount  Current count of tasks stored
     */
    public static void setTaskAsDone(ArrayList<Task> tasks, String[] splitUserInput) throws WalterException, IOException {
        checkForValidInput(splitUserInput);
        if (splitUserInput.length == 1) {
            throw new WalterException(EXCEPTION_EMPTY_DONE);
        }
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;
        String markedItemDetails;

        //TaskNumber is valid
        tasks.get(taskNumber).setAsDone();
        markedItemDetails = tasks.get(taskNumber).toString();

        printSetOrDeleteConfirmMessage(MESSAGE_TASK_MARKED, markedItemDetails);
    }

    /**
     * Removes task from the tasks ArrayList
     *
     * @param tasks  Array of current stored tasks
     * @param splitUserInput  Array of strings after original user input has been split by whitespace
     */
    public static void deleteTask(ArrayList<Task> tasks, String[] splitUserInput) throws WalterException {
        checkForValidInput(splitUserInput);
        int taskToDelete = Integer.parseInt(splitUserInput[1]) - 1;
        String deleteItemDetails = tasks.get(taskToDelete).toString();

        //TaskNumber is valid
        tasks.remove(taskToDelete);

        printSetOrDeleteConfirmMessage(MESSAGE_TASK_DELETED, deleteItemDetails);
    }

    /**
     * Prints the confirmation messages for setTaskAsDone and deleteTask
     *
     * @param message  The header message to inform the user whether action is set or delete
     * @param itemDetails  Details of the item that was set or deleted
     */
    public static void printSetOrDeleteConfirmMessage(String message, String itemDetails) {
        printSeparator();
        System.out.println(message);
        System.out.println(" " + itemDetails);
        printSeparator();
    }

    /**
     * Writes data from the tasks array onto a file, so that data can be saved
     *
     * @param tasks  ArrayList of tasks to be written onto the file
     */
    public static void writeToFile(ArrayList<Task> tasks) throws IOException {
        //Clearing entire file
        FileWriter fwClear = new FileWriter("data/walter.txt");
        fwClear.write("");
        fwClear.close();

        //Append information into file
        FileWriter fw = new FileWriter("data/walter.txt", true);
        for (Task task : tasks) {
            String taskToSave = task.getTaskIcon() + "@" + task.getStatusIcon() + "@"
                    + task.getDescription() + "@" + task.getTimingInformation() + System.lineSeparator();
            fw.write(taskToSave);
        }
        fw.close();
    }

    public static void readFileContents(ArrayList<Task> tasks) throws IOException {
        File f = new File("data/walter.txt");

        //Read from file if exists, else create new directory and files
        if (f.exists()) {
            Scanner s = new Scanner(f);
            //Re-create task objects in the array
            while (s.hasNext()) {
                String taskInformation = s.nextLine();
                String[] taskComponents = taskInformation.split("@");
                switch (taskComponents[0]) {
                case "[T]":
                    tasks.add(new Todo(taskComponents[2]));
                    break;
                case "[D]":
                    tasks.add(new Deadline(taskComponents[2], taskComponents[3]));
                    break;
                case "[E]":
                    tasks.add(new Event(taskComponents[2], taskComponents[3]));
                    break;
                }
            }
        } else {
            //No existing file detected. Create new directory and file
            boolean directoryCreated = f.mkdir();
            boolean fileCreated = f.createNewFile();
            if (directoryCreated && fileCreated) {
                System.out.println("New directory and files have been created!");
            }
        }
    }

    public static void main(String[] args) {
        //Initialise variables
        String userInput;
        String[] splitUserInput;
        ArrayList<Task> tasks = new ArrayList<>();
        boolean isFinished = true;
        Scanner in = new Scanner(System.in);

        //Print startup sequence
        printStartupSequence();

        //Read information from saved files
        try {
            readFileContents(tasks);
        } catch (IOException e) {
            System.out.println("Oh no, something went wrong ;-;");
        }

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
                    printTaskList(tasks);
                    break;
                case "done":
                    setTaskAsDone(tasks, splitUserInput);
                    break;
                case "delete":
                    deleteTask(tasks, splitUserInput);
                    break;
                case "todo":
                    addTodoTask(tasks, userInput);
                    printTaskAddedConfirmation(tasks);
                    break;
                case "deadline":
                    addNewTimedEvent(tasks, userInput, COMMAND_DEADLINE, DEADLINE_IDENTIFIER);
                    printTaskAddedConfirmation(tasks);
                    break;
                case "event":
                    addNewTimedEvent(tasks, userInput, COMMAND_EVENT, EVENT_IDENTIFIER);
                    printTaskAddedConfirmation(tasks);
                    break;
                default:
                    //Throw exception for invalid command - Break statement unreachable
                    throw new WalterException(EXCEPTION_INVALID_COMMAND);
                }

            } catch (WalterException e) {
                //Catch exceptions and print error messages unique to Walter
                System.out.println(e.getErrorMessage());

            } catch (NullPointerException | IndexOutOfBoundsException e) {
                //Catch exceptions when index given is out of bounds or invalid
                System.out.println(EXCEPTION_INVALID_TASK_NUMBER);

            } catch (NumberFormatException e) {
                //Catch exception when string is given for a field which requires number
                System.out.println(EXCEPTION_DONE_EXPECTED_INTEGER);
            } catch (IOException e) {
                System.out.println("Oh no, something went wrong while saving!");
            }
        }

        //Print closing sequence
        printClosingSequence();
    }

}
