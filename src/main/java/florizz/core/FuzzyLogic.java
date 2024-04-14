package florizz.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides functionality for detecting the closest command based on user input,
 * by computing Levenshtein Distance between strings;
 * removing unnecessary whitespaces within command word (e.g. h e l p, flo wer s);
 * and splitting inputs with the correct words but wrong format (e.g. infoRose, deletebouquetname),
 * for the purpose of reducing the impact human error and improving programme flow.
 */
public class FuzzyLogic {

    private static final Map<String, String> ITEMS = new HashMap<>();
    private static final int SIMILARITY_THRESHOLD = 2;
    private static final Logger logger = Logger.getLogger(FuzzyLogic.class.getName());

    static {
        ITEMS.put("save", "Command: Save a bouquet to device");
        ITEMS.put("new", "Command: Add a new bouquet");
        ITEMS.put("delete", "Command: Delete a bouquet");
        ITEMS.put("mybouquets", "Command: List all bouquets");
        ITEMS.put("info", "Command: Get information about a flower");
        ITEMS.put("bye", "Command: Exits the programme");
        ITEMS.put("flowers", "Command: List all flowers");
        ITEMS.put("remove","Command: Remove flower(s) from a bouquet");
        ITEMS.put("occasion", "Command: List flowers of specified occasion");
        ITEMS.put("add", "Command: Add flower(s) to a bouquet");
        ITEMS.put("help", "Command: List available commands");
        ITEMS.put("recommend", "Command: Get a recommended bouquet");
        ITEMS.put("next", "Command: Goes to next page");
        ITEMS.put("back", "Command: Goes to previous page");
        ITEMS.put("Orchid", "Flower");
        ITEMS.put("Rose", "Flower");
        ITEMS.put("Lily", "Flower");
        ITEMS.put("Daisy", "Flower");
        ITEMS.put("Baby Breath", "Flower");
        ITEMS.put("Chrysanthemum", "Flower");
        ITEMS.put("Hydrangea", "Flower");
        ITEMS.put("Carnation", "Flower");
        ITEMS.put("Wedding", "Occasion");
        ITEMS.put("Valentines", "Occasion");
        ITEMS.put("Mothers Day", "Occasion");
        ITEMS.put("Funeral", "Occasion");
        ITEMS.put("Eucalyptus", "Flower");
        ITEMS.put("Dusty Miller", "Flower");
        ITEMS.put("Pistacia", "Flower");
        ITEMS.put("Pittosporum", "Flower");
        ITEMS.put("Chamomile", "Flower");
        ITEMS.put("Astilbe", "Flower");
        ITEMS.put("Hypericum", "Flower");
        ITEMS.put("Freesia", "Flower");
        ITEMS.put("Helichrysum", "Flower");
        ITEMS.put("Limonium", "Flower");
        ITEMS.put("Limonium Perezii", "Flower");
        ITEMS.put("Statice", "Flower");
        ITEMS.put("Rice Flower", "Flower");
    }

    /**
     * Detects the closest predefined command/item/occasion based on user input.
     *
     * @param userInput The user input to be matched with predefined command/item/occasion.
     * @return The closest matching command/item/occasion.
     * @throws FlorizzException if the input is null or no matching command/item/occasion is found.
     */
    protected static String detectItem(String userInput) throws FlorizzException {
        if (userInput == null) {
            throw new FlorizzException("Input cannot be null");
        }

        if (userInput.length() == 1) {
            throw new FlorizzException("No matching command/item/occasion found for input: " + userInput);
        }

        String bestMatch = null;
        int bestDistance = Integer.MAX_VALUE;
        Ui uiFuzzy = new Ui();

        // Iterate over predefined commands
        for (String item : ITEMS.keySet()) {
            int distance = computeDLDistance(item.toLowerCase(), userInput.toLowerCase());
            if (distance < bestDistance) {
                bestDistance = distance;
                bestMatch = item;
            }
        }

        // If the best match is within threshold, return it; otherwise, return null
        if (bestDistance <= SIMILARITY_THRESHOLD && bestDistance != 0) {
            logger.log(Level.INFO, "--> Detected input: [{0}]", bestMatch);
            uiFuzzy.printFuzzyInputDetection(userInput, bestMatch);
            return bestMatch;
        } else if (bestDistance == 0) {
            return bestMatch;
        } else {
            logger.log(Level.SEVERE, "No matching command/item/occasion found for input: {0}", userInput);
            throw new FlorizzException("No matching command/item/occasion found for input: " + userInput);
        }
    }

