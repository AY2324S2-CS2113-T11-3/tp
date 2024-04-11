package florizz.command;

import florizz.core.FlorizzException;
import florizz.core.Parser;
import florizz.core.FlowerDictionary;
import florizz.core.Ui;
import florizz.objects.Bouquet;
import florizz.objects.Flower;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RecommendCommand extends Command{
    private Logger logger = Logger.getLogger(RecommendCommand.class.getName());
    @Override
    public boolean execute(ArrayList<Bouquet> bouquetList, Ui ui) throws FlorizzException {
        logger.entering(RecommendCommand.class.getName(), "execute");
        // ask for occasion
        Flower.Occasion occasion = askOccasion(ui);

        // eligible flowers based on occasion
        ArrayList<Flower> eligibleFlowers = FlowerDictionary.filterByOccasion(occasion);

        // ask for colour preference
        Flower.Colour colour = askColour(ui, eligibleFlowers);

        // eligible flowers based on colour
        eligibleFlowers = FlowerDictionary.filterByColour(eligibleFlowers, colour);

        // check if there are any flowers available
        if (eligibleFlowers.isEmpty()) {
            throw new FlorizzException("No flowers available for this occasion and colour");
        }

        // ask for bouquetName
        String bouquetName = askBouquetName(ui, bouquetList);

        // ask for size [FUTURE IMPLEMENTATION]

        // create bouquet with occasion and colour
        Bouquet recommendedBouquet = new Bouquet(bouquetName);

        // randomly add 3 flowers to bouquet
        addRandomFlowers(eligibleFlowers, recommendedBouquet);

        // ask if they want to save bouquet to array
        askSaveBouquet(ui, bouquetList, recommendedBouquet);

        logger.exiting(RecommendCommand.class.getName(), "execute");
        return true;
    }

    /**
     * Gets the name the user wants to make for the recommended bouquet and makes sure the name does not already exist
     * @param ui Ui to take input and print messages
     * @param bouquetList List that contains all bouquets
     * @return The chosen valid bouquetName
     */
    public String askBouquetName(Ui ui, ArrayList<Bouquet> bouquetList) throws FlorizzException {
        boolean isValidName = false;
        String bouquetName = "placeHolder";
        ui.printAskBouquetName();
        while (!isValidName) {
            bouquetName = ui.getInput();
            Parser.checkRecommendExitCondition(bouquetName);
            if (bouquetList.contains(new Bouquet(bouquetName))) {
                System.out.println("Sorry a bouquet with this name already exists, please enter another name");
            } else {
                isValidName = true;
            }
        }
        return bouquetName;
    }

    /**
     * Adds random flowers to the bouquet
     * @param eligibleFlowers list of flowers to choose from
     * @param recommendedBouquet bouquet to add flowers to
     */
    private void addRandomFlowers(ArrayList<Flower> eligibleFlowers, Bouquet recommendedBouquet)
            throws FlorizzException {
        logger.entering(RecommendCommand.class.getName(), "addRandomFlowers");
        // [TEMPORARY CODE]
        // generate random combination of flowers from eligible flowers totaling to 5
        for (int i = 0; i < 5; i++) {
            int randomIndex = (int) (Math.random() * eligibleFlowers.size());
            recommendedBouquet.addFlower(eligibleFlowers.get(randomIndex), 1);
        }
        logger.exiting(RecommendCommand.class.getName(), "addRandomFlowers");
    }

    /**
     * Asks user for occasion
     * @return Occasion enum
     */
    private Flower.Occasion askOccasion(Ui ui) throws FlorizzException {
        logger.entering(RecommendCommand.class.getName(), "askOccasion");
        boolean isValidFormat = false;
        boolean isValidOccasion = false;
        String occasionInput = "placeholder";
        ui.printAskOccasion();
        while (!(isValidFormat && isValidOccasion)) {
            occasionInput = ui.getInput();
            Parser.checkRecommendExitCondition(occasionInput);
            isValidFormat = Parser.parseOccasion(occasionInput);
            isValidOccasion = Flower.isValidOccasion(occasionInput);
            // check if occasion is in our dictionary
            if (!isValidOccasion) {
                System.out.println("This occasion does not exist." +
                        "Type 'cancel' if you would like to exit the recommendation page");
            }
        }

        logger.exiting(RecommendCommand.class.getName(), "askOccasion");
        // convert string to Occasion enum
        return Flower.stringToOccasion(occasionInput);
    }

    /**
     * Asks user for colour
     * @param eligibleFlowers list of flowers to choose from
     * @return Colour enum
     */
    private Flower.Colour askColour(Ui ui, ArrayList<Flower> eligibleFlowers) throws FlorizzException{
        assert !eligibleFlowers.isEmpty() : "Eligible flowers should not be empty";
        logger.entering(RecommendCommand.class.getName(), "askColour");
        String colourInput = "placeHolder";
        boolean isValidFormat = false;
        boolean isValidColour = false;
        ui.printAskColour(eligibleFlowers);
        while (!(isValidColour && isValidFormat)) {
            colourInput = ui.getInput();
            Parser.checkRecommendExitCondition(colourInput);
            isValidFormat = Parser.parseColour(colourInput);
            isValidColour = Flower.isValidColour(colourInput);

            // check if colour is in our dictionary
            if (!isValidColour) {
                System.out.println("This colour does not exist. " +
                        "Type 'cancel' if you would like to exit the recommendation page");
            }
        }

        logger.exiting(RecommendCommand.class.getName(), "askColour");
        return Flower.stringToColour(colourInput);
    }

    private void askSaveBouquet(Ui ui, ArrayList<Bouquet> bouquetList,
                                Bouquet recommendedBouquet) {
        logger.entering(RecommendCommand.class.getName(), "askSaveBouquet");
        String saveInput = "placeHolder";
        boolean isValidFormat = false;
        boolean isValidInput = false;
        while (!(isValidInput && isValidFormat)) {
            saveInput = ui.printAskSaveBouquet(recommendedBouquet).toLowerCase();
            isValidFormat = Parser.parseSaveBouquet(saveInput);
            isValidInput = (saveInput.equalsIgnoreCase("yes") || saveInput.equalsIgnoreCase("no"));
        }

        if (saveInput.equalsIgnoreCase("yes")) {
            bouquetList.add(recommendedBouquet);
            ui.printBouquetAdded(recommendedBouquet);
            assert !bouquetList.isEmpty() : "Bouquet list should not be empty";
        }
        logger.exiting(RecommendCommand.class.getName(), "askSaveBouquet");
    }
}
