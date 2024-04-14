package florizz.core;


import java.util.ArrayList;
import java.util.Arrays;

import florizz.objects.Flower;

/**
 * A class that contains a dictionary of preset flowers
 */
public class FlowerDictionary {
    private static final ArrayList<Flower> flowerDict = new ArrayList<Flower>();
    /**
     * Adds a new flower to the flower dictionary
     *
     * @param name      Name of flower to be added
     * @param colour    Colour of flower to be added
     * @param occasions Occasions that the flower can be bought for
     */
    private static void add(String name, String colour, String[] occasions,
                            Double price, String[] meanings, Flower.Type type) {

        Flower.Colour colourEnum = Flower.Colour.valueOf(colour.replaceAll(" ", "_").toUpperCase());
        ArrayList<Flower.Occasion> occasionsArrayList = new ArrayList<>();
        for (String occasion : occasions) {
            occasionsArrayList.add(Flower.Occasion.valueOf(occasion.replaceAll(" ", "_").toUpperCase()));
        }
        ArrayList<String> meaningsArrayList = new ArrayList<>(Arrays.asList(meanings));
        flowerDict.add(new Flower(name, colourEnum, occasionsArrayList, price, meaningsArrayList, type));
    }


    /**
     * Adds flowers to the dictionary when florizz starts up (temporary)
     */
    public static void startup() {
        add("Orchid", "White", new String[]{"Wedding"}, 10.00,
                new String[]{"Innocence", "Respect", "Beauty"}, Flower.Type.FLOWER);
        add("Rose", "Dark Crimson", new String[]{"Funeral"}, 2.00,
                new String[]{"Mourning"}, Flower.Type.FLOWER);
        add("Rose", "Red", new String[]{"Valentines", "Wedding", "Mothers Day"}, 2.00,
                new String[]{"Love"}, Flower.Type.FLOWER);
        add("Rose", "Yellow", new String[]{}, 2.00,
                new String[]{"Jealousy, Decrease of love, Infidelity"}, Flower.Type.FLOWER);
        add("Lily", "White", new String[]{"Funeral", "Wedding"}, 2.50,
                new String[]{"Innocence"}, Flower.Type.FLOWER);
        add("Lily", "Orange", new String[]{}, 2.50,
                new String[]{"Hatred"}, Flower.Type.FLOWER);
        add("Daisy", "White", new String[]{"Valentines"}, 0.50,
                new String[]{"Innocence"}, Flower.Type.FLOWER);
        add("Chrysanthemum", "White", new String[]{"Funeral"}, 1.00,
                new String[]{"Love", "Loyalty", "Innocence"}, Flower.Type.FLOWER);
        add("Hydrangea", "Blue", new String[]{"Wedding"}, 9.00,
                new String[]{"Forgiveness", "Gratitude"}, Flower.Type.FLOWER);
        add("Carnation", "Pink", new String[]{"Mothers Day"}, 2.00,
                new String[]{"Gratitude", "Love"}, Flower.Type.FLOWER);
        add("Carnation", "Red", new String[]{"Valentines"}, 2.00,
                new String[]{"My heart aches", "Deep Love"}, Flower.Type.FLOWER);
        // [Fillers have yet to be implemented]
        add("Baby Breath", "White", new String[]{"Wedding", "Valentines", "Mothers Day"}, 1.00,
                new String[]{"Innocence", "Kindness", "Care", "Humble"}, Flower.Type.FLOWER);
        add("Eucalyptus", "Green", new String[]{"Wedding"}, 1.5, new String[]{"Love", "Kindness"}, Flower.Type.FLOWER);
        add("Dusty Miller", "Green", new String[]{}, 1.5, new String[]{}, Flower.Type.FILLER);
        add("Pistacia", "Green", new String[]{}, 1.5, new String[]{}, Flower.Type.FILLER);
        add("Pittosporum", "Green", new String[]{}, 1.5, new String[]{}, Flower.Type.FILLER);
        add("Chamomile", "White", new String[]{}, 1.9, new String[]{}, Flower.Type.FILLER);
        add("Astilbe", "Pink", new String[]{}, 2.8, new String[]{}, Flower.Type.FILLER);
        add("Hypericum", "Red", new String[]{}, 2.0, new String[]{}, Flower.Type.FILLER);
        add("Freesia", "White", new String[]{}, 1.9, new String[]{}, Flower.Type.FILLER);
        add("Helichrysum", "Yellow", new String[]{}, 1.5, new String[]{}, Flower.Type.FILLER);
        add("Limonium", "Red", new String[]{}, 1.8, new String[]{}, Flower.Type.FILLER);
        add("Limonium", "Dark Crimson", new String[]{}, 1.8, new String[]{}, Flower.Type.FILLER);
        add("Limonium Perezii", "Purple", new String[]{}, 1.8, new String[]{}, Flower.Type.FILLER);
        add("Statice", "Blue", new String[]{}, 1.5, new String[]{}, Flower.Type.FILLER);
        add("Statice", "Purple", new String[]{}, 1.5, new String[]{}, Flower.Type.FILLER);
        add("Rice Flower", "Pink", new String[]{}, 1.8, new String[]{}, Flower.Type.FILLER);
    }

