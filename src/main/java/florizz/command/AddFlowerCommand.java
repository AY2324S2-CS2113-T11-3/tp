package florizz.command;

import florizz.core.FlorizzException;
import florizz.core.FlowerDictionary;
import florizz.core.Ui;
import florizz.objects.Bouquet;
import florizz.objects.Flower;

import java.util.ArrayList;
import java.util.logging.Logger;

public class AddFlowerCommand extends Command{
    private static Logger logger = Logger.getLogger(AddFlowerCommand.class.getName());
    private String flowerName;
    private Flower.Colour colour;
    private boolean hasColour = false;
    private Integer quantity;
    private String bouquetName;
    private boolean enableUi;

    /**
     * Constructs a new AddFlowerCommand with the specified parameters.
     *
     * @param flowerName The name of the flower to add
     * @param quantity The quantity of the flower to add
     * @param bouquetName The name of the bouquet to add the flower to
     * @param enableUi A boolean indicating whether UI should be enabled
     */
    public AddFlowerCommand(String flowerName, int quantity, String bouquetName, boolean enableUi) {
        this.flowerName = flowerName;
        this.quantity = quantity;
        this.bouquetName = bouquetName;
        this.enableUi = enableUi;
    }

    /**
     * Constructs a new AddFlowerCommand with the specified parameters.
     *
     * @param flowerName The name of the flower to add
     * @param colour The color of the flower
     * @param quantity The quantity of the flower to add
     * @param bouquetName The name of the bouquet to add the flower to
     * @param enableUi A boolean indicating whether UI should be enabled
     */
    public AddFlowerCommand(String flowerName, Flower.Colour colour,
                            int quantity, String bouquetName, boolean enableUi) {
        this.flowerName = flowerName;
        this.colour = colour;
        this.quantity = quantity;
        this.bouquetName = bouquetName;
        this.enableUi = enableUi;
        this.hasColour = true;
    }

    /**
     * Executes the AddFlowerCommand by adding flowers to the specified bouquet.
     *
     * @param bouquetList The list of bouquets to search for the specified bouquet
     * @param ui The user interface to interact with the user
     * @return True if the command is executed successfully, false otherwise
     * @throws FlorizzException If the specified bouquet is not found or if there is an issue adding the flower
     */
    @Override
    public boolean execute(ArrayList<Bouquet> bouquetList, Ui ui) throws FlorizzException {
        logger.entering(AddFlowerCommand.class.getName(), "execute");
        boolean doesBouquetExist = false;
        Bouquet bouquetToAddFlower = new Bouquet();
        for (int i = 0; !doesBouquetExist && i < bouquetList.size(); i++) {
            if (bouquetList.get(i).getBouquetName().equals(this.bouquetName)) {
                bouquetToAddFlower = bouquetList.get(i);
                doesBouquetExist = true;
            }
        }

        if (!doesBouquetExist) {
            throw new FlorizzException("No such bouquet is found.");
        }

        ArrayList<Flower> matchingFlowers = FlowerDictionary.filterByName(flowerName);
        Flower flowerToAdd;
        if (hasColour){
            ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchingFlowers, colour);
            if (!matchedFlowerAndColour.isEmpty()){
                flowerToAdd = matchedFlowerAndColour.get(0);
                bouquetToAddFlower.addFlower(flowerToAdd,this.quantity);
                if (enableUi) {
                    ui.printAddFlowerSuccess(bouquetList, flowerToAdd.getNameAndColour(), quantity, bouquetName);
                }
            } else {
                throw new FlorizzException("This flower does not exist in that colour. " +
                        "Type info <flower> to view all available colours for this flower");
            }
        } else if (matchingFlowers.size() == 1){
            flowerToAdd = matchingFlowers.get(0);
            bouquetToAddFlower.addFlower(matchingFlowers.get(0), this.quantity);
            if (enableUi) {
                ui.printAddFlowerSuccess(bouquetList, flowerToAdd.getNameAndColour(), quantity, bouquetName);
            }
        } else {
            flowerToAdd = ui.chooseColour(matchingFlowers, flowerName);
            if (!flowerToAdd.getFlowerName().isBlank()){
                bouquetToAddFlower.addFlower(flowerToAdd, this.quantity);
                if (enableUi) {
                    ui.printAddFlowerSuccess(bouquetList, flowerToAdd.getNameAndColour(), quantity, bouquetName);
                }
            } else {
                ui.printCancelCommand();
            }

        }

        logger.exiting(AddFlowerCommand.class.getName(), "execute");
        return true;
    }
}
