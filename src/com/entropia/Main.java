package com.entropia;

import com.entropia.encypt.DecryptManager;
import com.entropia.encypt.EncryptManager;
import com.entropia.generator.KeyManager;

/**
 * @author Nadeen Gamage
 * @date 07, June, 2019.
 * @description encrypt and decrypt algorithms.
 */

public class Main {

    public static void main(String[] args) {

        EncryptManager encryptManager = new EncryptManager();
        DecryptManager decryptManager = new DecryptManager();

        /* Declaring Contents */
        String content = "Cryptographic Hashing Example";
        String password = "password!@#";
        System.out.println("content:" + content + "\n");

        /* Encryption */
        String encrypted = encryptManager.encrypt(content, password);
        System.out.println("AES encrypt:" + encrypted);
        System.out.println("AES decrypt:" + decryptManager.decrypt(encrypted, password) + "\n");

        /* Decryption */
        encrypted = encryptManager.encrypt(content, password, ALGORITHM.DES);
        System.out.println("DES encrypt:" + encrypted);
        System.out.println("DES decrypt:" + decryptManager.decrypt(encrypted, password, ALGORITHM.DES) + "\n");

        /* Generate MD5 and SHA256 Tokens */
        System.out.println("md5 hash:" + KeyManager.md5(content));
        System.out.println("sha256 hash:" + KeyManager.digest(content, ALGORITHM.SHA256));
    }
}