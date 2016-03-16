package de.axelspringer.ideas.mate;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class HexPrinter {

    public static void printBinFile() {
        InputStream resourceAsStream = VinylPlayerApp.class.getClassLoader().getResourceAsStream("turndownfornotch.bin");
        print(resourceAsStream);
    }

    public static void print(InputStream in) {
        StringBuilder builder = new StringBuilder();
        try {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            int counter = 0;
            while ((bytesRead = in.read(buffer)) > -1)
                for (int i = 0; i < bytesRead; i++) {
                    counter++;
                    builder.append(String.format("%02x", buffer[i] & 0xFF)).append(i != bytesRead - 1 ? " " : "");
                    if ((counter % 32 == 0)) {
                        builder.append("\n");
                    }

                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(builder.toString());
    }

    public static void print(List<Byte> buffer) {
        print(buffer.toArray(new Byte[buffer.size()]));
    }

    public static void print(Byte[] buffer) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < buffer.length; i++) {
            builder.append(String.format("%02x", buffer[i] & 0xFF)).append(" ");
            if ((i + 1) % 32 == 0) {
                builder.append("\n");
            }

        }
        System.out.println(builder.toString());
    }
}
