package de.axelspringer.ideas.mate.five;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EanHasher {

    // http://www.gs1.org/check-digit-calculator
    //
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
            return  0;
        }
        //subtract the ones digit from 10 to find the checksum
        return (10 - (sum % 10));
    }

    @Test
    public void checksum_shouldReturnCorrectChecksum() throws Exception {
        assertThat(checksum("426036115002")).isEqualTo(8);
        assertThat(checksum("401116011501")).isEqualTo(1);
        assertThat(checksum("400638133393")).isEqualTo(1);
        assertThat(checksum("978316148410")).isEqualTo(0);

    }
}
