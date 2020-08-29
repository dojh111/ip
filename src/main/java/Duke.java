import java.util.Scanner;
import java.util.Arrays;

/**
 * Duke is a chat bot which can help the user do multiple tasks
 */
public class Duke {
    /** Prints separator component after text is printed */
    public static void printSeparator() {
        System.out.println("______________________________________________________________________________");
    }

    /** Adds user input into the task list */
    public static void addTodoTask(Task[] tasks, String[] splitUserInput, int taskCount) {
        String description = "";
        for (int i = 1; i < splitUserInput.length; i++) {
            description += splitUserInput[i] + " ";
        }
        tasks[taskCount] = new Todo(description);
    }

    /** Adds new deadline task into the task list */
    public static void addDeadlineTask(Task[] tasks, String[] splitUserInput, int taskCount) {
        String description;
        String by;
        int indexOfBy = 0;
        for (int i = 0; i < splitUserInput.length; i++) {
            if (splitUserInput[i].equals("/by")) {
                indexOfBy = i;
                break;
            }
        }
        description = determineDescription(splitUserInput, indexOfBy);
        by = determineAdditionInformation(splitUserInput, indexOfBy);
        tasks[taskCount] = new Deadline(description, by);
    }

    /** Adds new event task into the task list */
    public static void addEventTask(Task[] tasks, String[] splitUserInput, int taskCount) {
        String description;
        String at;
        int indexOfAt = 0;
        for (int i = 0; i < splitUserInput.length; i++) {
            if (splitUserInput[i].equals("/at")) {
                indexOfAt = i;
                break;
            }
        }
        description = determineDescription(splitUserInput, indexOfAt);
        at = determineAdditionInformation(splitUserInput, indexOfAt);
        tasks[taskCount] = new Event(description, at);
    }

    /** Determines the description of user input task */
    public static String determineDescription(String[] splitUserInput, int indexOfBy) {
        String description = "";
        for (int i = 0; i < indexOfBy; i++) {
            description += splitUserInput[i] + " ";
        }
        return description;
    }

    /** Determines additional information of user input task */
    public static String determineAdditionInformation(String[] splitUserInput, int indexOfBy) {
        String additionalInformation = "";
        for (int i = indexOfBy + 1; i < splitUserInput.length; i++) {
            additionalInformation += splitUserInput[i] + " ";
        }
        return additionalInformation;
    }

    /** Print confirmation text when a new task is added */
    public static void printTaskAddedConfirmation(Task[] tasks, int taskCount) {
        int numberOfTasks = taskCount + 1;
        printSeparator();
        System.out.println(" Got it, I've added this task: ");
        System.out.println("   " + tasks[taskCount]);
        System.out.println(" Now you have " + numberOfTasks + " in the list.");
        printSeparator();
    }

    /** Print list of tasks when user requests */
    public static void printTaskList(Task[] tasks) {
        int taskNumber = 1;
        printSeparator();
        if (tasks.length == 0) {
            System.out.println("No tasks available... (｡◕‿‿◕｡)");
            return;
        }
        System.out.println(" Here are the tasks in your list: ");
        for (Task task : tasks) {
            System.out.println(" " + taskNumber + "." + task);
            taskNumber++;
        }
        printSeparator();
    }

    /** Sets isDone of selected task to true */
    public static void setTaskAsDone(Task[] tasks, String[] splitUserInput, int taskCount) {
        //Determine index of task to be marked as done - this part can pass in broken array straight (To change)
        if (splitUserInput.length == 1) {
            System.out.println("Invalid command entered... (ㆆ _ ㆆ)");
            printSeparator();
            return;
        }
        int taskNumber = Integer.parseInt(splitUserInput[1]) - 1;
        printSeparator();

        //Check if taskNumber is out of bounds
        if (taskNumber < 0 || taskNumber > taskCount - 1) {
            System.out.println("Invalid task number entered... (ㆆ _ ㆆ)");
            printSeparator();
            return;
        }

        //TaskNumber is valid
        tasks[taskNumber].setAsDone();
        System.out.println("NICE! (｡◕‿‿◕｡) I've marked the task as done!:");
        System.out.println("  [" + "\u2713" + "] " + tasks[taskNumber].description);
        printSeparator();
    }

    public static void main(String[] args) {
        String logo = "____    __    ____  ___       __      .___________. _______ .______      \n"
                + "\\   \\  /  \\  /   / /   \\     |  |     |           ||   ____||   _  \\     \n"
                + " \\   \\/    \\/   / /  ^  \\    |  |     `---|  |----`|  |__   |  |_)  |    \n"
                + "  \\            / /  /_\\  \\   |  |         |  |     |   __|  |      /     \n"
                + "   \\    /\\    / /  _____  \\  |  `----.    |  |     |  |____ |  |\\  \\----.\n"
                + "    \\__/  \\__/ /__/     \\__\\ |_______|    |__|     |_______|| _| `._____|\n";
        String endLogo = "________              \n"
                + "___  __ )____  ______ \n"
                + "__  __  |_  / / /  _ \\\n"
                + "_  /_/ /_  /_/ //  __/\n"
                + "/_____/ _\\__, / \\___/ \n"
                + "        /____/        ";
        String userInput;
        String[] splitUserInput;
        Task[] tasks = new Task[100];
        int taskCount = 0;
        boolean isFinished = true;
        Scanner in = new Scanner(System.in);

        //Print startup sequence
        System.out.println("Hello from\n" + logo);
        printSeparator();
        System.out.println(" Hello! I'm Walter  ◕_◕");
        System.out.println(" What can I do for you?");
        printSeparator();

        //Loop infinitely until user enters "bye"
        while (isFinished) {
            userInput = in.nextLine();
            splitUserInput = userInput.split(" ");
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
                addTodoTask(tasks, splitUserInput, taskCount);
                printTaskAddedConfirmation(tasks, taskCount);
                taskCount++;
                break;
            case "deadline":
                addDeadlineTask(tasks, Arrays.copyOfRange(splitUserInput, 1, splitUserInput.length), taskCount);
                printTaskAddedConfirmation(tasks, taskCount);
                taskCount++;
                break;
            case "event":
                addEventTask(tasks, Arrays.copyOfRange(splitUserInput, 1, splitUserInput.length), taskCount);
                printTaskAddedConfirmation(tasks, taskCount);
                taskCount++;
                break;
            default:
                break;
            }
        }

        //Print closing sequence
        printSeparator();
        System.out.println(" I'm sad to see you go. Hope to see you again soon! :D");
        System.out.println(endLogo);
        printSeparator();
    }

}
