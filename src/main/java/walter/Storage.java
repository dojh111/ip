package walter;

import walter.tasks.Deadline;
import walter.tasks.Event;
import walter.tasks.Task;
import walter.tasks.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    //File Path and other constants
    public static final String SAVE_DELIMITER = "--";
    public static final String FILE_MESSAGE_CREATED_SUCCESS = "Save file creation successful! :D";
    public static final String FILE_MESSAGE_NO_SAVE_DETECTED = "No previous saves detected! Creating save file...";
    public static final String TODO_ICON = "[T]";
    public static final String DEADLINE_ICON = "[D]";
    public static final String EVENT_ICON = "[E]";
    public static final String BLANK_STRING = "";

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Recreates tasks array by reading data from file. If no file available, create new file
     */
    public ArrayList<Task> readFileContents() throws IOException {
        ArrayList<Task> taskList = new ArrayList<>();
        File saveFile = new File(filePath);

        //Read from file if exists, else create new directory and files
        if (saveFile.exists()) {
            Scanner fileScanner = new Scanner(saveFile);
            //Re-create task objects in the array
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
                }
                //Set status of task to done if required
                if (Integer.parseInt(taskStatus) == 1) {
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
     * Writes data from the tasks array onto a file, so that data can be saved
     *
     * @param tasks  ArrayList of tasks to be written onto the file
     */
    public void writeToFile(ArrayList<Task> tasks) throws IOException {
        //Clearing file before writing
        FileWriter fwClear = new FileWriter(filePath);
        fwClear.write(BLANK_STRING);
        fwClear.close();

        //Append information into file
        FileWriter fileWriter = new FileWriter(filePath, true);
        for (Task task : tasks) {
            int taskStatus;
            //Determine status to write to file based on task status
            if (task.getStatus()) {
                taskStatus = 1;
            } else {
                taskStatus = 0;
            }
            //Create text string to write so save file
            String taskToSave = task.getTaskIcon() + SAVE_DELIMITER + taskStatus + SAVE_DELIMITER
                    + task.getDescription() + SAVE_DELIMITER + task.getTimingInformation() + SAVE_DELIMITER
                    + task.getDate() +System.lineSeparator();
            fileWriter.write(taskToSave);
        }
        fileWriter.close();
    }
}
