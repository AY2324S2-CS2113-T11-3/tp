package florizz.command;

import florizz.core.FlorizzException;
import florizz.core.Ui;
import florizz.objects.Bouquet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteBouquetTest {
    @Test
    void testDeleteCommandExecute1() {
        Bouquet testBouquet = new Bouquet("for testing"); // Two-word bouquetName
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        ArrayList<Bouquet> testList = new ArrayList<>();
        controlList.add(testBouquet);
        testList.add(testBouquet);
        Command testDeleteBouquetCommand = new DeleteBouquetCommand(testBouquet);
        controlList.remove(testBouquet);
        try {
            assertTrue(testDeleteBouquetCommand.execute(testList, ui));
        } catch(FlorizzException error){
            ui.printError(error);
        }
        assertEquals(controlList, testList);
    }

    @Test
    void testDeleteCommandExecute2() {
        Bouquet testBouquet = new Bouquet("mybouquets"); // bouquetName which is also a valid command name
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        ArrayList<Bouquet> testList = new ArrayList<>();
        controlList.add(testBouquet);
        testList.add(testBouquet);
        Command testDeleteBouquetCommand = new DeleteBouquetCommand(testBouquet);
        controlList.remove(testBouquet);
        try {
            assertTrue(testDeleteBouquetCommand.execute(testList, ui));
        } catch(FlorizzException error){
            ui.printError(error);
        }
        assertEquals(controlList, testList);
    }

    @Test
    void testDeleteCommandExecuteException(){
        Bouquet testBouquet = new Bouquet("myBouquet"); // One-word bouquetName
        Ui ui = new Ui();
        ArrayList<Bouquet> controlList = new ArrayList<>();
        controlList.add(testBouquet);
        Command testDeleteBouquetCommand = new DeleteBouquetCommand(testBouquet);
        controlList.remove(testBouquet);

        assertThrows(FlorizzException.class, () ->  testDeleteBouquetCommand.execute(controlList, ui));
    }
}
