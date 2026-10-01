package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image_char_matching.*;
import image.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * This department is responsible for running the program by the user and controlling
 * the parameters that will make up the image as well as receiving the image,
 * The program will be executed.
 * @author Noam Simon,Hadar Wallf.
 */
public class Shell {
    private static final String EXIT_COMMAND = "exit";
    private static final String CHARS = "chars";
    private static final String CHAR_NUMS = "0123456789" ;
    private static final String ADD = "add";
    private static final String ALL = "all";
    private static final String SPACE = "space";
    private static final String REMOVE = "remove";
    private static final String RES = "res";
    private static final String ROUND = "round";
    private static final String OUTPUT = "output";
    private static final String RUN = "asciiArt";
    private static final String HTML = "html";
    private static final String CONSOLE = "console";
    private static final String EMPTY = "";
    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String ABS = "abs";
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int LOWEST_CHAR = 32;
    private static final int HIGHEST_CHAR = 126;
    private static final int DIFULT_RESOLUTION = 2;
    private static final int FACTOR_GROWTH_RESOLUTION = 2;
    private static final String WATE_FOR_INPUT = ">>> ";
    private static final String INCORRECT_COMMAND = " due to incorrect command.";
    private static final String INCORRECT_FORMAT_MESSEG_OPEN = "Did not change ";
    private static final String INCORRECT_FORMAT_MESSEG_END = " method due to incorrect format.";
    private static final String EXECUTE_EROR_MESSEG = "Did not execute";
    private static final String CHAR_SET_IS_SMALL = ". Charset is too small.";
    private static final Character CHAR_SPACE = ' ';
    private static final String HTML_PATH = "out.html";
    private static final String FONT_OF_OUTPUT = "Courier New";
    private static final String STRING_SPACE = " ";
    private static final int THREE = 3;
    private static final String ROUNDING = "rounding";
    private static final String RESOLOTION_STYTMENT = "Resolution set to ";
    private static final String RESOLOTION = "resolution";
    private static final String LINE = "-";
    private static final String ELIGAL_RESOLOTION_STATMENT = "Did not change resolution due" +
            " to exceeding boundaries.";
    // Fields for managing the ASCII art generation
    private Set<Character> charSet; // Set of characters to use for the ASCII art
    private SubImgCharMatcher matcher; // Matcher to map image brightness to characters
    private int resolution; // Current resolution
    private Image imageWork; // The input image
    private String roundMethod; // Current rounding method
    private String output; // Current output format
    private AsciiArtAlgorithm algorithm; // Algorithm to generate ASCII art
    private AsciiOutput asciiOutput; // Output handler for ASCII art
    //private charMatcherFactory charMatcherFactory;


    /**
     * Constructor initializes the Shell object with default settings.
     */
    public Shell() {

        //charMatcherFactory = new charMatcherFactory();
        //matcher = charMatcherFactory.createMatcher(roundMethod,CHAR_NUMS.toCharArray());
        matcher = new SubImgCharMatcher(CHAR_NUMS.toCharArray());
        charSet = new TreeSet<>();
        for (char c : CHAR_NUMS.toCharArray()) {
            charSet.add(c);
        }
        resolution = DIFULT_RESOLUTION;
        roundMethod = ABS;
        output = CONSOLE;


    }


    /**
     * Runs the main shell loop to process commands from the user.
     * @param imagename The name of the image file to process.
     * @throws Exception If an error occurs during processing.
     */
    public void run(String imagename) throws Exception {
        initializeImage(imagename);

        while (true) {
            String input = promptUserInput();
            String firstWord = extractFirstWord(input);

            try {
                processCommand(firstWord, input);
            } catch (IncorrectFormat e) {
                handleError(INCORRECT_FORMAT_MESSEG_OPEN + e.getMessage() +
                        INCORRECT_FORMAT_MESSEG_END);
            } catch (ExecuteEror e) {
                handleError(EXECUTE_EROR_MESSEG + e.getMessage());
            } catch (EligalResolution e) {
                handleError(ELIGAL_RESOLOTION_STATMENT);
            }
        }
    }

