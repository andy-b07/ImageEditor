import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;
import javax.swing.*;

public class ImageEditorPanel extends JPanel {

    Color[][] pixels;
    
    public ImageEditorPanel() {
        BufferedImage imageIn = null;
        try {
            // the image should be in the main project folder, not in \src or \bin
            imageIn = ImageIO.read(new File("contact-image-800x600.jpg"));
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        pixels = makeColorArray(imageIn);
        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        // paints the array pixels onto the screen
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1);
            }
        }
    }

    public void run() {
        // call your image-processing methods here OR call them from keyboard event
        // handling methods
        // write image-processing methods as pure functions - for example: pixels =
        //pixels = flipHorizontal(pixels);
        pixels = flipVerticalColors(pixels);
        //pixels = brighten(pixels);
        //pixels = singlePixelAlgoGrayScale(pixels);
        repaint();
    }
    public Color[][] multiPixleAlgo(Color[][] inputArr){
    final int RADIUS = 1;
    int height = inputArr.length;
    int width = inputArr[0].length;
    Color[][] outputArr = new Color[height][width];
    for (int row = 0; row < height ; row++) {
        for (int col = 0; col < width ; col++){

            //intililize variables
            //visti each the neighbores
            for (int row2 = row - RADIUS; row2 <= row + RADIUS; row2++) {
                for (int col2 = col - RADIUS; col2 <= + RADIUS; col2++){
                    if ( row2 >= 0 && .......) {
                        //do some work with this neighbor pixel
                    }

                }
            }
            //figure out what to do with allat neighbor data
            //outputArr[row][col]
            
            Color c = inputArr[row][col];
        }
    }
}
    public Color[][] singlePixelAlgoGrayScale(Color[][] inputArr){
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];
        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width ; col++){
                Color c = inputArr[row][col];
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();
                int avg = (red + blue + green) / 3;
                Color newC = new Color (avg, avg, avg);
                outputArr[row][col] = newC;
            }
        }
        return outputArr;
    }

    public Color[][] brighten(Color[][] inputArr) {
        Color[][] outputArr = new Color[inputArr.length][inputArr[0].length];
        for (int row = 0; row < inputArr.length; row++) {
            for ( int col = 0; col < inputArr[0].length; col++) {
                Color c = inputArr[row][col];
                outputArr[row][col] = c.brighter();
            }
        }
        return outputArr;
    }
    public Color[][] flipHorizontal (Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];
        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width ; col++) {
                outputArr[row][col] = inputArr[row][width - col - 1];
            }      
        }
        return outputArr;
    }
    public Color[][] flipVerticalColors (Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];
        for (int row = 0; row < height ; row++) {
            for (int col = 0; col < width ; col++) {
                outputArr[row][col] = inputArr[height - row - 1][col];
            }      
        }
        return outputArr;
    }

    public Color[][] makeColorArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[height][width];
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = new Color(image.getRGB(col, row), true);
                result[row][col] = c;
            }
        }
        // System.out.println("Loaded image: width: " +width + " height: " + height);
        return result;
    }
}
