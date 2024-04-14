package florizz.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserTest {
    @Test
    void testParse() throws FlorizzException {
        String userInput = "Gibberish Input";
        boolean enableUi = true;

        assertThrows(FlorizzException.class, () -> Parser.parse(userInput, enableUi));
    }
}
