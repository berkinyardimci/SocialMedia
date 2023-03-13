package com.socialmedia.utility;

import java.util.Optional;

public interface IService <T,ID>{
    T save(T entity);

    Iterable<T> saveAll(Iterable<T> entities);

    T update(T entity);

    void delete(T entity);

    void deleteById(ID id);

    Optional<T> findById(ID id);

    Iterable<T> findAll();
}
