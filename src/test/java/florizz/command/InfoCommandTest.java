package florizz.command;

import florizz.core.FlorizzException;
import florizz.core.FlowerDictionary;
import florizz.core.Ui;
import florizz.objects.Bouquet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class InfoCommandTest {

    @BeforeAll
    static void setup() {
        FlowerDictionary.startup(); // Initialize the FlowerDictionary with test data
    }
    @Test
    void testInfoCommandRose() {
        String flowerName = "Rose";
        ArrayList<Bouquet> bouquetList = new ArrayList<>();
        Ui ui = new Ui();
        InfoCommand infoCommand = new InfoCommand(flowerName);

        boolean result = false;
        try {
            result = infoCommand.execute(bouquetList, ui);
        } catch (FlorizzException e) {
            fail("Unexpected Florizz Exception thrown: " + e.getMessage());
        }

        assertTrue(result, "Expected execute method to return true indicating successful execution");
    }

    @Test
    void testInfoCommandLily() {
        String flowerName = "Lily";
        ArrayList<Bouquet> bouquetList = new ArrayList<>();
        Ui ui = new Ui();
        InfoCommand infoCommand = new InfoCommand(flowerName);

        boolean result = false;
        try {
            result = infoCommand.execute(bouquetList, ui);
        } catch (FlorizzException e) {
            fail("Unexpected Florizz Exception thrown: " + e.getMessage());
        }
        assertTrue(result, "Expected execute method to return true indicating successful execution");
    }

    @Test
    void testInfoCommandOrchid() {
        String flowerName = "Orchid";
        ArrayList<Bouquet> bouquetList = new ArrayList<>();
        Ui ui = new Ui();
        InfoCommand infoCommand = new InfoCommand(flowerName);

        boolean result = false;
        try {
            result = infoCommand.execute(bouquetList, ui);
        } catch (FlorizzException e) {
            fail("Unexpected Florizz Exception thrown: " + e.getMessage());
        }
        assertTrue(result, "Expected execute method to return true indicating successful execution");
    }

    @Test
    void testInfoCommandBabyBreath() {
        String flowerName = "Baby Breath";
        ArrayList<Bouquet> bouquetList = new ArrayList<>();
        Ui ui = new Ui();
        InfoCommand infoCommand = new InfoCommand(flowerName);

        boolean result = false;
        try {
            result = infoCommand.execute(bouquetList, ui);
        } catch (FlorizzException e) {
            fail("Unexpected Florizz Exception thrown: " + e.getMessage());
        }
        assertTrue(result, "Expected execute method to return true indicating successful execution");
    }

    @Test
    void testInfoCommandDaisy() {
        String flowerName = "Daisy";
        ArrayList<Bouquet> bouquetList = new ArrayList<>();
        Ui ui = new Ui();
        InfoCommand infoCommand = new InfoCommand(flowerName);

        boolean result = false;
        try {
            result = infoCommand.execute(bouquetList, ui);
        } catch (FlorizzException e) {
            fail("Unexpected Florizz Exception thrown: " + e.getMessage());
        }
        assertTrue(result, "Expected execute method to return true indicating successful execution");
    }

    @Test
    void testInfoCommandException() {
        String flowerName = "Non-existent Flower";
        ArrayList<Bouquet> bouquetList = new ArrayList<>();
        Ui ui = new Ui();
        InfoCommand infoCommand = new InfoCommand(flowerName);

        assertThrows(FlorizzException.class, () -> infoCommand.execute(bouquetList, ui));
    }
}
