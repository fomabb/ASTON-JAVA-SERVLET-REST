package org.iase24.nikolay.kirilyuk.mapper;

public interface Mapper<F, T> {
    T map(F object);
}
