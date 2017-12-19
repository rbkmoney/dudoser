package com.rbkmoney.dudoser.dao;

public class Template {
    private String body;
    private boolean isActive;

    public Template(String body, boolean isActive) {
        this.body = body;
        this.isActive = isActive;
    }

    public String getBody() {
        return body;
    }

    public boolean isActive() {
        return isActive;
    }
}