    /**
     * Computes the Damerau-Levenshtein distance which is a metric used to measure the similarity between two strings.
     * It calculates the minimum number of single-character edits required to change one string into another.
     * These edits can be insertions, deletions, substitutions and transpositions of individual characters.
     *
     * @param item The first string.
     * @param userInput The second string.
     * @return The Damerau-Levenshtein distance between the two strings.
     * @throws FlorizzException if the loop tries to access elements beyond the array bounds.
     */
    protected static int computeDLDistance(String item, String userInput) throws FlorizzException {
        assert item != null && userInput != null : "Strings cannot be null";

        int itemLength = item.length();
        int inputLength = userInput.length();

        int[] previousRow = new int[inputLength + 1];
        int[] currentRow = new int[inputLength + 1];

        // Initialize the first row
        for (int j = 0; j <= inputLength; j++) {
            previousRow[j] = j;
        }

        // Calculate the Damerau-Levenshtein distance
        for (int i = 1; i <= itemLength; i++) {
            currentRow[0] = i;

            for (int j = 1; j <= inputLength; j++) {
                if (i > itemLength || j > inputLength) {
                    throw new FlorizzException("Fuzzy Logic: Accessing element outside of array bounds");
                }

                int substitutionCost = (item.charAt(i - 1) == userInput.charAt(j - 1)) ? 0 : 1;
                currentRow[j] = Math.min(Math.min(
                                previousRow[j] + 1,             // deletion
                                currentRow[j - 1] + 1),         // insertion
                        previousRow[j - 1] + substitutionCost); // substitution

                // Check for transpositions
                if (i > 1 && j > 1 && item.charAt(i - 1) == userInput.charAt(j - 2)
                        && item.charAt(i - 2) == userInput.charAt(j - 1)) {
                    currentRow[j] = Math.min(currentRow[j], previousRow[j - 2] + substitutionCost);
                }
            }

            // Swap rows
            int[] tempRow = previousRow;
            previousRow = currentRow;
            currentRow = tempRow;
        }

        // Return the Damerau-Levenshtein distance
        assert previousRow[inputLength] >= 0 : "Levenshtein distance cannot be negative";
        return previousRow[inputLength];
    }

    /**
     * Processes the user input command and returns the input string at the correct format.
     *
     *
     * @param userInput The user input command string.
     * @return The corrected input string after processing.
     * @throws FlorizzException if the input is null or empty.
     */
    protected static String processCommand(String userInput) throws FlorizzException {
        if (userInput == null) {
            throw new FlorizzException("Input cannot be empty.");
        }
        String correctedInput;
        String trimmedInput = userInput.trim();
        int firstWhitespace = trimmedInput.indexOf(" ");
        if ((firstWhitespace != -1)
                && (userInput.startsWith("delete")
                || userInput.startsWith("save")
                || userInput.startsWith("new")
                || userInput.startsWith("remove")
                || userInput.startsWith("add")
                || userInput.startsWith("info")
                || userInput.startsWith("flowers"))) {
            String[] arguments = new String[2];
            arguments[0] = userInput.substring(0,firstWhitespace).toLowerCase();
            arguments[1] = userInput.substring(firstWhitespace).trim();
            correctedInput = detectItem(arguments[0]) + " " + arguments[1];
        } else if ((firstWhitespace == -1)
                && userInput.equals("help")
                || userInput.equals("mybouquets")
                || userInput.equals("flowers")
                || userInput.equals("recommend")
                || userInput.equals("bye")
                || userInput.equals("occasion")
                || userInput.equals("back")
                || userInput.equals("next")) {
            correctedInput = userInput;
        } else {
            correctedInput = splitAndMergeInput(userInput);
        }
        return correctedInput;
    }

