package net.youtics.app.users.model;

import java.util.List;
import java.util.Optional;

public interface Repositorio<T> {

    List<T> findAll();

    Optional<T> findById(Long id);

    Optional<T> save(T t);

    T delete(Long id);

    Optional<T> update(T t, Long id);
}
