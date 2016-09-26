package de.axelspringer.ideas.mate.five.challenges;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SymetricEncryptionTest {

    @InjectMocks
    private SymetricEncryption symetricEncryption;

    @Before
    public void beforeMethod() throws Exception {
        Whitebox.setInternalState(symetricEncryption, "password", "changeit");
    }

    @Test
    public void encrytion() throws Exception {
        String encrypt = symetricEncryption.encrypt("barriwaschi+123@gmail.com");
        assertThat(encrypt).isEqualTo("CWKUCatHTUaAHApvhlLWzKz8XtfxrXS%2BQCAOt%2BSV%2Byg%3D");
        assertThat(symetricEncryption.decrypt(encrypt)).isEqualTo("barriwaschi+123@gmail.com");
    }
}