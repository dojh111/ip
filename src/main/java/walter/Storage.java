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

    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Recreates tasks array by reading data from file. If no file available, create new file
     */
    public ArrayList<Task> readFileContents() throws IOException {
        ArrayList<Task> taskList = new ArrayList<>();
        File f = new File(filePath);

        //Read from file if exists, else create new directory and files
        if (f.exists()) {
            Scanner s = new Scanner(f);
            //Re-create task objects in the array
            while (s.hasNext()) {
                String taskInformation = s.nextLine();
                String[] taskComponents = taskInformation.split(SAVE_DELIMITER);
                switch (taskComponents[0]) {
                case TODO_ICON:
                    taskList.add(new Todo(taskComponents[2]));
                    break;
                case DEADLINE_ICON:
                    taskList.add(new Deadline(taskComponents[2], taskComponents[3]));
                    break;
                case EVENT_ICON:
                    taskList.add(new Event(taskComponents[2], taskComponents[3]));
                    break;
                }
                //Set status of task to done if required
                if (Integer.parseInt(taskComponents[1]) == 1) {
                    taskList.get(taskList.size() - 1).setAsDone();
                }
            }
        } else {
            //No existing file detected. Create new save file
            System.out.println(FILE_MESSAGE_NO_SAVE_DETECTED);
            boolean fileCreated = f.createNewFile();
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
        fwClear.write("");
        fwClear.close();

        //Append information into file
        FileWriter fw = new FileWriter(filePath, true);
        for (Task task : tasks) {
            int taskStatus;
            //Determine status to write to file based on task status
            if (task.getStatus()) {
                taskStatus = 1;
            } else {
                taskStatus = 0;
            }
            String taskToSave = task.getTaskIcon() + SAVE_DELIMITER + taskStatus + SAVE_DELIMITER
                    + task.getDescription() + SAVE_DELIMITER + task.getTimingInformation() + System.lineSeparator();
            fw.write(taskToSave);
        }
        fw.close();
    }
}
