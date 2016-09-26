package de.axelspringer.ideas.mate.five.challenges;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.net.URI;

@Service
public class QrGenerator {

//    public static void main(String[] args) throws Exception {
//        new QrGenerator().generateQRCode_general("http://localhost/asdf/1234/qwerty");
//    }

    private final static String BASE_URL = "https://mate-challenge.herokuapp.com/five/cheesecake/";

    @Autowired
    private SymetricEncryption symetricEncryption;

    @SneakyThrows
    public String generateQrText(String email) {
        BufferedImage image = generateQRCode(BASE_URL + symetricEncryption.encrypt(email));

        String s = "<pre>";
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                s += rgb == -1 ? 0 : 1;
            }
            s += "\n";
        }

        s += "</pre>";
        return s;
    }

    @SneakyThrows
    private BufferedImage generateQRCode(String data) throws WriterException {
        com.google.zxing.Writer qrCodeWriter = new QRCodeWriter();
        String finaldata = new URI(data).toASCIIString();

        int dimension = 40;
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
