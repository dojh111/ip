package walter;

import walter.exceptions.WalterException;

public class Parser {

    public static final String EXCEPTION_EMPTY_FIELD = "Oh no... You have to enter a task number. Please try again!";
    public static final String BLANK_SPACE = "";
    public static final String WHITESPACE_IDENTIFIER = " ";

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
     * Checks for invalid command and throws WalterException
     */
    public static void checkForValidInput(String[] splitUserInput) throws WalterException {
        if (splitUserInput.length == 1) {
            throw new WalterException(EXCEPTION_EMPTY_FIELD);
        }
    }

    /** Splits string by white space and returns array of strings */
    public String[] divideUserCommand(String userInput) {
        return userInput.split(WHITESPACE_IDENTIFIER);
    }

    /** Determines command from string */
    public String determineCommand(String[] splitUserInput) {
        return splitUserInput[0];
    }
}
