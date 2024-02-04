package ascii_art;

import ascii_art.img_to_char.BrightnessImgCharMatcher;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;

import java.util.*;
import java.util.stream.Stream;

public class Shell {
    private static final String CMD_EXIT = "exit";
    private static final int MIN_PIXELS_PER_CHAR = 2;
    private Set<Character> charSet = new HashSet<>();
    private  static final String SHOW_CHARS = "chars";
    private  static final String RES = "res";
    private  static final String ADD = "add";
    private  static final String REMOVE = "remove";
    private  static final String RENDER = "render";
    private  static final String CONSOLE = "console";

    private  static final String UP = "up";
    private  static final String DOWN = "down";
    private  static final String ALL = "all";
    private static final String SPACE = "space";

    private static final char[] CHARS_ALL = new char[]{' ','~'};
    private static final char[] CHARS_SPACE = new char[]{' ',' '};
    private static final int INITIAL_CHARS_IN_ROW = 64;
    private static final String FONT_NAME = "Courier New";
    private static final String ERROR_MESSAGE = "Did not executed due to incorrect command";
    private static final String ERROR_ADD="Did not add due to incorrect format";
    private static final String ERROR_REMOVE="Did not remove due to incorrect format";
    private static final String ERROR_RES = "Did not change due to exceeding boundaries";

    private static final String COMMAND_MESSAGE=">>> ";

    private static final String OUTPUT_FILENAME = "out.html";
    private static final String INITIAL_CHARS_RANGE = "0-9";
    private static final String EMPTY_SET="Your set of chars is empty";
    private static final String WIDTH_SET="Width set to %d";
    private final BrightnessImgCharMatcher charMatcher;
    private AsciiOutput output;
    private final int maxCharsInRow;
    private int charsInRow;

    /**
     * CONSTRUCTOR
     */
    public Shell(Image image){
        charMatcher= new BrightnessImgCharMatcher(image,FONT_NAME);
        output = new HtmlAsciiOutput(OUTPUT_FILENAME, FONT_NAME);
        int minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        maxCharsInRow = image.getWidth() / MIN_PIXELS_PER_CHAR;
        charsInRow = Math.max(Math.min(INITIAL_CHARS_IN_ROW, maxCharsInRow), minCharsInRow);
        addChars(INITIAL_CHARS_RANGE);
    }

    /**
     * This function is the function that the main call to run the code, it allowed to change the resolution of the
     * Ascii image, add characters and remove it, if we want to print the ascii image in the console or creat a
     * html file
     * if the user enter exit the function stops
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(COMMAND_MESSAGE);
        String action= null;
        String cmd = scanner.nextLine().trim();
        String[] commands =cmd.split(" ");
        cmd = commands[0];
        if (commands.length>1){
             action = commands[1];
        }


        while(!(cmd.toLowerCase().equals(CMD_EXIT)&& action==null)) {
            if (cmd.equals(SHOW_CHARS)){
                showChars();
            }
            else if (cmd.equals(RENDER)){
                render();
            }
            else if (cmd.equals(RES)&& action!=null){
                resChange(action);
            }
            else if(cmd.equals(ADD)&& action!=null){
                addChars(action);
            }
            else if(cmd.equals(REMOVE)&& action!=null){
                removeChars(action);
            }
            else if(cmd.startsWith(CONSOLE)){
                console();
            }
            else{
                System.out.println(ERROR_MESSAGE);
            }
            System.out.print(COMMAND_MESSAGE);
             cmd = scanner.nextLine().trim();
             commands =cmd.split(" ");
            cmd = commands[0];
            if (commands.length>1){
                action = commands[1];
            }
            else{
                action=null;
            }


        }
    }

    /**
     * this function print the characters that are in our charset
     */
    private void showChars(){
        charSet.stream().sorted().forEach(c-> System.out.print(c + " "));
        System.out.println();
    }

    /**
     * the function return a list of char depending on what the user enter,it will be allowed to remove or add
     * characters from the charset a list of characters, if parameters isn't valid the function print an error message
     * @param param what we want to add or remove
     * @return list of char
     */
    private static char[] parseCharRange(String param){
        if (param.length()==1){
            char res= param.charAt(0);
            return new char[]{res, res};
        }
        if(param.contains("-")){
           String[] results= param.split("-");
           char res0= results[0].charAt(0);
            char res1= results[1].charAt(0);
            if (res0>res1){
                return new char[]{res1,res0};
            }
           return new char[]{res0,res1};
        }
        if (param.equals(ALL)){
            return CHARS_ALL;
        }
        if (param.equals(SPACE)){
            return CHARS_SPACE;
        }
        System.out.println(ERROR_MESSAGE);
        return null;

    }

    /**
     * this function add char(s) to the char list e
     * @param s a string that the user entered
     */
    private void addChars(String s) {
        char[] range = parseCharRange(s);
        if(range != null){
            Stream.iterate(range[0], c -> c <= range[1], c -> (char)((int)c+1)).forEach(charSet::add);
        }
        else {
            System.out.println(ERROR_ADD);
        }
    }

    /**
     * this function remove char(s) to the char list e
     * @param s a string that the user entered
     */
    private void removeChars(String s){
        char[] range = parseCharRange(s);
        if(range != null){
            Stream.iterate(range[0], c -> c <= range[1], c -> (char)((int)c+1)).forEach(charSet::remove);
        }
        else{
            System.out.println(ERROR_REMOVE);
        }

    }

    /**
     * the function increases the resolution of the ascii image or decrease the resolution
     * @param s a string that the user entered
     */
    private void resChange(String s) {
        if (s.equals(UP)) {
            if (charsInRow <= maxCharsInRow / 2) {
                charsInRow *= 2;
                System.out.println(String.format(WIDTH_SET,charsInRow));
            }
            else{
                System.out.println(ERROR_RES);
            }
        }
        else if(s.equals(DOWN)){
            if(charsInRow>=MIN_PIXELS_PER_CHAR){
                charsInRow/=2;
                System.out.println(String.format(WIDTH_SET,charsInRow));
            }
            else{
                System.out.println(ERROR_RES);
            }
        }
        else{
            System.out.println(ERROR_MESSAGE);
        }
    }

    /**
     * the function will convert the image to Ascii image with the parameters we decide to give
     * and we write it in a html file or will print it in the console
     */
    private void render(){
        if(charSet.size()==0){
            System.out.println(EMPTY_SET);
        }
        else{
        Character[] charset= charSet.toArray(new Character[0]);
        char[][] chars = charMatcher.chooseChars(charsInRow,charset);
        output.output(chars);}
    }

    /**
     * the function change the output field, when we run will call render after the user called console,
     * it will print the Ascii image in the console
     */
    private void console(){
        this.output=new ConsoleAsciiOutput();
    }
}
