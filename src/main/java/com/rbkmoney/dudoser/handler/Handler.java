package com.rbkmoney.dudoser.handler;

import com.rbkmoney.thrift.filter.Filter;

/**
 * Created by inal on 24.11.2016.
 */
public interface Handler<T> {

    default boolean accept(T value) {
        return getFilter().match(value);
    }

    void handle(T value);

    Filter getFilter();
}
