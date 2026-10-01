package ascii_art;

import image.Image;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;
import java.io.IOException;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class AsciiArtAlgorithm {

    private static final int ZERO = 0;
    private Image img;
    private int resolution;
    private Set<Character> charset;
    private SubImgCharMatcher charMatcher;
    private String output;
    private String round;
    private boolean equalToLastImageAndResolution;
    private static double[][] lastBrightnessImage;

    /**
     * Constructor to initialize the AsciiArtAlgorithm with
     * the image, resolution, character matcher, rounding method,
     * and a flag to check if the image and resolution are equal to the last processed ones.
     *
     * @param img The input image to be converted to ASCII art.
     * @param resolution The resolution for splitting the image into sub-images.
     * @param matcher The character matcher used for mapping brightness to ASCII characters.
     * @param round The rounding method to use when mapping brightness to
     *             characters (e.g., "up", "down", "abs").
     * @param equalToLastImageAndResolution A flag indicating whether the image
     *                                      and resolution are equal to the last ones processed.
     */
    public AsciiArtAlgorithm(Image img, int resolution, SubImgCharMatcher matcher, String round,
                             boolean equalToLastImageAndResolution) {
        this.img = img;
        this.resolution = resolution;
        this.charMatcher = matcher;
        this.round = round;
        this.equalToLastImageAndResolution = equalToLastImageAndResolution;
    }

    /**
     * The main method that runs the ASCII art generation process.
     * It either generates a new ASCII art based on the current image and resolution or
     * reuses the previously computed
     * brightness image if the flag indicates that the image and resolution are the same.
     *
     * @return A 2D array of characters representing the ASCII art of the image.
     */
    public char[][] run() {
        if (equalToLastImageAndResolution){

            return fellAsciiArt(lastBrightnessImage.length,lastBrightnessImage[ZERO].length);

        }
        else {
            // Pad the image to the nearest power of 2
            img = ImageProcessor.padImageToNearestPowerOfTwo(img);

            // Split the image into square sub-images based on resolution
            ImageProcessor imageProcessor = new ImageProcessor();
            List<Color[][]> subImages = imageProcessor.splitIntoSquareSubImages(img, resolution);

            // 2D array to hold the ASCII art result
            int imageWidth = img.getWidth();
            int imageHeight = img.getHeight();

            // Determine sub-image size and number of sub-images
            int subImageSize = Math.min(imageWidth, imageHeight) / resolution;
            int rowsOfSubImages = imageHeight / subImageSize; // Rows of sub-images
            int colsOfSubImages = imageWidth / subImageSize;   // Columns of sub-images

            char[][] asciiArt = new char[rowsOfSubImages][colsOfSubImages];
            lastBrightnessImage = new double[rowsOfSubImages][colsOfSubImages];

            for (int i = ZERO; i < rowsOfSubImages; i++) {
                for (int j = ZERO; j < colsOfSubImages; j++) {
                    // Get the sub-image (in this case, it's a 2D array of Color[][])
                    Color[][] subImage = subImages.get(i * colsOfSubImages + j);

                    // Calculate the brightness of the sub-image
                    double brightness = ImageProcessor.calculateBrightness(
                            new Image(subImage, subImage[ZERO].length, subImage.length));
                    lastBrightnessImage[i][j] = brightness;

                    // Get the closest character based on brightness
                    char asciiChar = charMatcher.getCharByImageBrightness(brightness);

                    // Store the ASCII character in the array
                    asciiArt[i][j] = asciiChar;
                }
            }
            return asciiArt;
        }
    }
    /*
    * Reuse the previously generated ASCII art if the image and resolution are the same.
    */
    private char[][] fellAsciiArt(int rows, int cols) {
        char[][] asciiArt = new char[rows][cols];
        for (int i = ZERO; i < rows; i++) {
            for (int j = ZERO; j < cols; j++) {
                asciiArt[i][j] =charMatcher.getCharByImageBrightness(lastBrightnessImage[i][j]) ;

            }
        }
        return asciiArt;
    }
}
