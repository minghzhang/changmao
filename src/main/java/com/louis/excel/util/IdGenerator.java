package com.louis.excel.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;

@Slf4j
public class IdGenerator {

    public static String uuid() {
        String uuid = uuidBase64UrlSafe();
        if (!uuid.contains("-")) {
            return uuid;
        }
        log.info("again");
        return uuid();
    }

    public static String uuidBase64UrlSafe() {
        byte[] data = toByteArray(java.util.UUID.randomUUID());
        String s = Base64.encodeBase64URLSafeString(data);
        return s.split("=")[0];
    }

    private static byte[] toByteArray(java.util.UUID uuid) {
        ByteBuffer bytes = ByteBuffer.wrap(new byte[16]);
        bytes.putLong(uuid.getMostSignificantBits());
        bytes.putLong(uuid.getLeastSignificantBits());
        return bytes.array();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            log.info("uuid: {}", IdGenerator.uuid());
        }
    }
}
