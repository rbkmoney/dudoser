package com.rbkmoney.dudoser.handler;

import com.rbkmoney.thrift.filter.Filter;

public interface Handler<T> {

    default boolean accept(T value) {
        return getFilter().match(value);
    }

    void handle(T value);

    Filter getFilter();

}
