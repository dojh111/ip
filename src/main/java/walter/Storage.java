package walter;

import walter.exceptions.WalterException;
import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The storage class handles all file reading and writing operations for Walter
 */
public class Storage {

    //File Path and other constants
    public static final String SAVE_DELIMITER = "--";
    public static final String FILE_MESSAGE_CREATED_SUCCESS = "Save file creation successful! :D";
    public static final String FILE_MESSAGE_NO_SAVE_DETECTED = "No previous saves detected! Creating save file...";
    public static final String TODO_ICON = "[T]";
    public static final String DEADLINE_ICON = "[D]";
    public static final String EVENT_ICON = "[E]";
    public static final String BLANK_STRING = "";

    public static final String EXCEPTION_FAILED_IDENTIFICATION = "Oh no, something went wrong while determining "
            + "the task type!";

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Builds and returns an ArrayList of tasks from the save file. If no save file is found, a new file is created
     *
     * @return ArrayList of tasks
     */
    public ArrayList<Task> readFileContents() throws IOException, WalterException {
        ArrayList<Task> taskList = new ArrayList<>();
        File saveFile = new File(filePath);

        //Read from file if file exists, else create a new save file
        if (saveFile.exists()) {
            Scanner fileScanner = new Scanner(saveFile);
            //Re-create task objects and add to the ArrayList
            while (fileScanner.hasNext()) {
                String taskInformation = fileScanner.nextLine();
                String[] taskComponents = taskInformation.split(SAVE_DELIMITER);
                String taskIcon = taskComponents[0];
                String taskStatus = taskComponents[1];
                String taskDescription = taskComponents[2];
                String taskTimingInformation;
                String taskDate;
                switch (taskIcon) {
                case TODO_ICON:
                    taskList.add(new Todo(taskDescription));
                    break;
                case DEADLINE_ICON:
                    taskTimingInformation = taskComponents[3];
                    taskDate = taskComponents[4];
                    taskList.add(new Deadline(taskDescription, taskTimingInformation, taskDate));
                    break;
                case EVENT_ICON:
                    taskTimingInformation = taskComponents[3];
                    taskDate = taskComponents[4];
                    taskList.add(new Event(taskDescription, taskTimingInformation, taskDate));
                    break;
                default:
                    throw new WalterException(EXCEPTION_FAILED_IDENTIFICATION);
                }
                //Set status of task to done if required
                if (isTaskDone(taskStatus)) {
                    taskList.get(taskList.size() - 1).setAsDone();
                }
            }
        } else {
            //No existing file detected. Create new save file
            System.out.println(FILE_MESSAGE_NO_SAVE_DETECTED);
            boolean fileCreated = saveFile.createNewFile();
            if (fileCreated) {
                System.out.println(FILE_MESSAGE_CREATED_SUCCESS);
            }
        }
        return taskList;
    }

    /**
     * Returns true if task is saved as done
     *
     * @param taskStatus The status of the task, can be 1 for true or 0 for false
     */
    public boolean isTaskDone(String taskStatus) {
        return Integer.parseInt(taskStatus) == 1;
    }

    /**
     * Writes data from the tasks array onto a file. File data is cleared first before writing
     *
     * @param tasks ArrayList of tasks to be written onto the file
     */
    public void writeToFile(ArrayList<Task> tasks) throws IOException {
        //Clear file before writing
        FileWriter fwClear = new FileWriter(filePath);
        fwClear.write(BLANK_STRING);
        fwClear.close();

        //Append information into file
        FileWriter fileWriter = new FileWriter(filePath, true);
        for (Task task : tasks) {
            int taskStatus = determineTaskStatus(task);
            String taskToSave = generateSaveText(task, taskStatus);
            fileWriter.write(taskToSave);
        }
        fileWriter.close();
    }

    /**
     * Returns the current status of the task. 1 is returned when task is marked as done and 0 is returned if task
     * is marked as undone
     *
     * @param task Current task object
     */
    public int determineTaskStatus(Task task) {
        int taskStatus = 0;

        if (task.getStatus()) {
            taskStatus = 1;
        }

        return taskStatus;
    }

    /**
     * Returns the string to be saved on the Walter save file
     *
     * @param task Current task object
     * @param taskStatus Task object status represented in integer form
     */
    public String generateSaveText(Task task, int taskStatus) {
        return task.getTaskIcon() + SAVE_DELIMITER + taskStatus + SAVE_DELIMITER
                + task.getDescription() + SAVE_DELIMITER + task.getTimingInformation() + SAVE_DELIMITER
                + task.getDate() +System.lineSeparator();
    }
}
