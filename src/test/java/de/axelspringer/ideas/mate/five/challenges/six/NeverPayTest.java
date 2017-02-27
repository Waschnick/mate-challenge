package de.axelspringer.ideas.mate.five.challenges.six;

import de.axelspringer.ideas.mate.six.NeverPay;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NeverPayTest {

    private static final String NORMAL_TEXT = "vor mehr als 25 Jahren untergetauchten";
    private static final String ENCODED_TEXT = "wps nfis bmt 36 Kbisfo voufshfubvdiufo";

    public NeverPay neverPay = new NeverPay();

    @Test
    public void neverPay_shouldDecodeCorrectly() throws Exception {
        assertThat(neverPay.decode(ENCODED_TEXT.toCharArray())).isEqualTo(NORMAL_TEXT);
    }

    @Test
    public void neverPay_shouldEncodeCorrectly() throws Exception {
        assertThat(neverPay.encode(NORMAL_TEXT.toCharArray())).isEqualTo(ENCODED_TEXT);
    }
}
