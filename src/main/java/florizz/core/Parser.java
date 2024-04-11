package florizz.core;

import florizz.command.InfoCommand;
import florizz.command.HelpCommand;
import florizz.command.SaveCommand;
import florizz.command.FlowerCommand;
import florizz.command.ExitCommand;
import florizz.command.RemoveFlowerCommand;
import florizz.command.ListOccasionCommand;
import florizz.command.ListBouquetCommand;
import florizz.command.DeleteBouquetCommand;
import florizz.command.AddFlowerCommand;
import florizz.command.Command;
import florizz.command.AddBouquetCommand;
import florizz.command.BackCommand;
import florizz.command.NextCommand;
import florizz.command.RecommendCommand;
import florizz.objects.Bouquet;
import florizz.objects.Flower;

import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Parses user input and generates appropriate Command objects.
 */
public class Parser {
    private static Logger logger = Logger.getLogger(Florizz.class.getName());
    // prefixes to parse input
    private static final String COLOUR = "/c";
    private static final String QUANTITY = "/q";
    private static final String ADD_FLOWER_PREFIX = "/to";
    private static final String REMOVE_FLOWER_PREFIX = "/from";

    // regex
    private static final String ADD_FLOWER_REGEX = "(.+)/q(\\s*)(-?\\d+)(\\s*)/to(.+)";

