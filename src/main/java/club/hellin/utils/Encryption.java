package club.hellin.utils;

import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * This is for encrypting anything sensitive for reddit, so it's not stored in plain text
 */

@UtilityClass
public final class Encryption {
    public final String KEY = "6b492475bf7h2db5";

    public String encrypt(final String data) {
        try {
            final SecretKeySpec spec = new SecretKeySpec(KEY.getBytes(), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.ENCRYPT_MODE, spec);

            final byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (final Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static String decrypt(final String data) {
        try {
            final SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
            final Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");

            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            final byte[] original = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(original);
        } catch (final Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}