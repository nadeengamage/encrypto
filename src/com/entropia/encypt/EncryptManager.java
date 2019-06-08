package com.entropia.encypt;

import com.entropia.ALGORITHM;
import com.entropia.generator.KeyManager;

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

public class EncryptManager {

    /**
     * encrypt content
     * @param content content
     * @param password password
     * @return encrypted content
     */
    public static String encrypt(final String content, final String password) {
        return encrypt(content, password, ALGORITHM.AES);
    }

    /**
     * encrypt content
     * @param content content
     * @param password password
     * @param algorithm encrypt algorithm: AES, DES
     * @return encrypted content
     */
    public static String encrypt(final String content, final String password, final ALGORITHM algorithm) {
        return encrypt(content, password, null, algorithm);
    }

    /**
     * encrypt content
     * @param content content
     * @param password password
     * @param iv initialization vector
     * @param algorithm encrypt algorithm: AES, DES
     * @return encrypted content
     */
    public static String encrypt(final String content, final String password, final byte[] iv, final ALGORITHM algorithm) {
        try {
            final SecretKeySpec keySpec = KeyManager.generateKey(password, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm.toString());
            if (iv == null) {
                cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            } else {
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            }
            byte[] result = cipher.doFinal(content.getBytes(ALGORITHM.CHARSET));
            return KeyManager.byte2Hex(result);
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
