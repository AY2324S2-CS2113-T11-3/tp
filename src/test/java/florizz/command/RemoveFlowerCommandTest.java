package florizz.command;

import florizz.core.FlorizzException;
import florizz.core.FlowerDictionary;
import florizz.core.Ui;
import florizz.objects.Bouquet;
import florizz.objects.Flower;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RemoveFlowerCommandTest {
    @BeforeAll
    static void setup() {
        FlowerDictionary.startup(); // Initialize the FlowerDictionary with test data
    }

    @Test
    void testRemoveFlowerExecuteChrysanthemumWithColour() throws FlorizzException {
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 5;
        String flowerName = "Chrysanthemum";
        Flower.Colour flowerColour = Flower.Colour.WHITE;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToRemove = matchedFlowerAndColour.get(0);
        testBouquet.addFlower(testFlowerToRemove, quantity);
        Command testAddFlower = new RemoveFlowerCommand (testFlowerToRemove.getFlowerName(), flowerColour
                , quantity, testBouquet.getBouquetName());
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToRemove, quantity);
        controlBouquet.removeFlower(testFlowerToRemove, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testRemoveFlowerExecuteBabyBreathWithNoColour() throws FlorizzException {
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 5;
        String flowerName = "Baby Breath";

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        Flower testFlowerToRemove = matchedFlower.get(0);
        testBouquet.addFlower(testFlowerToRemove, quantity);
        Command testAddFlower = new RemoveFlowerCommand (testFlowerToRemove.getFlowerName()
                , quantity, testBouquet.getBouquetName());
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToRemove, quantity);
        controlBouquet.removeFlower(testFlowerToRemove, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }
    @Test
    void testRemoveFlowerExecuteRedRose() throws FlorizzException {
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 5;
        int quantityToRemove = 2;
        String flowerName = "Rose";
        Flower.Colour flowerColour = Flower.Colour.RED;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToRemove = matchedFlowerAndColour.get(0);
        testBouquet.addFlower(testFlowerToRemove, quantity);

        int existingQuantity = 10;
        String existingFlowerName = "Lily";
        Flower.Colour existingFlowerColour = Flower.Colour.WHITE;

        ArrayList<Flower> matchedExistingFlower = FlowerDictionary.filterByName(existingFlowerName);
        ArrayList<Flower> matchedExistingFlowerAndColour =
                FlowerDictionary.filterByColour(matchedExistingFlower, existingFlowerColour);
        Flower testExistingFlowerToRemove = matchedExistingFlowerAndColour.get(0);
        testBouquet.addFlower(testExistingFlowerToRemove,existingQuantity);

        Command testAddFlower = new RemoveFlowerCommand (testFlowerToRemove.getFlowerName(), flowerColour
                , quantityToRemove, testBouquet.getBouquetName());
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToRemove, quantity);
        controlBouquet.addFlower(testExistingFlowerToRemove,existingQuantity);
        controlBouquet.removeFlower(testFlowerToRemove, quantityToRemove);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testRemoveFlowerExecuteDaisy() throws FlorizzException {
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 10;
        int quantityToRemove = 20;
        String flowerName = "Daisy";
        Flower.Colour flowerColour = Flower.Colour.WHITE;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToRemove = matchedFlowerAndColour.get(0);
        testBouquet.addFlower(testFlowerToRemove, quantity);

        int existingQuantity = 10;
        String existingFlowerName = "Lily";
        Flower.Colour existingFlowerColour = Flower.Colour.WHITE;

        ArrayList<Flower> matchedExistingFlower = FlowerDictionary.filterByName(existingFlowerName);
        ArrayList<Flower> matchedExistingFlowerAndColour =
                FlowerDictionary.filterByColour(matchedExistingFlower, existingFlowerColour);
        Flower testExistingFlowerToRemove = matchedExistingFlowerAndColour.get(0);
        testBouquet.addFlower(testExistingFlowerToRemove,existingQuantity);

        Command testAddFlower = new RemoveFlowerCommand (testFlowerToRemove.getFlowerName(), flowerColour
                , quantityToRemove, testBouquet.getBouquetName());
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToRemove, quantity);
        controlBouquet.addFlower(testExistingFlowerToRemove,existingQuantity);
        controlBouquet.removeFlower(testFlowerToRemove, quantityToRemove);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

}
