package de.axelspringer.ideas.mate.five.challenges;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.scene.paint.Color;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;

public class QrGenerator {
//    public static void main(String[] args) throws Exception {
//        new QrGenerator().generateQRCode_general("http://localhost/asdf/1234/qwerty");
//    }

    @SneakyThrows
    private void generateQRCode_general(String data) throws WriterException {
        com.google.zxing.Writer qrCodeWriter = new QRCodeWriter();
        String finaldata = new URI(data).toASCIIString();

        int dimension = 40;
        BitMatrix bm = qrCodeWriter.encode(finaldata, BarcodeFormat.QR_CODE, dimension, dimension);
        BufferedImage ImageBitmap = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);

        for (int i = 0; i < dimension; i++) {//width
            for (int j = 0; j < dimension; j++) {//height
                int color = bm.get(i, j) ? -1 : Color.BLACK.hashCode();
                ImageBitmap.setRGB(i, j, color);
            }
        }
        ImageIO.write(ImageBitmap, "png", new File("/Users/swaschni/Projekte/mate-challenge/" + System.currentTimeMillis() + ".png"));
//        if (ImageBitmap != null) {
//            qrcode.setImageBitmap(ImageBitmap);
//        } else {
//            Toast.makeText(getApplicationContext(), getResources().getString(R.string.userInputError),
//                    Toast.LENGTH_SHORT).show();
//        }
    }
}
