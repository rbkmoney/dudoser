package com.rbkmoney.dudoser.converter;

public interface BinaryConverter<T> {

    T convert(byte[] bin, Class<T> clazz);

}
