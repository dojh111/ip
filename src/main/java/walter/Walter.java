package walter;

import walter.exceptions.WalterException;

import java.io.IOException;
import java.util.Scanner;

/**
 * Duke is a chat bot which can help the user do multiple tasks
 */
public class Walter {
    //Identifiers and values
    public static final String DEADLINE_IDENTIFIER = "/by";
    public static final String EVENT_IDENTIFIER = "/at";
    public static final String COMMAND_DEADLINE = "deadline";
    public static final String COMMAND_EVENT = "event";

    public static final String MESSAGE_TASK_MARKED = "NICE! (｡◕‿‿◕｡) I've marked the task as done!:";
    public static final String MESSAGE_TASK_DELETED = "Alright! I've removed this task from the list:";

    //Exception Messages
    public static final String EXCEPTION_INVALID_COMMAND = "I do not know what that means ;-;, please try again!";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parse;

    public Walter(String filePath) {
        ui = new Ui();
        parse = new Parser();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.readFileContents());
        } catch (IOException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        String userInput;
        String command;
        String[] splitUserInput;
        String details;
        boolean isFinished = false;

        ui.printStartupSequence();

        //Loop infinitely until user enters "bye"
        while (!isFinished) {
            userInput = ui.readUserCommand();
            splitUserInput = parse.divideUserCommand(userInput);
            command = parse.determineCommand(splitUserInput);
            try {
                switch (command) {
                case "bye":
                    isFinished = true;
                    break;
                case "list":
                    ui.printTaskList(tasks.getTaskList());
                    break;
                case "done":
                    details = tasks.setTaskAsDone(splitUserInput);
                    ui.printSetDeleteConfirmMessage(MESSAGE_TASK_MARKED, details);
                    storage.writeToFile(tasks.getTaskList());
                    break;
                case "delete":
                    details = tasks.deleteTask(splitUserInput);
                    ui.printSetDeleteConfirmMessage(MESSAGE_TASK_DELETED, details);
                    storage.writeToFile(tasks.getTaskList());
                    break;
                case "todo":
                    tasks.addTodoTask(userInput);
                    ui.printTaskAddedConfirmation(tasks.getTaskList());
                    storage.writeToFile(tasks.getTaskList());
                    break;
                case "deadline":
                    tasks.addNewTimedEvent(userInput, COMMAND_DEADLINE, DEADLINE_IDENTIFIER);
                    ui.printTaskAddedConfirmation(tasks.getTaskList());
                    storage.writeToFile(tasks.getTaskList());
                    break;
                case "event":
                    tasks.addNewTimedEvent(userInput, COMMAND_EVENT, EVENT_IDENTIFIER);
                    ui.printTaskAddedConfirmation(tasks.getTaskList());
                    storage.writeToFile(tasks.getTaskList());
                    break;
                case "schedule":
                    tasks.getSchedule(userInput);
                    break;
                case "find":
                    tasks.findTask(userInput);
                    break;
                default:
                    throw new WalterException(EXCEPTION_INVALID_COMMAND);
                }

            } catch (WalterException e) {
                ui.showWalterError(e.getErrorMessage());

            } catch (NullPointerException | IndexOutOfBoundsException e) {
                ui.showInvalidNumberError();

            } catch (NumberFormatException e) {
                ui.showInvalidInputError();
            } catch (IOException e) {
                ui.showFileSaveError();
            }
        }

        ui.printClosingSequence();
    }

    public static void main(String[] args) {
        new Walter("walter.txt").run();
    }

}
