package de.axelspringer.ideas.mate.six;

public class EanHasher {

    public void isValidEan(String ean) {

    }

    public static Integer checksum(String ean) {
        if (ean.length() != 12) {
            throw new IllegalArgumentException("EAN without checksum must be 12 chars and was " + ean.length());
        }

        int sum = 0;
        for (int i = 0; i < ean.length(); i++) {
            String c = ean.substring(i, i + 1);
            int weight = i % 2 == 0 ? 1 : 3;
            sum += Integer.valueOf(c) * weight;
        }

        if (sum % 10 == 0) {
            return 0;
        }
        //subtract the ones digit from 10 to find the checksum
        return (10 - (sum % 10));
    }


}
