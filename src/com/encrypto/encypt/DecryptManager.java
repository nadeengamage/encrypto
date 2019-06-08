package com.encrypto.encypt;

import com.encrypto.ALGORITHM;
import com.encrypto.generator.KeyManager;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Nadeen Gamage
 * @date 07, June, 2019.
 * @description encrypt and decrypt algorithms.
 */

public class DecryptManager {

    /**
     * decrypt content
     * @param encryptedContent encrypted content
     * @param password password
     * @return decrypted content
     */
    public static String decrypt(final String encryptedContent, final String password) {
        return decrypt(encryptedContent, password, ALGORITHM.AES);
    }

    /**
     * decrypt content
     * @param encryptedContent encrypted content
     * @param password password
     * @param algorithm decrypt algorithm: AES, DES
     * @return decrypted content
     */
    public static String decrypt(final String encryptedContent, final String password, final ALGORITHM algorithm) {
        return decrypt(encryptedContent, password, null, algorithm);
    }

    /**
     * decrypt content
     * @param encryptedContent encrypted content
     * @param password password
     * @param iv initialization vector
     * @param algorithm decrypt algorithm: AES, DES
     * @return decrypted content
     */
    public static String decrypt(final String encryptedContent, final String password, final byte[] iv, final ALGORITHM algorithm) {
        try {
            final SecretKeySpec keySpec = KeyManager.generateKey(password, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm.toString());
            if (iv == null) {
                cipher.init(Cipher.DECRYPT_MODE, keySpec);
            } else {
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            }
            byte[] result = cipher.doFinal(KeyManager.hex2byte(encryptedContent));
            return new String(result, ALGORITHM.CHARSET);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

}
