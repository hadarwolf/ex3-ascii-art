package image_char_matching;

import java.util.TreeMap;

public interface BrightnessStrategy {
    char getClosestChar(TreeMap<Double, Character> brightnessMap, Double lowerKey,
                        Double higherKey, double targetBrightness);
}

