package net.youtics.app.users.model;

import java.util.List;
import java.util.Optional;

public interface UserInterface {

    List<User> findAll();

    Optional<User> add(User user);

    Optional<User> findById(Long id);

    Optional<User> delete(Long id);

    Optional<User> update(Long id, User user);
}
