package net.youtics.app.users.controller;

import net.youtics.app.users.model.User;
import net.youtics.app.users.model.UserRepository;
import net.youtics.app.users.view.UserView;

import java.awt.*;
import java.util.Optional;

public class UserController {

    private UserView userView;
    private UserRepository repository;

    public UserController(UserView userView, UserRepository repository) {
        this.userView = userView;
        this.repository = repository;
    }

    public void init(){
        repository.loadUsersFromDB();
        repository.getListBehaviorSubject().subscribe( list -> userView.listarUsers(list));
    }

    public User addUser(){
        User user = userView.addUser();
        Optional<User> userOptional = repository.add(user);
        return userOptional.get();
    }

    public User deleteUser(){
        Long id = userView.deleteUser();
        Optional<User> userOptional = repository.delete(id);
        return userOptional.get();
    }



    public void getAllUser(){
        userView.listarUsers(repository.findAll());
    }
}
