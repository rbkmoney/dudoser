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

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Template{" +
                "body='" + body.substring(1, 10) + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
