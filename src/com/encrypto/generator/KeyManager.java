package com.encrypto.generator;

import com.encrypto.ALGORITHM;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;

/**
 * @author Nadeen Gamage
 * @date 07, June, 2019.
 * @description encrypt and decrypt algorithms.
 */

public class KeyManager {

    private static final int DEFAULT_KEY_SIZE = 128;

    /**
     * generate crypto keys.
     * @param password
     * @param algorithm
     * @return random generated string
     */
    public static SecretKeySpec generateKey(final String password, final ALGORITHM algorithm) {
        try {
            final javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance(algorithm.toString());
            SecureRandom secureRandom;
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(password.getBytes());
            int keySize = DEFAULT_KEY_SIZE;
            switch (algorithm) {
                case DES:
                    keySize = 56;
                    break;
            }
            keyGenerator.init(keySize, secureRandom);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encodedFormat = secretKey.getEncoded();
            return new SecretKeySpec(encodedFormat, algorithm.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b: bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = "0" + hex;   // one byte to double-digit hex
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hex2byte(String hex) {
        if (hex == null || hex.length() < 1) {
            return null;
        }
        int len = hex.length() / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
            bytes[i] = (byte) (high * 16 + low);
        }
        return bytes;
    }

    /**
     * get the sha1 hash value of content
     * @param content get the sha1 hash value using this content
     * @return the computed hash value
     */
    public static String sha1(String content) {
        return digest(content, ALGORITHM.SHA1);
    }

    /**
     * get the sha1 hash value of a file
     * @param file get the sha1 hash value using this file
     * @return the computed hash value
     */
    public static String sha1(File file) {
        return digest(file, ALGORITHM.SHA1);
    }

    /**
     * get the md5 of content
     * @param content get the md5 using this content
     * @return the computed md5 hash value
     */
    public static String md5(String content) {
        return digest(content, ALGORITHM.MD5);
    }

    /**
     * get the file md5
     * @param file get the md5 using this file
     * @return the computed md5 hash value
     */
    public static String md5(File file) {
        return digest(file, ALGORITHM.MD5);
    }

    /**
     * get the hash value of content
     * @param content get the hash value using this content
     * @param algorithm hash algorithm
     * @return the computed hash value
     */
    public static String digest(String content, ALGORITHM algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toString());
            messageDigest.update(content.getBytes(ALGORITHM.CHARSET));
            byte[] result = messageDigest.digest();
            return KeyManager.byte2Hex(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get the hash value of a file
     * @param file get the hash value using this file
     * @param algorithm hash algorithm
     * @return the computed hash value
     */
    public static String digest(File file, ALGORITHM algorithm) {
        DigestInputStream inputStream = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.toString());
            inputStream = new DigestInputStream(new FileInputStream(file), messageDigest);
            byte[] buffer = new byte[128 * 1024];
            while (inputStream.read(buffer) > 0);   // message digest will update during reading file
            messageDigest = inputStream.getMessageDigest();
            byte[] result = messageDigest.digest();
            return KeyManager.byte2Hex(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
