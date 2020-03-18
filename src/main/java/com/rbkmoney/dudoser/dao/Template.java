package com.rbkmoney.dudoser.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Template {
    private String body;
    private String subject;
    private boolean isActive;
}
