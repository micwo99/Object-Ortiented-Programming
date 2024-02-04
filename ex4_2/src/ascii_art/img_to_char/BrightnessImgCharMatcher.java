package ascii_art.img_to_char;


import image.Image;
import java.awt.*;
import java.util.HashMap;


public class BrightnessImgCharMatcher {
    private  static final double NUM_OF_PIXEL = 255;
    private  static final double GREY_PIXEL_RED = 0.2126 ;
    private  static final double GREY_PIXEL_GREEN = 0.7152 ;
    private  static final double GREY_PIXEL_BLUE = 0.0722 ;
    private final HashMap<Image, Double> cache = new HashMap<>();
    private final Image image;
    private final String fontName;


    /**
     *Constructor
     */
    public BrightnessImgCharMatcher(Image image, String fontName){
        this.image=image;
        this.fontName=fontName;
    }

    /**
     *the function returns 2d array of Ascii after conversion, with the brightness level that we change as asked in
     * the exercise
     * @param numCharsInRow number of character in a row
     * @param charSet a set of characters
     * @return 2d array of Ascii
     */
    public char[][] chooseChars(int numCharsInRow, Character[] charSet){
       double []brightness = changeBrightness(getBrightness(charSet));
        return convertImageToAscii(numCharsInRow,image,charSet,brightness);
    }
    /**
     * the functions find the min and the max of a list of double
     * @param list brightness level
     * @return
     */
    private double[] findMinMax(double[]list){
        double min=list[0];
        double max=list[0];
        for(double compare: list){
            if (compare>=max){
                max=compare;
            }
            if (compare<=min){
                min=compare;
            }
        }
        return new double[]{min,max};

    }


    /**
     *the function returns the num of true in the 2D array
     * @param image  a boolean 2D array
     * @return the average of true in an image
     */
    private double CountTrueInImage(boolean[][] image){
        double counter=0;
        for (boolean[] booleans : image) {
            for (Boolean aBoolean : booleans) {
                if (aBoolean) {
                    counter++;
                }
            }
        }
        double numPixel= image.length * image[0].length;
        return counter/numPixel;
    }

    /**
     * the function returns an array that is the brightness level of the charset
     * @param charSet set of characters
     * @return  array of double
     */
    private double[] getBrightness(Character[] charSet){
        double [] brightness = new double[charSet.length];
        for (int i = 0; i <charSet.length ; i++) {
            boolean[][] image = CharRenderer.getImg(charSet[i], 16, this.fontName);
            brightness[i]= CountTrueInImage(image);
        }
        return brightness;
    }

    /**
     * the function return an array of double that correspond to the brightness level after
     * we change it depending on the given formula in the exercise
     * @param brightness brightness level of the charset before we change it
     * @return brightness level of the charset after we change it
     */
    private double[] changeBrightness(double[] brightness){
        double [] minMax= findMinMax(brightness);
        double min= minMax[0];
        double max =minMax[1];
        for (int i = 0; i < brightness.length; i++) {
            brightness[i]=(brightness[i]-min)/(max-min);
        }
        return brightness;
    }
    /**
     *the function calculates the brightness average of an image
     * @param image an object Image
     * @return the brightness average of image
     */
    private double getAverage(Image image) {
        double sum=0;
        double numOfPixel=0;
        for (Color pixel : image.pixels()) {
            numOfPixel++;
            double greyPixel =pixel.getRed() * GREY_PIXEL_RED
                    + pixel.getGreen() * GREY_PIXEL_GREEN
                    + pixel.getBlue() * GREY_PIXEL_BLUE;
            sum+=greyPixel;
        }
        return (sum/numOfPixel)/NUM_OF_PIXEL;
    }

    /**
     * the function returns 2d array of Ascii after conversion. It divides the image into the required number
     * of sub-images, calculates the luminosity level for each sub-image and adjusts the ASCII character
     * with the nearest luminosity level. It keeps this character in the appropriate position in the board.
     * @param numCharsInRow number of character in a row
     * @param image an object Image
     * @param charSet a set of characters
     * @param brightness brightness level of the charset
     * @return 2d array of Ascii
     */
    private char[][] convertImageToAscii(int numCharsInRow, Image image, Character[] charSet, double[] brightness) {
        int pixels = image.getWidth() / numCharsInRow;
        int row = image.getHeight() / pixels;
        int col = image.getWidth() / pixels;

        char[][] asciiArt = new char[row][col];
        int x = 0;
        int y = 0;


        for (Image subImage : image.squareSubImagesOfSize(pixels)) {
            double subImgBright;
            if (x == col) {
                x = 0;
                y++;
            }
            if(cache.containsKey(subImage)){
                subImgBright = cache.get(subImage);
            }
            else{
                subImgBright=getAverage(subImage);
                cache.put(subImage,subImgBright);
            }
            double toReplace = Math.abs(subImgBright - brightness[0]);
            int bestIndex = 0;
            for (int i = 1; i < brightness.length; i++) {
                double newValue = Math.abs(brightness[i] - subImgBright);
                if (newValue <= toReplace) {
                    toReplace = newValue;
                    bestIndex = i;
                }
            }
            asciiArt[y][x] = charSet[bestIndex];
            x++;

        }
        return asciiArt;
    }


}

