package image;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class adds functionality to the image class so that we can run
 * methods on them to represent the image using ASCII code.
 * @author Noam Simon,Hadar Wolf.
 */
public class ImageProcessor {
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int RGB_MAX = 255;
    private static final double RED_PART = 0.2126;
    private static final double GREEN_PART = 0.7152;
    private static final double BLUE_PART = 0.0722;

    /**
     * Pads an image to the nearest power-of-two dimensions by adding white pixels around the image.
     *
     * @param img The input image to be padded.
     * @return A new image with dimensions adjusted to the nearest power of two.
     */
    public static Image padImageToNearestPowerOfTwo(Image img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Get the nearest powers of 2 for width and height
        int newWidth = nearestPowerOfTwo(width);
        int newHeight = nearestPowerOfTwo(height);

        // Calculate the padding on each side
        int paddingWidth = newWidth - width;
        int paddingHeight = newHeight - height;

        // Calculate the amount of padding on each side (evenly split)
        int leftPadding = paddingWidth / TWO;
        int topPadding = paddingHeight / TWO;

        // Create the padded array with the new dimensions
        Color[][] paddedArray = new Color[newHeight][newWidth];

        fillPadedArrey(paddedArray,img, newHeight,newWidth,newWidth,topPadding,height,leftPadding,width);

        // Return a new image with the padded array
        return new Image(paddedArray, newWidth, newHeight);
    }
    /*
     * Fill the padded array with the original image and padding (white pixels).
     */
    private static void fillPadedArrey(Color[][] paddedArray,Image img,
                                       int newHeight, int newWidth, int newWidth1,
                                       int topPadding, int height, int leftPadding, int width) {
        for (int i = ZERO; i < newHeight; i++) {
            for (int j = ZERO; j < newWidth; j++) {
                if (i >= topPadding && i < topPadding + height && j >= leftPadding
                        && j < leftPadding + width) {
                    // Copy the original image pixels into the padded array
                    paddedArray[i][j] = img.getPixel(i - topPadding, j - leftPadding);
                } else {
                    // Fill the padding area with white pixels
                    paddedArray[i][j] = Color.WHITE;
                }
            }
        }
    }

    /*
     * Helper method to find the nearest power of two.
     */
    private static int nearestPowerOfTwo(int value) {
        int power = ONE;
        while (power < value) {
            power *= TWO;
        }
        return power;
    }

    /**
     * Splits an image into square sub-images of specified resolution.
     *
     * @param img The input image to be split.
     * @param subImageResolution The resolution for sub-images (number of sub-images per row/column).
     * @return A list of 2D arrays representing the sub-images.
     */
    public List<Color[][]> splitIntoSquareSubImages(Image img, int subImageResolution) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Calculate the size of each square sub-image (make sure sub-images are square)
        int subImageSize = Math.min(width, height) / subImageResolution;

        // Create a list to store the sub-images
        List<Color[][]> subImages = new ArrayList<>();

        // Loop through the rows and columns of sub-images
        int rowsOfSubImages = height / subImageSize;  // Number of rows of sub-images
        int colsOfSubImages = width / subImageSize;   // Number of columns of sub-images

        return loopAllImages(img,subImages, rowsOfSubImages,colsOfSubImages, subImageSize);}

    /*
     * Loop  through all the subimages and back a list of all them
     */
    private List<Color[][]> loopAllImages(Image img, List<Color[][]>subImages, int rowsOfSubImages,
                                          int colsOfSubImages, int subImageSize) {
        for (int i = ZERO; i < rowsOfSubImages; i++) {
            for (int j = ZERO; j < colsOfSubImages; j++) {
                // Create a 2D array to hold the pixels for the sub-image
                Color[][] subImagePixels = new Color[subImageSize][subImageSize];

                // Populate the sub-image with pixel data from the original image
                for (int x = ZERO; x < subImageSize; x++) {
                    for (int y = ZERO; y < subImageSize; y++) {
                        // Ensure we stay within bounds of the image
                        int imgX = i * subImageSize + y;
                        int imgY = j * subImageSize + x;

                        // Fill sub-image with pixels from the original image
                        subImagePixels[x][y] = img.getPixel(imgX, imgY);}
                }
                // Add the sub-image to the list
                subImages.add(subImagePixels);}
        }
        return subImages;}

    /**
     * Calculates the brightness of an image by averaging the grayscale value of all pixels.
     *
     * @param img The input image.
     * @return The brightness value between 0 (dark) and 1 (bright).
     */
    public static double calculateBrightness(Image img) {
        int width = img.getWidth();
        int height = img.getHeight();
        double totalBrightness = ZERO;
        int totalPixels = width * height;

        for (int i = ZERO; i < height; i++) {
            for (int j = ZERO; j < width; j++) {
                Color pixel = img.getPixel(i, j);
                double greyPixel = pixel.getRed() * RED_PART + pixel.getGreen() * GREEN_PART +
                        pixel.getBlue() * BLUE_PART;
                totalBrightness += greyPixel;}
        }
        return totalBrightness / (totalPixels * RGB_MAX);}
}