    private static final String ADD_FLOWER_AND_COLOUR_REGEX = "(.+)/c(\\s*)(.+)(\\s*)/q(\\s*)(-?\\d+)(\\s*)/to(.+)";
    private static final String REMOVE_FLOWER_REGEX = "(.+)/q(\\s*)(-?\\d+)(\\s*)/from(.+)";
    private static final String REMOVE_FLOWER_AND_COLOUR_REGEX
            = "(.+)/c(\\s*)(.+)(\\s*)/q(\\s*)(-?\\d+)(\\s*)/from(.+)";
    private static final String PARSE_OCCASION_REGEX = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)?\\s*$";
    private static final String PARSE_COLOUR_REGEX = "^\\s*[A-Za-z]+(?:\\s+[A-Za-z]+)?\\s*$";
    private static final String SAVE_BOUQUET_REGEX = "^\\s*(yes|no)\\s*$";

    public static Command parse (String input, boolean enableUi) throws FlorizzException {
        logger.entering("Parser", "parse");
        Command command = null;

        try {
            String[] decodedInput = commandHandler(input);
            switch (decodedInput[0]) {
            case ("mybouquets"):
                command = new ListBouquetCommand();
                break;
            case ("new"):
                command = handleAddBouquet(decodedInput[1], enableUi);
                break;
            case ("delete"):
                command = handleDeleteBouquet(decodedInput[1]);
                break;
            case ("bye"):
                command = new ExitCommand();
                break;
            case ("help"):
                command = new HelpCommand();
                break;
            case ("flowers"):
                command = handleFlowerCommand(decodedInput);
                break;
            case ("info"):
                command = handleInfoCommand(decodedInput[1]);
                break;
            case ("next"):
                command = new NextCommand();
                break;
            case ("back"):
                command = new BackCommand();
                break;
            case ("occasion"):
                command = new ListOccasionCommand();
                break;
            case ("add"):
                command = handleAddFlower(decodedInput[1], enableUi);
                break;
            case ("remove"):
                command = handleRemoveFlower(decodedInput[1]);
                break;
            case ("recommend"):
                command = new RecommendCommand();
                break;
            case ("save"):
                command = new SaveCommand(decodedInput[1]);
                break;
            default:
                throw new FlorizzException("Unidentified input, type help to get a list of all commands!");
            }
            logger.log(Level.INFO, "Command parsed successfully");
        } catch (FlorizzException ex) {
            logger.log(Level.INFO, "Exception occurred while parsing command: " + ex.errorMessage, ex);
            throw ex;
        } finally {
            logger.exiting("Parser", "parse");
        }
        return command;
    }


    /**
     * Splits input into command and arguments (if any). Handles capitalisation, whitespaces and small typos.
     *
     * @param input The user input where command is to be extracted
     * @return String[] output; output[0] = item ; output[1] = argument(s)
     */
    public static String[] commandHandler(String input) throws FlorizzException {
        String[] outputs = new String[2];
        String trimmedInput = input.trim();
        String processedInput = FuzzyLogic.processCommand(trimmedInput);
        int firstWhitespace = processedInput.indexOf(" ");

        if (firstWhitespace != -1) {
            outputs[0] = FuzzyLogic.detectItem(processedInput.substring(0,firstWhitespace).toLowerCase());
            switch (outputs[0]) {
            case ("save"): // Fallthrough
            case ("delete"): // Fallthrough
            case ("new"):
                outputs[1] = processedInput.substring(firstWhitespace).trim();
                break;
            case ("remove"): // Fallthrough
            case ("add"):
                String[] arguments = new String[2];
                String trimmedArgument = processedInput.substring(firstWhitespace).trim();

                int firstSlash = trimmedArgument.indexOf("/");
                if (firstSlash < 0 && outputs[0].equals("remove")) {
                    throw new FlorizzException("Incorrect usage of remove." +
                            " Correct format: remove <flowerName> " +
                            "/c <flowerColour> (optional) " +
                            "/q <quantity> /from <bouquetName>");
                } else if (firstSlash < 0) { // add

                    throw new FlorizzException("Incorrect usage of add." +
                            " Correct format: add <flowerName> " +
                            "/c <flowerColour> (optional) " +
                            "/q <quantity> /to <bouquetName>");
                }
                arguments[0] = FuzzyLogic.detectItem(trimmedArgument.substring(0,firstSlash).trim());
                arguments[1] = trimmedArgument.substring(firstSlash).trim();
                outputs[1] = arguments[0] + " " + arguments[1];
                break;
            default:
                outputs[1] = FuzzyLogic.detectItem(processedInput.substring(firstWhitespace).trim());
                break;
            }
        } else {
            outputs[0] = FuzzyLogic.detectItem(processedInput.toLowerCase());
        }
        if (firstWhitespace == -1 && (outputs[0].equals("save"))) {
            throw new FlorizzException("Please specify which bouquet you are saving!");
        }

        return outputs;
    }

    /**
     * remove prefix from an input string
     * e.g. "/to For Mom" -> " For Mom"
     *
     * @param input The argument
     * @param prefix The keyword that identifies the argument
     * @return input with prefix removed
     */
    public static String removePrefix(String input, String prefix) {
        return input.replace(prefix, "");
    }

    /**
     * Handles the parsing and creation of an AddBouquetCommand object based on user input.
     * @param input The user input to be parsed.
     * @return An AddBouquetCommand object corresponding to the parsed input.
     * @throws FlorizzException If the input does not contain the required bouquet information.
     */
    private static AddBouquetCommand handleAddBouquet(String input, boolean enableUi) throws FlorizzException{
        if (input == null){
            throw new FlorizzException("Did not include bouquet to add");
        }
        String newBouquetName = input.trim();
        return new AddBouquetCommand(new Bouquet(newBouquetName), enableUi);
    }

    /**
     * Handles the parsing and creation of a DeleteBouquetCommand object based on user input.
     * @param input The user input to be parsed.
     * @return A DeleteBouquetCommand object corresponding to the parsed input.
     * @throws FlorizzException If the input does not contain the required bouquet information.
     */
    private static DeleteBouquetCommand handleDeleteBouquet(String input) throws FlorizzException{
        if (input == null){
            throw new FlorizzException("Did not include bouquet to delete");
        }
        String bouquetToDelete = input.trim();
        return new DeleteBouquetCommand(new Bouquet(bouquetToDelete));
    }

    /**
     * Handles the parsing and creation of a FlowerCommand object based on user input.
     * @param input The user input to be parsed.
     * @return A FlowerCommand object corresponding to the parsed input.
     */
    private static FlowerCommand handleFlowerCommand(String[] input) {
        String occasion = (input[1] == null) ? " " : input[1].trim();
        return new FlowerCommand(occasion);
    }

    /**
     * Handles the parsing and creation of an AddFlowerCommand object based on user input.
     * @param argument The user input to be parsed.
     * @return An AddFlowerCommand object corresponding to the parsed input.
     * @throws FlorizzException If the input does not match the required format.
     */
    private static AddFlowerCommand handleAddFlower(String argument, boolean enableUi) throws FlorizzException {
        boolean includeColour = false;
        if (argument == null) {
            throw new FlorizzException("No argument detected! " +
                    "Please use the correct format of 'add <flowerName> /q <quantity> /to <bouquetName>");
        }

        if (!argument.matches(ADD_FLOWER_REGEX)) {
            throw new FlorizzException("Incorrect format detected! " +
                    "Please use the correct format of 'add <flowerName> /q <quantity> /to <bouquetName>");
        }

        if (argument.matches(ADD_FLOWER_AND_COLOUR_REGEX)){
            includeColour = true;
        }
        // [WARNING] might need to check for extra slash k

        int prefixIndex = argument.indexOf(ADD_FLOWER_PREFIX);
        int quantityIndex = argument.indexOf(QUANTITY);

        String flowerName = argument.substring(0,quantityIndex).trim().toLowerCase();
        String quantityString = removePrefix(argument.substring(quantityIndex, prefixIndex), QUANTITY).trim();
        // [WARNING] might need to check if it's a valid integer
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException error) {
            throw new FlorizzException("Invalid number inputted, please enter a sensible number next time!");
        }
        if (quantity < 0) {
            throw new FlorizzException("Flowers are physical objects which cannot be negative!");
        }
        String bouquetName = removePrefix(argument.substring(prefixIndex), ADD_FLOWER_PREFIX).trim();
        if (includeColour) {
            int colourIndex = argument.indexOf(COLOUR);
            try{
                flowerName = argument.substring(0,colourIndex).trim();
                String colourString = removePrefix(argument.substring(colourIndex, quantityIndex), COLOUR).trim();
                Flower.Colour colourToAdd = Flower.stringToColour(colourString);
                return new AddFlowerCommand(flowerName, colourToAdd, quantity, bouquetName, enableUi);
            } catch( IllegalArgumentException error){
                throw new FlorizzException("Tried to add a non recognised colour" +
                        "Type 'flowers' to view all the currently available flowers and their colours.");
            }
        }
        return new AddFlowerCommand(flowerName, quantity, bouquetName, enableUi);
    }

    /**
     * Handles the parsing and creation of a RemoveFlowerCommand object based on user input.
     * @param argument The user input to be parsed.
     * @return A RemoveFlowerCommand object corresponding to the parsed input.
     * @throws FlorizzException If the input does not match the required format.
     */
    private static RemoveFlowerCommand handleRemoveFlower(String argument) throws FlorizzException {
        boolean includeColour = false;
        if (argument == null) {
            throw new FlorizzException("No argument detected! " +
                    "Please use the correct format of 'remove <flowerName> /q <quantity> /from <bouquetName>");
        }

        if (!argument.matches(REMOVE_FLOWER_REGEX)) {
            throw new FlorizzException("Incorrect format detected! " +
                    "Please use the correct format of 'remove <flowerName> /q <quantity> /from <bouquetName>");
        }
        if (argument.matches(REMOVE_FLOWER_AND_COLOUR_REGEX)){
            includeColour = true;
        }
        // [WARNING] might need to check for extra slash k

        int prefixIndex = argument.indexOf(REMOVE_FLOWER_PREFIX);
        int quantityIndex = argument.indexOf(QUANTITY);

        String flowerName = argument.substring(0, quantityIndex).trim().toLowerCase();
        String quantityString = removePrefix(argument.substring(quantityIndex, prefixIndex), QUANTITY).trim();
        // [WARNING] might need to check if it's a valid integer
        int quantity;
        try {
            quantity = Integer.parseInt(quantityString);
        } catch(NumberFormatException error) {
            throw new FlorizzException("Invalid number inputted, please enter a sensible number next time!");
        }
        if (quantity < 0) {
            throw new FlorizzException("Flowers are physical objects which cannot be negative!");
        }
        String bouquetName = removePrefix(argument.substring(prefixIndex), REMOVE_FLOWER_PREFIX).trim();
        if (includeColour){
            int colourIndex = argument.indexOf(COLOUR);
            try{
                flowerName = argument.substring(0,colourIndex).trim();
                String colourString = removePrefix(argument.substring(colourIndex, quantityIndex), COLOUR).trim();
                Flower.Colour colourToAdd = Flower.stringToColour(colourString);
                return new RemoveFlowerCommand(flowerName, colourToAdd, quantity, bouquetName);
            } catch(IllegalArgumentException error){
                throw new FlorizzException("Tried to add a non recognised colour" +
                        "Type 'flowers' to view all the currently available flowers and their colours.");
            }
        }
        return new RemoveFlowerCommand(flowerName, quantity, bouquetName);
    }

    /**
     * Handles the parsing and creation of an InfoCommand object based on user input.
     * @param flowerName The user input to be parsed.
     * @return An InfoCommand object corresponding to the parsed input.
     */
    private static InfoCommand handleInfoCommand(String flowerName) throws FlorizzException {
        if (flowerName == null) {
            throw new FlorizzException("Please specify flower name to retrieve info from.");
        }
        return new InfoCommand(flowerName);
    }

    /**
     * Parses the occasion from the user input.
     * @param argument The user input to be parsed.
     * @return The parsed occasion.
     */
    public static boolean parseOccasion(String argument) throws FlorizzException {
        if (argument == null) {
            System.out.println("No argument detected! " +
                    "Please input an occasion");
            return false;
        }
        String detectedOccasion = FuzzyLogic.detectItem(argument);
        if (detectedOccasion.isEmpty()) {
            System.out.println("Incorrect format detected! " +
                    "Please input a single occasion");
            return false;
        }

        return true;
    }

    /**
     * Parses the colour from the user input.
     * @param argument The user input to be parsed.
     * @return The parsed colour String
     */
    public static boolean parseColour(String argument) {
        if (argument == null) {
            System.out.println("No argument detected! " +
                    "Please input a colour");
            return false;
        }

        if (!argument.matches(PARSE_COLOUR_REGEX)) {
            System.out.println("Incorrect format detected! " +
                    "Please input a single colour");
            return false;
        }

        return true;
    }

    /**
     * Parses the user input to save a bouquet.
     * @param argument The user input to be parsed.
     * @return The parsed save bouquet String
     */
    public static boolean parseSaveBouquet(String argument) {
        if (argument == null) {
            System.out.println("No argument detected! " +
                    "Please input a bouquet name to save");
            return false;
        }

        if (!argument.matches(SAVE_BOUQUET_REGEX)) {
            System.out.println("Incorrect format detected! " +
                    "Please input a yes or a no");
            return false;
        }

        return true;
    }

    /**
     * Checks if the user has entered the exit word for the recommend page
     * @param input Input from the user
     * @throws FlorizzException Thrown when the user has entered the exit word
     */
    public static void checkRecommendExitCondition(String input) throws FlorizzException{
        if (input.equalsIgnoreCase("cancel")) {
            throw new FlorizzException("Leaving recommend");
        }
    }
}
