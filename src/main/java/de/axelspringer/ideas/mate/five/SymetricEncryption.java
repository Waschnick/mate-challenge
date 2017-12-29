package de.axelspringer.ideas.mate.five;

import de.axelspringer.ideas.mate.MateChallengeProperties;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;

public class SymetricEncryption {

    private static final String ALGORITHM = "AES";

    public String encrypt(String valueToEncrypt) {
        try {

            String encryptedVal = null;

            final Key key = generateKeyFromString(MateChallengeProperties.INSTANCE.encryptionPassword);
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            final byte[] encValue = c.doFinal(valueToEncrypt.getBytes());
            encryptedVal = new BASE64Encoder().encode(encValue);

            return encode(encryptedVal);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public String decrypt(String encryptedValue) {
        try {


            encryptedValue = decode(encryptedValue);

            String decryptedValue = null;

            final Key key = generateKeyFromString(MateChallengeProperties.INSTANCE.encryptionPassword);
            final Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            final byte[] decorVal = new BASE64Decoder().decodeBuffer(encryptedValue);
            final byte[] decValue = c.doFinal(decorVal);
            decryptedValue = new String(decValue);

            return decryptedValue;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private String encode(String value) {
        return value.replaceAll("\\+", "-").replaceAll("/", "_");
    }

    private String decode(String value) {
        return value.replaceAll("-", "\\+").replaceAll("_", "/");
    }

    private Key generateKeyFromString(final String secKey) throws Exception {
        byte[] key = new BASE64Decoder().decodeBuffer(Sha1.sha1(secKey));
        key = Arrays.copyOf(key, 16); // use only first 128 bit
        return new SecretKeySpec(key, ALGORITHM);
    }
}