    /**
     * Splits and merges the user input command into an input string with the correct format.
     *
     * @param userInput The user input command string.
     * @return The corrected input string after splitting and merging.
     * @throws FlorizzException if there is an issue processing the input.
     */
    protected static String splitAndMergeInput(String userInput) throws FlorizzException {
        try {
            String correctedInput;
            String mergedInput = mergeInput(userInput);
            String splitMergedInput = splitInput(mergedInput);
            String[] arguments = splitMergedInput.split(" ");
            String bouquetName;
            String removeArgument;
            String addArgument;
            Ui uiFuzzy = new Ui();

            if (arguments.length == 1 && arguments[0]
                    .matches("(mybouquets|flowers|occasion|recommend|bye|help|back|next)")) {
                correctedInput = arguments[0];
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else if (arguments[0].matches("(info|flowers)")) {
                if (Objects.equals(arguments[1].toLowerCase(), "mothersday")) {
                    arguments[1] = "Mothers Day";
                }
                correctedInput = arguments[0] + " " + arguments[1];
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else if (arguments[0].matches("(new)")) {
                bouquetName = userInput.replaceFirst("n", "")
                        .replaceFirst("e", "")
                        .replaceFirst("w", "")
                        .strip();
                correctedInput = "new " + bouquetName;
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else if (arguments[0].matches("(delete)")) {
                bouquetName = userInput.replaceFirst("d", "")
                        .replaceFirst("e", "")
                        .replaceFirst("l", "")
                        .replaceFirst("e", "")
                        .replaceFirst("t", "")
                        .replaceFirst("e", "")
                        .strip();
                correctedInput = "delete " + bouquetName;
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else if (arguments[0].matches("(save)")) {
                bouquetName = userInput.replaceFirst("s", "")
                        .replaceFirst("a", "")
                        .replaceFirst("v", "")
                        .replaceFirst("e", "")
                        .strip();
                correctedInput = "save " + bouquetName;
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else if (arguments[0].matches("(remove)")) {
                removeArgument = userInput.replaceFirst("r", "")
                        .replaceFirst("e", "")
                        .replaceFirst("m", "")
                        .replaceFirst("o", "")
                        .replaceFirst("v", "")
                        .replaceFirst("e", "")
                        .strip();
                correctedInput = "remove " + removeArgument;
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else if (arguments[0].matches("(add)")) {
                addArgument = userInput.replaceFirst("a", "")
                        .replaceFirst("d", "")
                        .replaceFirst("d", "")
                        .strip();
                correctedInput = "add " + addArgument;
                uiFuzzy.printFuzzyInputDetection(userInput, correctedInput);
            } else {
                correctedInput = userInput;
            }
            return correctedInput;
        } catch (Exception e) {
            throw new FlorizzException("Error processing input: " + e.getMessage());
        }
    }

    /**
     * Separates the input string into a command and an argument.
     * If the input string matches a predefined command pattern, it corrects the input by adding a space between the
     * command and the argument.
     * Supported commands include: info, delete, flowers, new, add, remove, save.
     * If the input does not match any predefined command pattern, it returns the original input string unchanged.
     *
     * @param userInput The input string to be separated and corrected.
     * @return correctedInput The input with a space between the command and the argument if applicable.
     * @throws FlorizzException if the input string is null.
     */
    protected static String splitInput(String userInput) throws FlorizzException {
        if (userInput == null) {
            throw new FlorizzException("Input cannot be empty.");
        }

        String correctedInput = "";
        if (userInput.matches("(info)[a-zA-Z].*")) {
            String argumentInfo = userInput.replaceFirst("(info)", "");
            correctedInput = "info " + argumentInfo;
        } else if (userInput.matches("(delete).+")) {
            String argumentDelete = userInput.replaceFirst("delete", "");
            correctedInput = "delete " + argumentDelete;
        } else if (userInput.matches("(flowers)[a-zA-Z].*")) {
            String argumentFlowers = userInput.replaceFirst("(flowers)", "");
            correctedInput = "flowers " + argumentFlowers;
        } else if (userInput.matches("(new).+")) {
            String argumentNew = userInput.replaceFirst("new", "");
            correctedInput = "new " + argumentNew;
        } else if (userInput.matches("(add)[a-zA-Z].+")) {
            String argumentAdd = userInput.replaceFirst("add", "");
            correctedInput = "add " + argumentAdd;
        } else if (userInput.matches("(remove)[a-zA-Z].+")) {
            String argumentRemove = userInput.replaceFirst("remove", "");
            correctedInput = "remove " + argumentRemove;
        } else if (userInput.matches("(save)[a-zA-Z].+")) {
            String argumentSave = userInput.replaceFirst("save", "");
            correctedInput = "save " + argumentSave;
        }

        if (correctedInput.isEmpty()) {
            correctedInput = userInput;
        }
        return correctedInput;
    }

    /**
     * Merges the user input command by removing all whitespace characters.
     *
     * @param userInput The user input command string.
     * @return The merged input string with whitespace removed.
     * @throws FlorizzException if the input is null.
     */
    protected static String mergeInput(String userInput) throws FlorizzException {
        if (userInput == null) {
            throw new FlorizzException("Input cannot be empty.");
        }
        return userInput.replaceAll("\\s", "");
    }
}