    /**
     * Initializes the image object with the given name.
     */
    private void initializeImage(String imagename) {
        try {
            imageWork = new Image(imagename);
        } catch (Exception IOException) {
            // Handle image initialization error if necessary
        }
    }

    /**
     * Prompts the user for input and returns the entered string.
     */
    private String promptUserInput() {
        System.out.print(WATE_FOR_INPUT); // Prompt for input
        return KeyboardInput.readLine();
    }

    /**
     * Extracts the first word from the given input string.
     */
    private String extractFirstWord(String input) {
        return (input.indexOf(CHAR_SPACE) > ZERO) ? input.substring(ZERO, input.indexOf(CHAR_SPACE)):input;
    }

    /**
     * Processes the command based on the first word of the input.
     */
    private void processCommand(String command, String input) throws Exception {
        try {
            switch (command) {
                case CHARS -> printChars();
                case ADD -> handleAddCommand(input, true);
                case EXIT_COMMAND -> System.exit(ZERO);
                case REMOVE -> handleAddCommand(input, false);
                case RES -> changeResolotion(input);
                case ROUND -> changeRoundMath(input);
                case OUTPUT -> setMethodToOutput(input);
                case RUN -> runAsciiArtAlgorithm();
                default -> throw new ExecuteEror(INCORRECT_COMMAND);
            }
        } catch (IncorrectFormat e) {
            handleError(INCORRECT_FORMAT_MESSEG_OPEN
                    + e.getMessage() + INCORRECT_FORMAT_MESSEG_END);
        } catch (ExecuteEror e) {
            handleError(EXECUTE_EROR_MESSEG + e.getMessage());
        } catch (EligalResolution e) {
            handleError(ELIGAL_RESOLOTION_STATMENT);
        }
    }


    /**
     * Handles error messages by printing them to the console.
     */
    private void handleError(String errorMessage) {
        System.out.println(errorMessage);
    }


    //    public void run(String imagename) throws Exception {
//        try {
//            imageWork = new Image(imagename);
//        } catch (Exception IOException) {
//            return;
//
//        }
//        while (true) {
//            System.out.print(WATE_FOR_INPUT); // Prompt for input
//            String input = KeyboardInput.readLine(); // Read input from terminal
//            String firstWord = input;
//            if (input.indexOf(CHAR_SPACE) > ZERO) {
//                firstWord = input.substring(ZERO, input.indexOf(CHAR_SPACE));
//            }
//            // Check the command and perform corresponding actions
//            try {
//
//                switch (firstWord) {
//
//                    case CHARS:
//                        printChars();
//                        break;
//                    case ADD:
//                        handleAddCommand(input, true);
//                        break;
//                    case EXIT_COMMAND:
//                        return; // Exit the program
//                    case REMOVE:
//                        handleAddCommand(input, false);
//                        break;
//                    case RES:
//                        changeResolotion(input);
//                        break;
//                    case ROUND:
//                        changeRoundMath(input);
//                        break;
//                    case OUTPUT:
//                        setMethodToOutput(input);
//                        break;
//                    case RUN:
//                        runAsciiArtAlgorithm();
//                        break;
//                    default:
//                        throw new ExecuteEror(INCORRECT_COMMAND);
//                }
//            }
//            catch (IncorrectFormat e) {
//                System.out.println
//                        (INCORRECT_FORMAT_MESSEG_OPEN + e.getMessage() + INCORRECT_FORMAT_MESSEG_END);
//            }
//            catch (ExecuteEror e){
//                System.out.println(EXECUTE_EROR_MESSEG + e.getMessage());
//            }
//            catch (EligalResolution e){
//                System.out.println(ELIGAL_RESOLOTION_STATMENT);
//            }
//
//        }
//        }
    /*
     * Runs the ASCII Art algorithm and outputs the result.
     * Throws an exception if the character set is too small.
     */
        private void runAsciiArtAlgorithm () throws Exception {
            if (charSet.size() < TWO) {
                throw new ExecuteEror(CHAR_SET_IS_SMALL);
            }
            algorithm = new AsciiArtAlgorithm(imageWork, resolution, matcher, roundMethod,
                    false);
            switch (output) {
                case HTML:
                    asciiOutput = new HtmlAsciiOutput(HTML_PATH, FONT_OF_OUTPUT);
                    asciiOutput.out(algorithm.run());
                    break;
                case CONSOLE:
                    asciiOutput = new ConsoleAsciiOutput();
                    char[][] s = algorithm.run();
                    asciiOutput.out(s);
                    break;
            }
        }

