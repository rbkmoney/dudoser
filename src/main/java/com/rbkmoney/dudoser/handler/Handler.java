package com.rbkmoney.dudoser.handler;

/**
 * Created by inal on 24.11.2016.
 */
public interface Handler<C> {

    default boolean accept(C change) {
        return getChangeType().getFilter().match(change);
    }

    void handle(C change, String sourceId, Long sequenceId);

    ChangeType getChangeType();

}
