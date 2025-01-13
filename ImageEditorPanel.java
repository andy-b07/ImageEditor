import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class ImageEditorPanel extends JPanel implements KeyListener {
    private Color[][] pixels; 
    private BufferedImage image; 
    private boolean quit = false; 

    public ImageEditorPanel() {
        try {
            image = ImageIO.read(new File("354655-bigthumbnail.jpg")); 
            pixels = makeColorArray(image);
        } catch (IOException e) {
            System.out.println("Error loading image: " + e.getMessage());
            System.exit(1);
        }

        setPreferredSize(new Dimension(pixels[0].length, pixels.length));
        setBackground(Color.BLACK);
        addKeyListener(this); 
      
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                g.setColor(pixels[row][col]);
                g.fillRect(col, row, 1, 1); // Draw each pixel
            }
        }
    }


    public void run() {
        while (!quit) {
            repaint(); 
        }
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_H -> pixels = flipHorizontal(pixels); // horizontal flip
            case KeyEvent.VK_V -> pixels = flipVerticalColors(pixels); // vertical flip
            case KeyEvent.VK_G -> pixels = singlePixelAlgoGrayScale(pixels); // grayscale
            case KeyEvent.VK_B -> pixels = brighten(pixels); // brighten image
            case KeyEvent.VK_P -> pixels = posterize(pixels); // posterize image
            case KeyEvent.VK_S -> pixels = sepiaFilter(pixels); // sepia filter
        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

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
        return result;
    }

    public Color[][] flipHorizontal(Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                outputArr[row][col] = inputArr[row][width - col - 1];
            }
        }
        return outputArr;
    }

    public Color[][] flipVerticalColors(Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                outputArr[row][col] = inputArr[height - row - 1][col];
            }
        }
        return outputArr;
    }

    public Color[][] singlePixelAlgoGrayScale(Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = inputArr[row][col];
                int avg = (c.getRed() + c.getGreen() + c.getBlue()) / 3; // Average of RGB
                outputArr[row][col] = new Color(avg, avg, avg);
            }
        }
        return outputArr;
    }

    public Color[][] brighten(Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = inputArr[row][col];
                outputArr[row][col] = c.brighter(); 
            }
        }
        return outputArr;
    }

    public Color[][] sepiaFilter(Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = inputArr[row][col];
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                int newRed = Math.min(255, (int) (0.393 * red + 0.769 * green + 0.189 * blue));
                int newGreen = Math.min(255, (int) (0.349 * red + 0.686 * green + 0.168 * blue));
                int newBlue = Math.min(255, (int) (0.272 * red + 0.534 * green + 0.131 * blue));

                outputArr[row][col] = new Color(newRed, newGreen, newBlue);
            }
        }
        return outputArr;
    }

    public Color[][] posterize(Color[][] inputArr) {
        int height = inputArr.length;
        int width = inputArr[0].length;
        Color[][] outputArr = new Color[height][width];

        Color colorCream = new Color(227, 221, 204);
        Color colorRed = new Color(232, 80, 72);
        Color colorBlue = new Color(90, 102, 232);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Color c = inputArr[row][col];
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                if (red >= green && red >= blue) {
                    outputArr[row][col] = colorCream;
                } else if (green >= red && green >= blue) {
                    outputArr[row][col] = colorRed;
                } else {
                    outputArr[row][col] = colorBlue;
                }
            }
        }
        return outputArr;
    }
}