    /*
     * Sets the output method (HTML or CONSOLE) based on user input.
     * Throws an exception for invalid input formats.
     */
        private void setMethodToOutput (String input) throws Exception {
            String[] parts = input.split(STRING_SPACE, THREE);//??האם זה ששמתי פה 3 פותר את זה
            if(parts.length==ONE){
                throw new IncorrectFormat(OUTPUT);
            }
            String addArgument = parts[ONE].trim();//.trim nedde?
            switch (addArgument) {
                case HTML:
                    output = HTML;
                    break;
                case CONSOLE:
                    output = CONSOLE;
                    break;
                default:
                    throw new IncorrectFormat(OUTPUT);

            }
        }
    /*
     * Changes the rounding method for the ASCII Art algorithm.
     */
        private void changeRoundMath (String input) throws Exception {
            String[] parts = input.split(STRING_SPACE, THREE);
            String addArgument = parts[ONE].trim();//.trim nedde?
            switch (addArgument) {
                case UP:
                    matcher.setRoundMethod(UP);
                    roundMethod = UP;
                    break;
                case DOWN:
                    matcher.setRoundMethod(DOWN);
                    roundMethod = DOWN;
                    break;
                case ABS:
                    matcher.setRoundMethod(ABS);
                    roundMethod = ABS;
                    break;
                default:
                    throw new IncorrectFormat(ROUNDING);
            }
            //charMatcherFactory.createMatcher(roundMethod,charSet);
        }
        /*
        change the resolotion of the output image increce or dicrese by factor of 2.
         */
        private void changeResolotion (String input) throws Exception {
            String[] parts = input.split(STRING_SPACE, THREE);
            if(parts.length==ONE){
                System.out.println(RESOLOTION_STYTMENT + resolution);
            }
            else {
                String addArgument = parts[ONE].trim();//.trim nedde?}

                switch (addArgument) {
                    case UP:
                        if (resolution * FACTOR_GROWTH_RESOLUTION <= imageWork.getWidth()) {
                            resolution *= FACTOR_GROWTH_RESOLUTION;
                            System.out.println(RESOLOTION_STYTMENT + resolution);
                        } else {
                            throw new EligalResolution();
                        }
                        break;
                    case DOWN:
                        if (resolution / FACTOR_GROWTH_RESOLUTION >=
                                Math.max(ONE, imageWork.getWidth() / imageWork.getHeight())) {
                            resolution /= FACTOR_GROWTH_RESOLUTION;
                            System.out.println(RESOLOTION_STYTMENT + resolution);
                        } else {
                            throw new EligalResolution();
                        }
                        break;
                    default:
                        throw new Exception(RESOLOTION);

                }
            }

        }

        /*
        adds or removes a reqwet chars.
         */
        private void handleAddCommand (String input,boolean add) throws Exception {
            String[] parts = input.split(STRING_SPACE, THREE);
            String addArgument = parts[ONE].trim();
            switch (addArgument) {
                case ALL -> {
                    addAllCharacters(add);
                    break;
                }
                case SPACE -> {
                    choseAddOrRemove(add,CHAR_SPACE);
                    break;
                }
                default -> {
                    if (addArgument.length() == ONE) {

                        if (addArgument.charAt(ZERO) >
                                LOWEST_CHAR && addArgument.charAt(ZERO) < HIGHEST_CHAR) {

                                choseAddOrRemove(add,addArgument.charAt(ZERO));

                        } else {
                            if (add) {
                                throw new IncorrectFormat(ADD);

                            } else {
                                throw new IncorrectFormat(REMOVE);
                            }
                        }
                    } else if (addArgument.contains(LINE)) {
                        handleRangeCommand(addArgument, add);
                    } else {
                        if (add) {
                            throw new IncorrectFormat(ADD);
                        } else {
                            throw new IncorrectFormat(REMOVE);
                        }
                    }
                }
            }
        }
    /*
    * add or remove a spesific char.
     */
    private void choseAddOrRemove(boolean add, Character charToAddOrRemove) throws Exception {
        if (add) {
            charSet.add(charToAddOrRemove);
            matcher.addChar(charToAddOrRemove);
        } else {
            charSet.remove(charToAddOrRemove);
            matcher.removeChar(charToAddOrRemove);

        }
    }

