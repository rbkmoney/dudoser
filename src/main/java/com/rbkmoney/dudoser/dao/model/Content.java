package com.rbkmoney.dudoser.dao.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Getter
@Setter
@NoArgsConstructor
public class Content {
    public String type;
    public byte[] data;

    public String getDataValue() {
        if (data != null) {
            return new String(data, StandardCharsets.UTF_8);
        }
        return "";
    }

}
