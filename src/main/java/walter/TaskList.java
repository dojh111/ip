package walter;

import walter.exceptions.WalterException;
import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

import java.io.IOException;
import java.util.ArrayList;

public class TaskList {

    public static final String BLANK_SPACE = "";
    public static final String COMMAND_TODO = "todo";
    public static final String EXCEPTION_TIMEDEVENT_INTRO = "Oh no! ;-;\nThe ";
    public static final String EXCEPTION_TIMEDEVENT_BODY =
            " command requires both description and time information in the format of: \n";
    public static final String EXCEPTION_TIMEDEVENT_DESCRIPTION = "[description] ";
    public static final String EXCEPTION_TIMEDEVENT_TIMEINFO = " [time information]";
    public static final String EXCEPTION_EMPTY_TODO = "Oh no! The description of the todo cannot be empty ;-;";

    public ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public TaskList() {
        this.taskList = new ArrayList<>();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Adds user input into the task list
     *
     * @params tasks  Array of current stored tasks
     * @params userInput  Original input by user
     */
    public void addTodoTask(String userInput) throws WalterException {
        String taskDescription = Parser.removeCommandFromInput(userInput, COMMAND_TODO);

        //Check for exception where user input for task is empty
        if (taskDescription.equals(BLANK_SPACE)) {
            throw new WalterException(EXCEPTION_EMPTY_TODO);
        }

        taskList.add(new Todo(taskDescription));
    }

    /**
     * Adds new timed event tasks such as events or deadlines into the task list
     *
     * @params tasks  Array of current stored tasks
     * @params userInput  Original input by user
     * @params taskCount  Current count of tasks stored
     * @params command  The command entered - Either event or deadline
     * @params eventIdentifier  Identifier to determine string information - Either /at or /by
     */
    public void addNewTimedEvent(String userInput, String command, String eventIdentifier) throws WalterException {
        String description;
        String timeInformation;
        boolean fieldsArePresent = true;

        String[] informationStrings = Parser.determineTaskInformation(userInput, command, eventIdentifier);

        //Check if both fields have been fulfilled
        for (String information : informationStrings) {
            if (information.equals(BLANK_SPACE)) {
                fieldsArePresent = false;
                break;
            }
        }

        //Check if additional information was given (TO REFACTOR INTO PARSER)
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
            taskList.add(new Event(description, timeInformation));
            break;
        case "deadline":
            taskList.add(new Deadline(description, timeInformation));
            break;
        default:
            break;
        }
    }

    /**
     * Sets isDone of selected task to true
     *
     * @params tasks  Array of current stored tasks
     * @params splitUserInput  Array of strings after original user input has been split by whitespace
     * @params taskCount  Current count of tasks stored
     */
    public String setTaskAsDone (String[] splitUserInput) throws WalterException {
        Parser.checkForValidInput(splitUserInput);
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;
        String markedItemDetails;

        //TaskNumber is valid
        taskList.get(taskNumber).setAsDone();
        markedItemDetails = taskList.get(taskNumber).toString();

        return markedItemDetails;
    }

    /**
     * Removes task from the tasks ArrayList
     *
     * @param splitUserInput Array of strings after original user input has been split by whitespace
     */
    public String deleteTask (String[]splitUserInput) throws WalterException {
        Parser.checkForValidInput(splitUserInput);
        int taskToDelete = Integer.parseInt(splitUserInput[1]) - 1;
        String deleteItemDetails = taskList.get(taskToDelete).toString();
        taskList.remove(taskToDelete);

        return deleteItemDetails;
    }
}
