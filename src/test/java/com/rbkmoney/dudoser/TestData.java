package com.rbkmoney.dudoser;

import lombok.NoArgsConstructor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@NoArgsConstructor
public class TestData {

    public static byte[] kebMetadata() {
        return ("{" +
                "\"transaction_id\":\"12345678901234567890\"," +
                "\"customer_initials\":\"Говнов Петр Сергеевич\"," +
                "\"fee_type\":\"1\",\"account\":\"40817810059900010245\"," +
                "\"original_amount\":\"1000000\"," +
                "\"fee_amount\":\"1000\"" +
                "}")
                .getBytes(StandardCharsets.UTF_8);
    }

    public static String kebTemplate() {
        return decode(loadFile("template/keb-template.html"));
    }

    private static String decode(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static byte[] loadFile(String fileName) {
        final InputStream in = TestData.class.getClassLoader().getResourceAsStream(fileName);
        Objects.requireNonNull(in, "in can't be null");
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (in; out) {
            byte[] buffer = new byte[4096];
            for (int length = in.read(buffer); length >= 0; length = in.read(buffer)) {
                out.write(buffer, 0, length);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Exception while loading file: " + fileName, e);
        }
    }

}
