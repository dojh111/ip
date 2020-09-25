package walter.components;

import walter.exceptions.WalterException;
import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import static java.util.stream.Collectors.toList;

/**
 * The TaskList class handles actions on the individual tasks of the taskList
 */
public class TaskList {

    public static final String DEFAULT_DATE = "9999-12-31";
    public static final String DATE_FORMAT = "MMM d yyyy";

    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_FIND = "find";
    public static final String COMMAND_SCHEDULE = "schedule";

    public static final String EXCEPTION_UNDETERMINABLE_TASK_TYPE = "Oh no, I could not determine the entered "
            + "task type!";

    private Ui ui;
    private Parser parse;

    private ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
        ui = new Ui();
        parse = new Parser();
    }

    public TaskList() {
        this.taskList = new ArrayList<>();
        ui = new Ui();
        parse = new Parser();
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Adds new timed event tasks such as events or deadlines into the task list
     *
     * @param userInput Original input by user
     * @param command The command entered - Either event or deadline
     * @param eventIdentifier Identifier to determine string information - Either /at or /by
     */
    public void addNewTimedEvent(String userInput, String command, String eventIdentifier) throws WalterException {
        String unformattedDate = DEFAULT_DATE;
        String[] informationStrings = parse.determineTaskInformation(userInput, command, eventIdentifier);

        parse.checkForValidFieldEntered(informationStrings, command, eventIdentifier);

        String description = informationStrings[0].trim();
        String additionalInformation = informationStrings[1].trim();

        ArrayList<String> dateInformation = parse.determineDateInformation(additionalInformation);

        //Pull information from arrayList and replace description with formatted date
        if (dateInformation.size() == 2) {
            unformattedDate = dateInformation.get(0);
            String formattedDate = dateInformation.get(1);
            additionalInformation = additionalInformation.replace(unformattedDate, formattedDate);
        }

        //Create new task objects
        switch (command) {
        case "event":
            taskList.add(new Event(description, additionalInformation, unformattedDate));
            break;
        case "deadline":
            taskList.add(new Deadline(description, additionalInformation, unformattedDate));
            break;
        default:
            throw new WalterException(EXCEPTION_UNDETERMINABLE_TASK_TYPE);
        }
    }

    /**
     * Adds a todo task into taskList
     *
     * @param userInput Unaltered input from user
     */
    public void addTodoTask(String userInput) throws WalterException {
        String taskDescription = parse.removeCommandFromInput(userInput, COMMAND_TODO);

        parse.checkForEmptySingleField(taskDescription, COMMAND_TODO);

        taskList.add(new Todo(taskDescription));
    }

    /**
     * Searches for tasks that include the search term and prints the results of the search
     *
     * @param userInput Unaltered input from user
     */
    public void findTask(String userInput) throws WalterException {
        String searchTerm = parse.removeCommandFromInput(userInput, COMMAND_FIND);

        parse.checkForEmptySingleField(searchTerm, COMMAND_FIND);

        ArrayList<Task> searchResults = filterTaskBySearchTerm(searchTerm);

        ui.printFilteredResults(searchResults, searchTerm, COMMAND_FIND);
    }

    /**
     * Returns an ArrayList of tasks that is filtered by the search term using streams
     *
     * @param searchTerm Keyword that has to be included in task
     */
    public ArrayList<Task> filterTaskBySearchTerm(String searchTerm) {
        return (ArrayList<Task>) taskList.stream()
                .filter((s) -> s.toString().contains(searchTerm))
                .collect(toList());
    }

    /**
     * Searches for dated objects that matches date entered by user and prints the results of the search
     *
     * @param userInput Unaltered input from user
     */
    public void getSchedule(String userInput) throws WalterException {
        String searchDate = parse.removeCommandFromInput(userInput, COMMAND_SCHEDULE);

        parse.checkForEmptySingleField(searchDate, COMMAND_SCHEDULE);

        try {
            //Parsing string into date object to ensure date format is correct
            LocalDate selectedDate = LocalDate.parse(searchDate);
            String inputDate = selectedDate.toString();
            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));

            ArrayList<Task> tasksOnDay = filterTaskByDate(inputDate);

            ui.printFilteredResults(tasksOnDay, formattedDate, COMMAND_SCHEDULE);
        } catch (DateTimeParseException e) {
            ui.showInvalidDateFormatError();
        }
    }

    /**
     * Returns an ArrayList of objects whose date matches the input date
     *
     * @param inputDate Date in string format that has to match task object date
     */
    public ArrayList<Task> filterTaskByDate(String inputDate) {
        return (ArrayList<Task>) taskList.stream()
                .filter((s) -> s.getDate().equals(inputDate))
                .collect(toList());
    }

    /**
     * Sets isDone of selected task to true
     *
     * @param splitUserInput Array of strings after original user input has been split by whitespace
     */
    public String setTaskAsDone (String[] splitUserInput) throws WalterException {
        parse.checkForValidInput(splitUserInput);
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;
        taskList.get(taskNumber).setAsDone();

        return taskList.get(taskNumber).toString();
    }

    /**
     * Removes task from the tasks ArrayList
     *
     * @param splitUserInput Array of strings after original user input has been split by whitespace
     */
    public String deleteTask (String[]splitUserInput) throws WalterException {
        parse.checkForValidInput(splitUserInput);
        int taskToDelete = Integer.parseInt(splitUserInput[1]) - 1;
        String deleteItemDetails = taskList.get(taskToDelete).toString();
        taskList.remove(taskToDelete);

        return deleteItemDetails;
    }

    public void clearTaskList() {
        taskList.clear();
    }
}
