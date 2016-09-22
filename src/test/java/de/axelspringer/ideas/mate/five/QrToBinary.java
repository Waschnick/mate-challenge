package de.axelspringer.ideas.mate.five;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class QrToBinary {

    public static void main(String[] args) throws Exception {
        InputStream is = QrToBinary.class.getClassLoader().getResourceAsStream("qr.png");
        BufferedImage image = ImageIO.read(is);


        String s = "";
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                s += rgb == -1 ? 0 : 1;
            }
            s += "\n";
        }

        System.out.println(s);
    }
}
