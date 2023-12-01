package kr.co.daontec.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Crypto {

    public static String getEncodingSha256(String plainText) {
        return Hashing.sha256().hashString(plainText, StandardCharsets.UTF_8).toString();
    }
}