    /**
     * Returns the size of flower dictionary
     *
     * @return The size as an int
     */
    public static int size() {
        return flowerDict.size();
    }

    /**
     * Gets the flower at the selected index in the flower dictionary
     *
     * @param i Index to get flower from
     * @return The flower object at index i
     */
    public static Flower get(int i) {
        return flowerDict.get(i);
    }

    /**
     * Gets a list of flowers that suit that occasion
     *
     * @param occasion occasion to filter flowers by
     * @return an ArrayList of flowers to be printed by ui
     */
    public static ArrayList<Flower> filterByOccasion(Flower.Occasion occasion) {
        ArrayList<Flower> filteredFlowers = new ArrayList<>();
        for (Flower flower : flowerDict) {
            if (flower.getOccasion().contains(occasion)) {
                filteredFlowers.add(flower);
            }
        }
        return filteredFlowers;
    }

    /**
     * Gets a list of flowers that contain the name search
     *
     * @param name name of Flowers to filter by
     * @return an ArrayList of Flowers to be printed by ui
     */
    public static ArrayList<Flower> filterByName(String name) throws FlorizzException {
        ArrayList<Flower> filteredFlowers = new ArrayList<>();
        for (Flower flower : flowerDict) {
            if (flower.getFlowerName().toLowerCase().contains(name.toLowerCase())) {
                filteredFlowers.add(flower);
            }
        }
        if (filteredFlowers.isEmpty()) {
            throw new FlorizzException("Flower name is unidentified.");
        }
        return filteredFlowers;
    }

    public static ArrayList<Flower> filterByName(ArrayList<Flower> listOfFlowers, String name) throws FlorizzException {
        ArrayList<Flower> filteredFlowers = new ArrayList<>();
        for (Flower flower : listOfFlowers) {
            if (flower.getFlowerName().toLowerCase().contains(name.toLowerCase())) {
                filteredFlowers.add(flower);
            }
        }
        if (filteredFlowers.isEmpty()) {
            throw new FlorizzException("Flower name is unidentified.");
        }
        return filteredFlowers;
    }

    /**
     * Gets a list of flowers that contain the colour search
     *
     * @param colour colour of Flowers to filter by
     * @return an ArrayList of Flowers to be printed by ui
     */
    public static ArrayList<Flower> filterByColour(Flower.Colour colour) {
        ArrayList<Flower> filteredFlowers = new ArrayList<>();
        for (Flower flower : flowerDict) {
            if (flower.getColour().equals(colour)) {
                filteredFlowers.add(flower);
            }
        }
        return filteredFlowers;
    }

    /**
     * Gets a list of flowers that contains the colour based on the list of flowers inputted
     *
     * @param listOfFlowers list of flowers to be filtered
     * @param colour        colour of Flowers to filter by
     * @return an ArrayList of Flowers to be printed by ui
     */
    public static ArrayList<Flower> filterByColour(ArrayList<Flower> listOfFlowers, Flower.Colour colour) {
        ArrayList<Flower> filteredFlowers = new ArrayList<>();
        for (Flower flower : listOfFlowers) {
            if (flower.getColour().equalsIgnoreCase(Flower.colourToString(colour))) {
                filteredFlowers.add(flower);
            }
        }
        return filteredFlowers;
    }

    public static ArrayList<Flower> getAllFlowers(){
        return flowerDict;

    }
}
