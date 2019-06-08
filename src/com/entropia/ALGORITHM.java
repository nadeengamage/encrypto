package com.entropia;

/**
 * @author Nadeen Gamage
 * @date 07, June, 2019.
 * @description encrypt and decrypt algorithms.
 */

public enum ALGORITHM {
    // Algorithm is for encrypt and decrypt.
    AES("AES"),
    DES("DES"),

    // Algorithm is for message digest.
    MD2("MD2"),
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    // Default charset type.
    public static final String CHARSET = "UTF-8";

    private final String text;

    ALGORITHM(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
