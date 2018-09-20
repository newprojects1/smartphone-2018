package com.codegym.service;

import com.codegym.model.Type;

public interface TypeService {
    Iterable<Type> findAll();

    Type findById(Long id);

    void save(Type type);

    void remove(Long id);
}
