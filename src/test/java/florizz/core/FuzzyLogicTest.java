package florizz.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuzzyLogicTest {

    @Test
    void testComputeDLDistance() throws FlorizzException { // Two-word command input
        String userInput = "invo";
        String validCommand = "info";
        int distance = FuzzyLogic.computeDLDistance(userInput, validCommand);
        assertEquals(1, distance);
    }

    @Test
    void testComputeDLDistance2() throws FlorizzException { // Two-word command input
        String userInput = "rimof";
        String validCommand = "remove";
        int distance = FuzzyLogic.computeDLDistance(userInput, validCommand);
        assertEquals(3, distance);
    }

    @Test
    void testComputeDLDistance3() throws FlorizzException { // Two-word command input
        String userInput = "mybouquets";
        String validCommand = "mybouquets";
        int distance = FuzzyLogic.computeDLDistance(userInput, validCommand);
        assertEquals(0, distance);
    }

    @Test
    void testDetectItemInfo() throws FlorizzException { // Two-word command input
        String userInput = "invo";
        String decodedInput = FuzzyLogic.detectItem(userInput);
        assertEquals("info", decodedInput);
    }

    @Test
    void testDetectItemOccasion() throws FlorizzException { // One-word command input
        String userInput = "okasion";
        String decodedInput = FuzzyLogic.detectItem(userInput);
        assertEquals("occasion", decodedInput);
    }

    @Test
    void testDetectItemEucalyptus() throws FlorizzException { // Flower
        String userInput = "eucalitus";
        String decodedInput = FuzzyLogic.detectItem(userInput);
        assertEquals("Eucalyptus", decodedInput);
    }

    @Test
    void testProcessCommand1() throws FlorizzException { // input starts with a valid command and input length != 1
        String userInput = "saves bouquetName";
        String processedInput = FuzzyLogic.processCommand(userInput);
        assertEquals("save bouquetName", processedInput);
    }

    @Test
    void testProcessCommand2() throws FlorizzException { // input starts with a valid command and input length == 1
        String userInput = "recommended";
        String processedInput = FuzzyLogic.processCommand(userInput);
        assertEquals("recommended", processedInput);
    }

    @Test
    void testProcessCommand3() throws FlorizzException { // processInput returns the original input with invalid command
        String userInput = "invo Ross";
        String processedInput = FuzzyLogic.processCommand(userInput);
        assertEquals("invo Ross", processedInput);
    }

    @Test
    void testSplitAndMergeInputAdd1() throws FlorizzException {
        String userInput = "addRose /q 10 /from bouquet";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("add Rose /q 10 /from bouquet", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputAdd2() throws FlorizzException {
        String userInput = "a         d    d Rose /q 10 /from bouquet";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("add Rose /q 10 /from bouquet", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputRemove1() throws FlorizzException {
        String userInput = "removeRose /q 10 /from bouquet";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("remove Rose /q 10 /from bouquet", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputRemove2() throws FlorizzException {
        String userInput = "r e m    ove Rose /q 10 /from bouquet";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("remove Rose /q 10 /from bouquet", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputSave1() throws FlorizzException {
        String userInput = "savebouquet123";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("save bouquet123", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputSave2() throws FlorizzException {
        String userInput = "s  av  e bouquet123";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("save bouquet123", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputDelete1() throws FlorizzException {
        String userInput = "deletebouquet";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("delete bouquet", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputDelete2() throws FlorizzException {
        String userInput = "d e l e t e bouquet";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("delete bouquet", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputNew1() throws FlorizzException {
        String userInput = "newmybouquets";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("new mybouquets", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputNew2() throws FlorizzException {
        String userInput = "n     ew mybouquets";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("new mybouquets", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputFlowers1() throws FlorizzException {
        String userInput = "flowersMothers Day";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("flowers Mothers Day", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputFlowers2() throws FlorizzException {
        String userInput = "fl      owe r  s Mothers Day";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("flowers Mothers Day", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputInfo1() throws FlorizzException {
        String userInput = "infoRose";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("info Rose", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputInfo2() throws FlorizzException {
        String userInput = "i n f   o Rose";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("info Rose", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputMyBouquets() throws FlorizzException {
        String userInput = "m y bou quets";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("mybouquets", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputFlowers() throws FlorizzException {
        String userInput = "f low ers";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("flowers", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputOccasion() throws FlorizzException {
        String userInput = "o cc asi on";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("occasion", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputRecommend() throws FlorizzException {
        String userInput = "re commen d";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("recommend", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputByw() throws FlorizzException {
        String userInput = "b    y            e";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("bye", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputHelp() throws FlorizzException {
        String userInput = "h elp";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("help", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputBack() throws FlorizzException {
        String userInput = "b a c    k";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("back", splitMergedInput);
    }

    @Test
    void testSplitAndMergeInputNext() throws FlorizzException {
        String userInput = "ne   xt";
        String splitMergedInput = FuzzyLogic.splitAndMergeInput(userInput);
        assertEquals("next", splitMergedInput);
    }

    @Test
    void testSplitInputDelete() throws FlorizzException {
        String userInput = "deleteBouquet For Girlfriend";
        String splitInput = FuzzyLogic.splitInput(userInput);
        assertEquals("delete Bouquet For Girlfriend", splitInput);
    }
    @Test
    void testSplitInputFlowers() throws FlorizzException {
        String userInput = "flowersMothers Day";
        String splitInput = FuzzyLogic.splitInput(userInput);
        assertEquals("flowers Mothers Day", splitInput);
    }
    @Test
    void testSplitInputNew() throws FlorizzException {
        String userInput = "newmother's day bouquet";
        String splitInput = FuzzyLogic.splitInput(userInput);
        assertEquals("new mother's day bouquet", splitInput);
    }

    @Test
    void testSplitInputAdd() throws FlorizzException {
        String userInput = "addLily /q 1 /to bouquetName";
        String splitInput = FuzzyLogic.splitInput(userInput);
        assertEquals("add Lily /q 1 /to bouquetName", splitInput);
    }

    @Test
    void testSplitInputInfo() throws FlorizzException {
        String userInput = "infoRose";
        String splitInput = FuzzyLogic.splitInput(userInput);
        assertEquals("info Rose", splitInput);
    }

    @Test // Test case for null input
    void testMergeInputNullInput() {
        assertThrows(FlorizzException.class, () -> FuzzyLogic.mergeInput(null));
    }

    @Test  // Test case for input with spaces
    void testMergeInputWithSpaces() throws FlorizzException {
        String userInput = "Hello World";
        String mergedInput = FuzzyLogic.mergeInput(userInput);
        assertEquals("HelloWorld", mergedInput);
    }

    @Test // Test case for input without spaces
    void testMergeInputNoSpaces() throws FlorizzException {
        String userInput = "NoSpacesHere";
        String mergedInput = FuzzyLogic.mergeInput(userInput);
        assertEquals("NoSpacesHere", mergedInput);
    }
}
