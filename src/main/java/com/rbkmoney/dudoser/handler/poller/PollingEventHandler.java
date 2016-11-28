package com.rbkmoney.dudoser.handler.poller;

import com.rbkmoney.dudoser.handler.Handler;
import com.rbkmoney.thrift.filter.Filter;

public interface PollingEventHandler<T> extends Handler<T> {
    default boolean accept(T value) {
        return getFilter().match(value);
    }

    Filter getFilter();
}
