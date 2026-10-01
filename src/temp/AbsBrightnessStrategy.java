package image_char_matching;

import java.util.TreeMap;

public class AbsBrightnessStrategy implements BrightnessStrategy {
    @Override
    public char getClosestChar(TreeMap<Double, Character> brightnessMap, Double lowerKey, Double higherKey, double targetBrightness) {
        if (targetBrightness - lowerKey > higherKey - targetBrightness) {
            return brightnessMap.get(higherKey);
        } else {
            return brightnessMap.get(lowerKey);
        }
    }
}
