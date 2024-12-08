package net.youtics.app.users.view;

import net.youtics.app.users.model.User;

import java.util.List;
import java.util.Scanner;

public class UserView {

    public User addUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el nombre de usuario");
        String username = scanner.nextLine();

        System.out.println("Ingrese el password");
        String password = scanner.nextLine();

        User user = new User(username, password);

        return user;
    }

    public Long deleteUser(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el ID del User que quiere eliminar");
        Long id = scanner.nextLong();

        return id;
    }

    public void listarUsers(List<User> users){
        users.forEach(user -> {
            System.out.println(user.getUsername());
        });
    }
}
