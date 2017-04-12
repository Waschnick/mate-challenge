package de.axelspringer.ideas.mate.five;

import org.junit.Test;

public class EanHasher {

    // http://www.gs1.org/check-digit-calculator
    //
    public static String checksum(String ean) {

    }

    @Test
    public void name() throws Exception {
        assertThat(checksum("401116011501")).isEqualTo("1");

    }
}
