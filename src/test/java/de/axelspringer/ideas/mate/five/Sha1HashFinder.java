package de.axelspringer.ideas.mate.five;

import lombok.SneakyThrows;

import java.security.MessageDigest;

public class Sha1HashFinder {

    public static void main(String[] args) throws Exception {
        // example+1899634@example.com | sha1sum
        String baseMail = "barriwaschi+%d@gmail.com";
        String hash = "";
        long counter = 0;
        while (!hash.startsWith("a51dea5")) {
            counter++;
            hash = sha1(String.format(baseMail, counter));
            if (counter % 1_000_000 == 0) {
                // FIXME Remove System.out
                System.out.println(counter / 1_000_000 + "M");
            }
        }
        // FIXME Remove System.out
        System.out.println("found it, hash is: " + hash);
        // FIXME Remove System.out
        System.out.println(String.format(baseMail, counter));
    }

    @SneakyThrows
    public static String sha1(String input) {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
