package de.axelspringer.ideas.mate.five;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URLEncoder;

public class QrGenerator {

    //    public static void main(String[] args) throws Exception {
//        new QrGenerator().generateQRCode_general("http://localhost/asdf/1234/qwerty");
//    }
    private static final Logger log = LoggerFactory.getLogger(QrGenerator.class);

    private final static String BASE_URL = "https://mate-challenge.herokuapp.com/five/cheesecake/";

    private SymetricEncryption symetricEncryption = new SymetricEncryption();

    public String generateQrText(String email) {
        try {


            log.info("Create image for email: " + email);
            BufferedImage image = generateQRCode(BASE_URL + URLEncoder.encode(symetricEncryption.encrypt(email), "UTF-8"));

            String s = "";
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    s += rgb == -1 ? 0 : 1;
                }
                s += "\n";
            }

            log.info("Image and code created for email: " + email);
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private BufferedImage generateQRCode(String data) throws Exception {
        com.google.zxing.Writer qrCodeWriter = new QRCodeWriter();
        String finaldata = new URI(data).toASCIIString();

        int dimension = 60;
        BitMatrix bm = qrCodeWriter.encode(finaldata, BarcodeFormat.QR_CODE, dimension, dimension);
        BufferedImage bufferedImage = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < dimension; i++) {//width
            for (int j = 0; j < dimension; j++) {//height
                int color = bm.get(i, j) ? -1 : java.awt.Color.BLACK.hashCode();
                bufferedImage.setRGB(i, j, color);
            }
        }
        //ImageIO.write(bufferedImage, "png", new File("/Users/swaschni/Projekte/mate-challenge/" + System.currentTimeMillis() + ".png"));
        return bufferedImage;
    }
}
