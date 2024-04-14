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

class AddFlowerCommandTest {

    @BeforeAll
    static void setup() {
        FlowerDictionary.startup(); // Initialize the FlowerDictionary with test data
    }

    @Test
    void testAddFlowerExecuteChrysanthemumWithColour() throws FlorizzException {
        boolean enableUi = true;
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 10;
        String flowerName = "Chrysanthemum";
        Flower.Colour flowerColour = Flower.Colour.WHITE;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToAdd = matchedFlowerAndColour.get(0);
        Command testAddFlower = new AddFlowerCommand (testFlowerToAdd.getFlowerName()
                , quantity, testBouquet.getBouquetName(), enableUi);
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToAdd, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testAddFlowerExecuteChrysanthemumNoColour() throws FlorizzException {
        boolean enableUi = true;
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 10;
        String flowerName = "Chrysanthemum";

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        Flower testFlowerToAdd = matchedFlower.get(0);
        Command testAddFlower = new AddFlowerCommand (testFlowerToAdd.getFlowerName()
                , quantity, testBouquet.getBouquetName(), enableUi);
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToAdd, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testAddFlowerExecuteBabyBreath() throws FlorizzException {
        boolean enableUi = true;
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 10;
        String flowerName = "Baby Breath";
        Flower.Colour flowerColour = Flower.Colour.WHITE;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToAdd = matchedFlowerAndColour.get(0);
        Command testAddFlower = new AddFlowerCommand (testFlowerToAdd.getFlowerName(), flowerColour
                , quantity, testBouquet.getBouquetName(), enableUi);
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToAdd, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testAddFlowerExecuteLily() throws FlorizzException {
        boolean enableUi = true;
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 10;
        String flowerName = "Lily";
        Flower.Colour flowerColour = Flower.Colour.WHITE;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToAdd = matchedFlowerAndColour.get(0);
        Command testAddFlower = new AddFlowerCommand (testFlowerToAdd.getFlowerName(), flowerColour
                , quantity, testBouquet.getBouquetName(), enableUi);
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToAdd, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testAddFlowerExecuteDarkCrimsonRose() throws FlorizzException {
        boolean enableUi = true;
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 3;
        String flowerName = "Rose";
        Flower.Colour flowerColour = Flower.Colour.DARK_CRIMSON;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToAdd = matchedFlowerAndColour.get(0);
        Command testAddFlower = new AddFlowerCommand (testFlowerToAdd.getFlowerName(), flowerColour
                , quantity, testBouquet.getBouquetName(), enableUi);
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToAdd, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }

    @Test
    void testAddFlowerExecuteRedRose() throws FlorizzException {
        boolean enableUi = true;
        Ui ui = new Ui();

        Bouquet testBouquet = new Bouquet("Test Bouquet");
        int quantity = 3;
        String flowerName = "Rose";
        Flower.Colour flowerColour = Flower.Colour.RED;

        ArrayList<Bouquet> testList = new ArrayList<>();
        ArrayList<Flower> matchedFlower = FlowerDictionary.filterByName(flowerName);
        ArrayList<Flower> matchedFlowerAndColour = FlowerDictionary.filterByColour(matchedFlower, flowerColour);
        Flower testFlowerToAdd = matchedFlowerAndColour.get(0);
        Command testAddFlower = new AddFlowerCommand (testFlowerToAdd.getFlowerName(), flowerColour
                , quantity, testBouquet.getBouquetName(), enableUi);
        testList.add(testBouquet);

        try {
            assertTrue(testAddFlower.execute(testList, ui));
        } catch (FlorizzException error) {
            fail("Unexpected Florizz Exception thrown: " + error.getMessage());
        }

        // Control Bouquet
        Bouquet controlBouquet = new Bouquet("Control Bouquet");
        controlBouquet.addFlower(testFlowerToAdd, quantity);

        assertEquals(controlBouquet.getFlowerHashMap(), testBouquet.getFlowerHashMap());
    }
}