    /*
    adds or remove all chars in the range(32-126).
     */
        private void addAllCharacters ( boolean add) throws Exception {
            for (char c = LOWEST_CHAR; c <= HIGHEST_CHAR; c++) {
                choseAddOrRemove(add,c);
            }
        }
    /**
     * Handles the addition or removal of a range of characters to/from the character set.
     * Splits the range and delegates the looping logic to the `loopAction` method.
     *
     * @param range The range of characters in the format "start-end".
     * @param add   Indicates whether to add (true) or remove (false) the characters.
     */
    private void handleRangeCommand(String range, boolean add) throws Exception {
        String[] rangeParts = range.split(LINE);
        if (rangeParts.length == TWO) {
            char start = rangeParts[ZERO].charAt(ZERO);
            char end = rangeParts[ONE].charAt(ZERO);

            // Ensure start is always less than or equal to end
            if (start > end) {
                char temp = start;
                start = end;
                end = temp;
            }
            loopAction(start, end, add);
        }
    }

    /**
     * Iterates through a range of characters and applies the specified action
     * (add or remove). Assumes the range is always ascending.
     *
     * @param start The starting character of the range (inclusive).
     * @param end   The ending character of the range (inclusive).
     * @param add   Indicates whether to add (true) or remove (false) the characters.
     */
    private void loopAction(char start, char end, boolean add) throws Exception {
        for (char c = start; c <= end; c++) {
            choseAddOrRemove(add, c);
        }
    }

//    /**
//     * Performs the action of adding or removing a single character from the character set
//     * and updates the matcher accordingly.
//     *
//     * @param c   The character to process.
//     * @param add Indicates whether to add (true) or remove (false) the character.
//     */
//    private void applyAction(char c, boolean add) {
//        if (add) {
//            charSet.add(c);
//            matcher.addChar(c);
//        } else {
//            charSet.remove(c);
//            matcher.removeChar(c);
//        }
//    }

    //        /*
//        adds or remove a range of chars in the type(a-j).
//         */
//        private void handleRangeCommand (String range,boolean add){
//            String[] rangeParts = range.split(LINE);
//            if (rangeParts.length == TWO) {
//                char start = rangeParts[ZERO].charAt(ZERO);
//                char end = rangeParts[ONE].charAt(ZERO);
//                if (start <= end) {
//                    loopAction(start,end,add);
//                    for (char c = start; c <= end; c++) {
//                        if (add) {
//                            charSet.add(c);
//                            matcher.addChar(c);
//                        } else {
//                            charSet.remove(c);
//                            matcher.removeChar(c);
//                        }
//                    }
//                } else {
//                    for (char c = start; c >= end; c--) {
//                        if (add) {
//                            charSet.add(c);
//                            matcher.addChar(c);
//                        } else {
//                            charSet.remove(c);
//                            matcher.removeChar(c);
//                        }
//                    }
//                }
//            }
//        }
        /*
        prints the chars that can be used on the image.
         */
        private void printChars () {
            for (char c : charSet) {
                System.out.print(c + STRING_SPACE);
            }
            System.out.println();
        }

    /**
     * the main of the pogram.
     * @param args void.
     * @throws Exception that will be trown in the pogram.
     */
    public static void main (String[]args) throws Exception {
            Shell shell = new Shell();
            try {
                shell.run("board.jpeg");

            } catch (Exception IOException) {
                return;
            }
        }
}
