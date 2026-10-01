package image_char_matching;

import java.util.TreeMap;

public class UpBrightnessStrategy implements BrightnessStrategy {
    @Override
    public char getClosestChar(TreeMap<Double, Character> brightnessMap, Double lowerKey, Double higherKey, double targetBrightness) {
        return brightnessMap.get(higherKey);
    }
}
