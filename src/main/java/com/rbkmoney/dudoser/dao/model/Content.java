package com.rbkmoney.dudoser.dao.model;

import lombok.Data;

import java.nio.charset.StandardCharsets;

@Data
public class Content {
    public final String type;
    public final byte[] data;

    public String getDataValue() {
        if (data != null) {
            return new String(data, StandardCharsets.UTF_8);
        }
        return "";
    }

}
