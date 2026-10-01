package image_char_matching;

public class charMatcherFactory {
    //private static final String ABS = "abs";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final BrightnessStrategy absStrategy = new AbsBrightnessStrategy();
    private static final BrightnessStrategy upStrategy = new UpBrightnessStrategy();
    private static final BrightnessStrategy downStrategy = new DownBrightnessStrategy();


//    public SubImgCharMatcher createMatcher(String roundMethod,char[] chars){
//        switch (roundMethod) {
//            case UP:
//                return new SubImgCharMatcher(chars,upStrategy);
//            case DOWN:
//                return new SubImgCharMatcher(chars,downStrategy);
//            default:
//                return new SubImgCharMatcher(chars,absStrategy);
//        }
//    }
}
