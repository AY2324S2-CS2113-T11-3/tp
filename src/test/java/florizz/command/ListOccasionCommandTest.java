package florizz.command;

import florizz.core.Ui;
import florizz.objects.Bouquet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListOccasionCommandTest {

    @Test
    void executeListOccasionTest() {
        ArrayList<Bouquet> tempBouquetList = new ArrayList<>();
        Ui ui = new Ui();
        ListOccasionCommand occasionCommand = new ListOccasionCommand();
        assertTrue(occasionCommand.execute(tempBouquetList, ui));
    }
}
