package florizz.core;

import florizz.objects.Bouquet;
import florizz.objects.Flower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Ui {
    private static ArrayList<Flower> lastShownList = new ArrayList<>();
    private static int lastPageNo = 0;
    private static final int PAGE_SIZE = 5;
    private static final Scanner inputScanner = new Scanner (System.in);

    private static String lastCommand = "";
    /**
     * Prints the introductory message.
     */
    public void printIntroMessage(){
        String logo = "\n" +
                "   __ _            _\n" +
                "  / _| |          (_)\n" +
                " | |_| | ___  _ __ _ ________\n" +
                " |  _| |/ _ \\| '__| |_  /_  /\n" +
                " | | | | (_) | |  | |/ / / /\n" +
                " |_| |_|\\___/|_|  |_/___/___|\n" +
                "\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("Type `help`, to view a list of valid commands!");
    }

    /**
     * Prints a break line.
     */
    public static void printBreakLine(){
        System.out.println(("____________________________________________________________"));
    }

    /**
     * Gets input from the user typing commands into the CLI.
     * @return Returns the user input as one String.
     */
    public String getInput(){
        try{
            return inputScanner.nextLine();
        } catch(NoSuchElementException error){
            return "bye";
        }
    }

    /**
     * Prints the message indicating a new bouquet has been added.
     * @param bouquetAdded The bouquet that has been added.
     */
    public void printBouquetAdded(Bouquet bouquetAdded){
        lastCommand = "OTHERS";
        System.out.println("Added new bouquet to list:\n" + bouquetAdded);
        printBreakLine();

    }

    /**
     * Prints the message indicating a bouquet has been deleted.
     * @param bouquetDeleted The bouquet that has been deleted.
     */
    public void printBouquetDeleted(Bouquet bouquetDeleted){
        lastCommand = "OTHERS";
        System.out.println("Deleted bouquet:\n" + bouquetDeleted);
        printBreakLine();

    }

    /**
     * Prints all saved bouquets with their flowers and total estimated price.
     * @param bouquetList The list of saved bouquets.
     */
    public void printAllBouquets(ArrayList<Bouquet> bouquetList){
        lastCommand = "BOUQUETS";
        System.out.println("Here is the list of your saved bouquets:");
        int i = 1;
        double totalPrice;
        for (Bouquet bouquet : bouquetList){
            System.out.println(i++ + ". " + bouquet + " :");
            HashMap<Flower, Integer> flowerHashMap = bouquet.getFlowerHashMap();
            totalPrice = 0;
            if (!flowerHashMap.isEmpty() ) {
                for (Flower j : flowerHashMap.keySet()) {
                    if (flowerHashMap.get(j) != 0) {
                        System.out.println("    - " + flowerHashMap.get(j) + " x " + j.getNameAndColour());
                        totalPrice += (flowerHashMap.get(j) * j.getPrice());
                    }
                }
                System.out.println("  Total estimated price = $" + String.format("%.2f", (double) totalPrice));
            } else {
                System.out.println("      No flowers added so far");
            }

        }
        printBreakLine();
    }

    /**
     * Prints all flowers in a bouquet.
     * @param bouquet The bouquet to print.
     */
    public void printFullBouquet(Bouquet bouquet) {
        System.out.println("Here is the full list of flowers in " + bouquet.getBouquetName() + ":");
        HashMap<Flower, Integer> flowerHashMap = bouquet.getFlowerHashMap();
        if (!flowerHashMap.isEmpty()) {
            for (Flower j : flowerHashMap.keySet()) {
                System.out.println("    - " + flowerHashMap.get(j) + " x " + j.getFlowerName());
            }
        } else {
            System.out.println("      No flowers added so far");
        }
        printBreakLine();
    }

    /**
     * print all available command
     */
    public void printHelpMessage() {
        lastCommand = "OTHERS";
        System.out.println("Here are the list of commands you can use:");
        System.out.println("1. new <bouquetName> - Add a bouquet");
        System.out.println("2. delete <bouquetName> - Delete a bouquets");
        System.out.println("3. mybouquets - List current saved bouquets");
        System.out.println("4. info <flowerName> - Provide information on chosen flower");
        System.out.println("5. add <flowerName> /c <colour> (optional) /q <quantity> " +
                "/to <bouquetName> - add flower to a bouquet");
        System.out.println("6. remove <flowerName> /c <colour> (optional) /q <quantity> " +
                "/from <bouquetName> - remove flower from a bouquet");
        System.out.println("7. flowers - Shows a list of flowers that can be added into mybouquets");
        System.out.println("8. flowers <occasion> - Shows a list of flowers associated with said occasion");
        System.out.println("9. occasion - Shows a list of occasions associated with available flowers");
        System.out.println("10. save <bouquetName> - Saves a bouquet to an external <bouquetName>.txt file");
        System.out.println("11. recommend - Recommends a bouquet based on the chosen occasion and colour");
        System.out.println("12. bye - Exits the programme");
        printBreakLine();
    }

    /**
     * Prints error message thrown by Florizz Exception.
     *
     * @param error Error message
     */
    public void printError(FlorizzException error){
        System.out.println(error.errorMessage);
        printBreakLine();
    }

    /**
     * Prints exit message.
     */
    public void printExitMessage() {
        System.out.println("Enjoy your bouquet! Thank you for using Florizz!");
        printBreakLine();
    }


    private static void printNextOrBack(int pageNo, int maxPages){

    }
    /**
     * Prints a list of flowers with optional additional information.
     *
     * @param needsInfo True if additional information about the flowers is required, false otherwise
     */
    private void printFlowerList(boolean needsInfo){
        int maxPages = (int) Math.ceil((double)lastShownList.size() / PAGE_SIZE);
        for (int i = (lastPageNo-1)*PAGE_SIZE; i < Math.min(lastPageNo*PAGE_SIZE, lastShownList.size()); i++) {
            if (needsInfo) {
                System.out.println(i+1 + ". " + lastShownList.get(i));
            } else {
                System.out.println(i + 1 + ". " + lastShownList.get(i).getNameAndColour());
            }
        }
        if (lastPageNo < maxPages && lastPageNo > 1){
            System.out.println("Type 'next' to go to the next page, or 'back' to go to the previous page.");
        } else if (lastPageNo < maxPages){
            System.out.println("Type 'next' to go to the next page.");
        } else if (lastPageNo > 1){
            System.out.println("Type 'back' to go to the previous page.");
        }
        printBreakLine();
    }
    /**
     * Prints flowers in the dictionary
     */
    public void printAllDictFlowerName(int pageNo) {
        lastShownList = FlowerDictionary.getAllFlowers();
        lastPageNo = pageNo;
        lastCommand = "ALL_FLOWERS";
        int maxPages = (int) Math.ceil((double)lastShownList.size() / PAGE_SIZE);
        System.out.println("Showing page "  + pageNo + "/" + maxPages
                + " of all the flowers you can add:");
        printFlowerList(false);
    }

    /**
     * Prints filtered flowers based on a given filter.
     * @param flowers The list of flowers to filter.
     * @param filter The filter to apply to the flowers.
     */
    public void printFilteredFlowers(ArrayList<Flower> flowers, String filter, int pageNo){
        lastShownList = flowers;
        lastPageNo = pageNo;
        lastCommand = "FILTERED_FLOWERS " + filter;
        int maxPages = (int) Math.ceil((double)lastShownList.size() / PAGE_SIZE);
        System.out.println("Here is page " + lastPageNo + "/" + maxPages +
                " of all the flowers related to " + filter + ":");
        printFlowerList(false);
    }

    /**
     * Prints name, colour, occasion and price information about a specific flower.
     * @param targetFlower The name of the flower the user searched for.
     * @param flowers The list of flowers that contain that name.
     */
    public void printFlowerInfo(ArrayList<Flower> flowers, String targetFlower, int pageNo) {
        lastShownList = flowers;
        lastPageNo = pageNo;
        lastCommand = "INFO_FLOWERS " + targetFlower;
        int maxPages = (int) Math.ceil((double)lastShownList.size() / PAGE_SIZE);
        System.out.println("Here is page " + lastPageNo + "/" + maxPages +
                " of info regarding flowers whose name contains " + targetFlower + ":");
        printFlowerList(true);
    }

    /**
     * Prints the next page of flower information based on the last command executed.
     *
     * @throws FlorizzException If there are no more pages to display or if there is no list of flowers to view.
     */
    public void printNextPage() throws FlorizzException{
        switch (lastCommand.split(" ")[0]) {
        case ("ALL_FLOWERS"):
            if (lastPageNo*PAGE_SIZE >= lastShownList.size()){
                throw new FlorizzException("There is no next page, type 'back' to go to the previous page");
            }
            printAllDictFlowerName(lastPageNo+1);
            break;
        case ("FILTERED_FLOWERS"):
            if (lastPageNo*PAGE_SIZE >= lastShownList.size()){
                throw new FlorizzException("There is no next page, type 'back' to go to the previous page");
            }
            printFilteredFlowers(lastShownList, lastCommand.split(" ")[1],lastPageNo+1);
            break;
        case ("INFO_FLOWERS"):
            if (lastPageNo*PAGE_SIZE >= lastShownList.size()){
                throw new FlorizzException("There is no next page, type 'back' to go to the previous page");
            }
            printFlowerInfo(lastShownList, lastCommand.split(" ")[1], lastPageNo+1);
            break;
        default:
            throw new FlorizzException("There is no list of flowers to view. " +
                    "Type 'flowers' to view a list of all flowers.");
        }
    }

    /**
     * Prints the previous page of flower information based on the last command executed.
     *
     * @throws FlorizzException If there are no previous pages to display or if there is no list of flowers to view.
     */
    public void printBackPage() throws FlorizzException{
        switch (lastCommand.split(" ")[0]) {
        case ("ALL_FLOWERS"):
            if (lastPageNo == 1){
                throw new FlorizzException("There is no previous page, type 'next' to go to the next page");
            }
            printAllDictFlowerName(lastPageNo-1);
            break;
        case ("FILTERED_FLOWERS"):
            if (lastPageNo == 1){
                throw new FlorizzException("There is no previous page, type 'next' to go to the next page");
            }
            printFilteredFlowers(lastShownList, lastCommand.split(" ")[1],lastPageNo-1);
            break;
        case ("INFO_FLOWERS"):
            if (lastPageNo == 1){
                throw new FlorizzException("There is no previous page, type 'next' to go to the next page");
            }
            printFlowerInfo(lastShownList, lastCommand.split(" ")[1], lastPageNo-1);
            break;
        default:
            throw new FlorizzException("There is no list of flowers to view. " +
                    "Type 'flowers' to view a list of all flowers.");
        }
    }

    /**
     * Prints all possible occasions the user can query
     */
    public void printAllOccasions() {
        lastCommand = "OTHERS";
        System.out.println("Here are all the occasions associated with the available flowers:");
        for (Flower.Occasion occasion : Flower.Occasion.values()){
            System.out.println("- " + Flower.occasionToString(occasion));
        }

        printBreakLine();
    }

    /**
     * print ui if flower added successfully
     *
     * @param bouquetList The list of bouquets.
     * @param flowerName The name of the flower added.
     * @param quantity The quantity of the flower added.
     * @param bouquetName The name of the bouquet to which the flower was added.
     */
    public void printAddFlowerSuccess(ArrayList<Bouquet> bouquetList,
                                      String flowerName, Integer quantity, String bouquetName) {
        lastCommand = "OTHERS";
        System.out.println("You have successfully added the following:" + System.lineSeparator() +
                           "    - " + quantity + " x " + flowerName + " -> Bouquet: " + bouquetName);
        printAllBouquets(bouquetList);
    }

    /**
     * print ui if flower removed successfully
     *
     * @param bouquetList The list of bouquets.
     * @param flowerName The name of the flower removed.
     * @param quantity The quantity of the flower removed.
     * @param bouquetName The name of the bouquet from which the flower was removed.
     */
    public void printRemoveFlowerSuccess(ArrayList<Bouquet> bouquetList,
                                         String flowerName, Integer quantity, String bouquetName) {
        lastCommand = "OTHERS";
        System.out.println("You have successfully removed the following:" + System.lineSeparator() +
                           "    - " + quantity + " x " + flowerName + " -> Bouquet: " + bouquetName);
        printAllBouquets(bouquetList);
    }

    /**
     * print ui if flower removed can't be found
     *
     * @param bouquetList The list of bouquets.
     * @param flowerName The name of the flower that couldn't be found.
     * @param bouquetName The name of the bouquet in which the flower couldn't be found.
     */
    public void printRemoveFlowerUnsuccessful(ArrayList<Bouquet> bouquetList, String flowerName, String bouquetName) {
        lastCommand = "OTHERS";
        System.out.println(flowerName + " cannot be found in bouquet: " + bouquetName);
        printAllBouquets(bouquetList);
    }

    /**
     * Prints a message to the UI indicating a fuzzy input detection.
     *
     * @param userInput The user's input.
     * @param bestMatch The best matching suggestion for the user's input.
     */
    public void printFuzzyInputDetection (String userInput, String bestMatch) {
        System.out.println("--> Your input is [" + userInput
                + "] but I am guessing you mean [" + bestMatch + "]");
    }

    /**
     * print if IOException is caught in a try catch block
     */
    public void printIOError() {
        System.out.println("ERROR: IO Error Encountered Xd");
    }

    /**
     * ask user for occasion input
     */
    public void printAskOccasion() {
        System.out.println("For what occasion are you buying flowers for?");
        this.printAllOccasions();
        System.out.println("Type 'cancel' if you would like to exit the recommendation page");
    }

    /**
     * Prints prompt to ask user to input bouquet name for recommended bouquet
     */
    public void printAskBouquetName() {
        System.out.println("Great we managed to find some flowers for you!");
        System.out.println("Before we carry on what would you like to call your bouquet?");
        System.out.println("Note: please take note 'cancel' cannot be used as a  bouquet name");
    }

    /**
     * Asks user for colour input
     * @param eligibleFlowers List of flowers that are eligible for the occasion
     */
    public void printAskColour(ArrayList<Flower> eligibleFlowers) {
        System.out.println("What colour would you like your bouquets to be?");

        // print all available colours in a given array list
        System.out.println("Here is the list of colours available for the occasion:");
        // remove duplicate colours in eligible flowers
        ArrayList<String> colourList = new ArrayList<>();
        for (Flower flower : eligibleFlowers) {
            if(!colourList.contains(flower.getColour())){
                colourList.add(flower.getColour());
            }
        }
        for (String colour : colourList){
            System.out.println("- " + colour);
        }
        System.out.println("Type 'cancel' if you would like to exit the recommendation page");
        printBreakLine();
    }

    /**
     * Prints a prompt asking the user if they would like to save a recommended bouquet to their list.
     * Prompts the user to input 'yes' to save the bouquet or 'no' to discard it.
     *
     * @param recommendedBouquet The recommended bouquet to be saved
     * @return The user's input ('yes' or 'no')
     */
    public String printAskSaveBouquet(Bouquet recommendedBouquet) {
        System.out.println("Would you like to save this bouquet to your list?");
        printFullBouquet(recommendedBouquet);
        System.out.println("Type 'yes' to save, 'no' to discard");
        return inputScanner.nextLine();
    }

    /**
     * Prints out to user when bouquet has been successfully saved
     * @param bouquetName Bouquet to save externally
     */
    public void printSaveSuccess(String bouquetName) {
        System.out.println("Successfully saved " + bouquetName + ". You can find it at 'florizz-out/saved/"
                + bouquetName + ".txt'");
        printBreakLine();
    }

    /**
     * Asks the user to choose a colour of a flower from a list of the flowers.
     * @param flowers The list of flowers where the user can choose the colour from
     * @param flowerName The name of the flower that the user is trying to choose its colour from
     * @return Flower the specific Flower with the correct colour. Is blank if user chose to cancel the command instead
     */
    public Flower chooseColour(ArrayList<Flower> flowers, String flowerName){
        printGetFlowerColour(flowers, flowerName);

        while (true){
            String colourInput = getInput().trim().toLowerCase();
            try {
                switch (colourInput) {
                case "back":
                    printBackPage();
                    break;
                case "next":
                    printNextPage();
                    break;
                case "cancel":
                    return new Flower();
                default:
                    Flower.Colour chosenColour = Flower.stringToColour(colourInput);
                    ArrayList<Flower> chosenFlowerList = FlowerDictionary.filterByColour(flowers, chosenColour);
                    if (!chosenFlowerList.isEmpty()){
                        return chosenFlowerList.get(0);
                    } else {
                        throw new FlorizzException("This flower is not available in this colour, " +
                                "try typing a colour shown above!");
                    }

                }
            } catch(FlorizzException error){
                printError(error);
            } catch(IllegalArgumentException error){
                System.out.println("Unrecognised input, type a colour that's available for this flower, " +
                        "or 'cancel' to go back to the main menu.");
            }
        }
    }

    /**
     * Prints information about the available colors of a flower and prompts the user to choose the color.
     * Displays information about the flower, including its name and available colors.
     * Prompts the user to input the desired color or 'cancel' to return to the main menu.
     *
     * @param flowers The list of flowers containing the flower with multiple colors
     * @param flowerName The name of the flower with multiple colors
     */
    public void printGetFlowerColour(ArrayList<Flower> flowers, String flowerName){
        System.out.println("The flower you're looking for has more than one colour available, " +
                "each with their own vastly different meanings. Here's some info:");
        printFlowerInfo(flowers, flowerName, 1);
        System.out.println("Type the colour you want to add into the bouquet, or 'cancel' to return to the main menu.");
    }

    /**
     * Prints a message indicating that the command has been canceled and the user is returning to the main menu.
     */
    public void printCancelCommand(){
        System.out.println("Canceled command, returning to main menu.");
    }
}

