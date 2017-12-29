package de.axelspringer.ideas.mate.five;

import de.axelspringer.ideas.mate.MateChallengeProperties;
import de.axelspringer.ideas.mate.five.SymetricEncryption;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SymetricEncryptionTest {

    @InjectMocks
    private SymetricEncryption symetricEncryption;

    @Before
    public void beforeMethod() throws Exception {
        MateChallengeProperties.INSTANCE.encryptionPassword = "changeit";
    }

    @Test
    @Ignore // --> Takes to much time
    public void encrytion() throws Exception {
        String encrypt = symetricEncryption.encrypt("barriwaschi+130219734@gmail.com");
        assertThat(encrypt).isEqualTo("yNin4r7k7C9K6DWv9FVpvjG0ko_-FmTzoqK58X5cqGk=");
        assertThat(symetricEncryption.decrypt(encrypt)).isEqualTo("barriwaschi+130219734@gmail.com");
    }
}