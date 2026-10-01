package image_char_matching;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SubImgCharMatcher {



    private static final String BLANCK_SPACE = " ";
    private static final String ABS = "abs";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final int SMALL_CHAR = 32;
    private static final int BIG_CHAR = 127;
    private  static final TreeMap<Character, Double> asciBritnesBasic = new TreeMap<>();
    private static final int ZERO = 0;
    // TreeMap to store character brightness as key and corresponding character as value.
    private TreeMap<Double, Character> asciBritnes;

    // Variables to track minimum and maximum brightness values
    private double minBright;
    private double maxBright;
    private String roundMethod;
    //private BrightnessStrategy strategy;
//    private BrightnessStrategy brightnessStrategy;

    /*
     *Saves a static treemap of all the different chars
     */
    static {
        for (char c = SMALL_CHAR; c < BIG_CHAR; c++) {
            asciBritnesBasic.put(c, getCharBright(c));
        }}
    /**
     * Constructor initializes the map with a given charset.
     * It calculates brightness for each character and normalizes the brightness values.
     *
     * @param charset an array of characters to initialize the matcher.
     */
    //public SubImgCharMatcher(char[] charset, BrightnessStrategy strategy){
    public SubImgCharMatcher(char[] charset){
        //this.strategy = strategy;
        asciBritnes = new TreeMap<>();
        this.roundMethod = ABS;
        for (char c : charset){
            asciBritnes.put(asciBritnesBasic.get(c),c);
        }
        minBright = asciBritnes.firstKey();
        maxBright = asciBritnes.lastKey();
        normalByLinerStrech();
    }

    /*
     * Normalizes the brightness values in the map to the range [0, 1] using linear stretching.
     */

    private void normalByLinerStrech() {
        TreeMap<Double, Character> normalizedMap = new TreeMap<>();

        for (Map.Entry<Double, Character> entry : asciBritnes.entrySet()) {
            double normalizedBrightness = (entry.getKey() - minBright) / (maxBright - minBright);
            normalizedMap.put(normalizedBrightness, entry.getValue());
        }

        asciBritnes.clear();
        asciBritnes.putAll(normalizedMap);
    }

    /*
     Calculates the brightness of a character.
     */

    private static double getCharBright(char c) {
        double whiteCounter = ZERO;
        boolean[][] charByBoll = CharConverter.convertToBoolArray(c);
        for (boolean[] bol:charByBoll) {
            for (boolean b : bol) {
                if (b) {
                    whiteCounter++;
                }
            }
        }
        return  whiteCounter /charByBoll.length;
    }
//    public void setBrightnessStrategy(BrightnessStrategy strategy) {
//        this.brightnessStrategy = strategy;

    /**
     * Retrieves the character whose brightness is closest to the given brightness value.
     *
     * @param brightness the target brightness value.
     * @return the character that best matches the given brightness.
     */


    public char getCharByImageBrightness(double brightness) {
//        Double lowerKey = asciBritnes.lowerKey(brightness);
//        Double higherKey = asciBritnes.higherKey(brightness);
//        if (lowerKey == null) return asciBritnes.get(higherKey);
//        if (higherKey == null) return asciBritnes.get(lowerKey);
//        return strategy.getClosestChar(asciBritnes, lowerKey, higherKey, brightness);
//    }
        Double lowerKey = asciBritnes.lowerKey(brightness);
        Double higherKey = asciBritnes.higherKey(brightness);
        if (lowerKey == null) return asciBritnes.get(higherKey);
        if (higherKey == null) return asciBritnes.get(lowerKey);
        switch (this.roundMethod) {

            case UP:
                return asciBritnes.get(higherKey);

            case DOWN:
                return asciBritnes.get(lowerKey);
            default:
                    if (brightness - lowerKey > higherKey - brightness) {
                        return asciBritnes.get(higherKey);}
                    else {
                        return asciBritnes.get(lowerKey);
                    }
        }
    }

    /**
     * sets the whey to round in method getCharByImageBrightness.
     * @param roundMethod String(abs,up,down).
     */

    public void setRoundMethod(String roundMethod) {
        this.roundMethod = roundMethod;
    }

    /**
     * Adds a new character to the map and updates the normalization if necessary.
     *
     * @param c the character to add.
     */
    public void addChar(char c){
        double charBrightness = (asciBritnesBasic.get(c) - minBright) / (maxBright - minBright);
        if(asciBritnes.containsKey(charBrightness)){
            return;
        }
        asciBritnes.put(charBrightness,c);
        if (asciBritnesBasic.get(c)>maxBright){
            maxBright = asciBritnesBasic.get(c);
            normalByLinerStrech();
        }
        else if (asciBritnesBasic.get(c)<minBright){
            minBright = asciBritnesBasic.get(c);
            normalByLinerStrech();
        }
    }
    /**
     * Removes a character from the map and updates the normalization if necessary.
     *
     * @param c the character to remove.
     */
    public void removeChar(char c){
        double charBrightness = (asciBritnesBasic.get(c) - minBright) / (maxBright - minBright);
        asciBritnes.remove(charBrightness);
        if (asciBritnesBasic.get(c)==maxBright) {
            maxBright = asciBritnes.lastKey();
            normalByLinerStrech();
        }
        else if (asciBritnesBasic.get(c) == minBright){
            minBright = asciBritnes.firstKey();
            normalByLinerStrech();
        }
        //System.out.println(asciBritnes.get(charBrightness));
    }
}




