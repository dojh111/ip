package walter;

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

public class TaskList {

    public static final String BLANK_SPACE = "";

    public static final String DEFAULT_DATE = "9999-12-31";
    public static final String DATE_FORMAT = "MMM d yyyy";

    public static final String COMMAND_TODO = "todo";
    public static final String COMMAND_FIND = "find";
    public static final String COMMAND_SCHEDULE = "schedule";

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
     * Adds new timed event tasks such as events or deadlines into the task list
     *
     * @params userInput  Original input by user
     * @params command  The command entered - Either event or deadline
     * @params eventIdentifier  Identifier to determine string information - Either /at or /by
     */
    public void addNewTimedEvent(String userInput, String command, String eventIdentifier) throws WalterException {
        String description;
        String additionalInformation;
        String unformattedDate = DEFAULT_DATE;
        ArrayList<String> dateInformation;

        String[] informationStrings = Parser.determineTaskInformation(userInput, command, eventIdentifier);

        Parser.checkForValidFieldEntered(informationStrings, command, eventIdentifier);

        //Set variables
        description = informationStrings[0].trim();
        additionalInformation = informationStrings[1].trim();

        dateInformation = Parser.determineDateInformation(additionalInformation);
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
            break;
        }
    }

    /** Adds todo task into taskList */
    public void addTodoTask(String userInput) throws WalterException {
        String taskDescription = Parser.removeCommandFromInput(userInput, COMMAND_TODO);

        Parser.checkForEmptySingleField(taskDescription, COMMAND_TODO);

        taskList.add(new Todo(taskDescription));
    }

    public void findTask(String userInput) throws WalterException {
        String searchTerm = Parser.removeCommandFromInput(userInput, COMMAND_FIND);

        Parser.checkForEmptySingleField(searchTerm, COMMAND_FIND);

        //Filter for tasks with searchterm using stream
        ArrayList<Task> searchResults = (ArrayList<Task>) taskList.stream()
                .filter((s) -> s.toString().contains(searchTerm))
                .collect(toList());

        Ui.printFilteredResults(searchResults, searchTerm, COMMAND_FIND);
    }

    public void getSchedule(String userInput) throws WalterException {
        String searchDate = Parser.removeCommandFromInput(userInput, COMMAND_SCHEDULE);

        Parser.checkForEmptySingleField(searchDate, COMMAND_SCHEDULE);

        try {
            LocalDate selectedDate = LocalDate.parse(searchDate);
            String inputDate = selectedDate.toString();
            String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));

            ArrayList<Task> tasksOnDay = (ArrayList<Task>) taskList.stream()
                    .filter((s) -> s.getDate().equals(inputDate))
                    .collect(toList());
            Ui.printFilteredResults(tasksOnDay, formattedDate, COMMAND_SCHEDULE);
        } catch (DateTimeParseException e) {
            Ui.showInvalidDateFormatError();
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

}
