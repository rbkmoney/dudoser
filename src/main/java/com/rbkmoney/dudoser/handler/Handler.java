package com.rbkmoney.dudoser.handler;

/**
 * Created by inal on 24.11.2016.
 */
public interface Handler<T> {

    boolean accept(T value);

    void handle(T value);
}
