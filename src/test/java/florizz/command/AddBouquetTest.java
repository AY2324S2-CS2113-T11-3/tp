package florizz.command;

import florizz.core.FlorizzException;
import florizz.core.Ui;
import florizz.objects.Bouquet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddBouquetTest {
    @Test
    void testAddCommandExecute1() {
        Bouquet testBouquet = new Bouquet("for testing"); // Two-word bouquetName
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        ArrayList<Bouquet> testList = new ArrayList<>();
        controlList.add(testBouquet);
        Command testAddBouquetCommand = new AddBouquetCommand(testBouquet, true);
        try {
            assertTrue(testAddBouquetCommand.execute (testList, ui));
        } catch (FlorizzException error){
            ui.printError(error);
        }
        assertEquals(controlList, testList);
    }

    @Test
    void testAddCommandExecute2() {
        Bouquet testBouquet = new Bouquet("add"); // bouquetName that is also a command name
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        ArrayList<Bouquet> testList = new ArrayList<>();
        controlList.add(testBouquet);
        Command testAddBouquetCommand = new AddBouquetCommand(testBouquet, true);
        try {
            assertTrue(testAddBouquetCommand.execute (testList, ui));
        } catch (FlorizzException error){
            ui.printError(error);
        }
        assertEquals(controlList, testList);
    }

    @Test
    void testAddCommandExecute3() {
        Bouquet testBouquet = new Bouquet("abc"); // One word bouquetName
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        ArrayList<Bouquet> testList = new ArrayList<>();
        controlList.add(testBouquet);
        Command testAddBouquetCommand = new AddBouquetCommand(testBouquet, true);
        try {
            assertTrue(testAddBouquetCommand.execute (testList, ui));
        } catch (FlorizzException error){
            ui.printError(error);
        }
        assertEquals(controlList, testList);
    }

    @Test
    void testAddCommandExecuteException() {
        Bouquet testBouquet = new Bouquet("");
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        controlList.add(testBouquet);
        Command testAddBouquetCommand = new AddBouquetCommand(testBouquet, true);

        assertThrows(FlorizzException.class, () ->  testAddBouquetCommand.execute(controlList, ui));
    }
}
