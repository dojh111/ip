package walter;

import walter.exceptions.WalterException;
import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

public class TaskList {

    public static final String BLANK_SPACE = "";
    public static final String COMMAND_TODO = "todo";
    public static final String EXCEPTION_EMPTY_TODO = "Oh no! The description of the todo cannot be empty ;-;";
    public static final String COMMAND_FIND = "find";
    public static final String EXCEPTION_EMPTY_SEARCHTERM = "Oh no! The search term cannot be empty!";

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
     * @params userInput  Original input by user
     * @params command  The command entered - Either event or deadline
     * @params eventIdentifier  Identifier to determine string information - Either /at or /by
     */
    public void addNewTimedEvent(String userInput, String command, String eventIdentifier) throws WalterException {
        String description;
        String timeInformation;

        String[] informationStrings = Parser.determineTaskInformation(userInput, command, eventIdentifier);

        Parser.checkForValidFieldEntered(informationStrings, command, eventIdentifier);

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
     * @params splitUserInput Array of strings after original user input has been split by whitespace
     */
    public String setTaskAsDone (String[] splitUserInput) throws WalterException {
        Parser.checkForValidInput(splitUserInput);
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;
        String markedItemDetails;
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

    public void findTask(String userInput) throws WalterException {
        String searchTerm = Parser.removeCommandFromInput(userInput, COMMAND_FIND);

        //Check for exception where user input for task is empty
        if (searchTerm.equals(BLANK_SPACE)) {
            throw new WalterException(EXCEPTION_EMPTY_SEARCHTERM);
        }

        //Filter for tasks with searchterm using stream
        ArrayList<Task> searchResults = (ArrayList<Task>) taskList.stream()
                .filter((s) -> s.toString().contains(searchTerm))
                .collect(toList());
    }
}
