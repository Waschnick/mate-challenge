package de.axelspringer.ideas.mate.five.challenges;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

@Service
public class SymetricEncryption {

    private static final String ALGORITHM = "AES";

    @Value("${de.axelspringer.ideas.mate.crypto.password:changeit}")
    private String password;

    @SneakyThrows
    public String encrypt(final String valueToEncrypt) {

        String encryptedVal = null;

        final Key key = generateKeyFromString(password);
        final Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        final byte[] encValue = c.doFinal(valueToEncrypt.getBytes());
        encryptedVal = new BASE64Encoder().encode(encValue);

        return encryptedVal;
    }

    @SneakyThrows
    public String decrypt(final String encryptedValue) {

        String decryptedValue = null;

        final Key key = generateKeyFromString(password);
        final Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        final byte[] decorVal = new BASE64Decoder().decodeBuffer(encryptedValue);
        final byte[] decValue = c.doFinal(decorVal);
        decryptedValue = new String(decValue);

        return decryptedValue;
    }

    private Key generateKeyFromString(final String secKey) throws Exception {
        final byte[] keyVal = new BASE64Decoder().decodeBuffer(secKey);
        final Key key = new SecretKeySpec(keyVal, ALGORITHM);
        return key;
    }
}
