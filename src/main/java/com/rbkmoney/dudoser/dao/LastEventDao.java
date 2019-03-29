package com.rbkmoney.dudoser.dao;

/**
 * Created by inal on 24.11.2016.
 */
public interface LastEventDao {
    Long get(int id);

    void set(Long eventId, int id);
}
