package net.youtics.app.users;

import com.sun.tools.jconsole.JConsoleContext;
import net.youtics.app.users.controller.UserController;
import net.youtics.app.users.model.UserRepository;
import net.youtics.app.users.view.UserView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        UserView userView = new UserView();
        UserRepository repository = new UserRepository();

        UserController controller = new UserController(userView,repository);

        controller.init();

        Main.menu(controller);
    }

    public static void menu(UserController controller){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("1 - Agregar");
            System.out.println("2 - Eliminar");
            System.out.println("Ingrese 's' para Salir");

            String ok = scanner.nextLine();

            if("1".equalsIgnoreCase(ok)){
                controller.addUser();
            }else if("2".equalsIgnoreCase(ok)){
                controller.deleteUser();
            }else if("s".equalsIgnoreCase(ok)){
                break;
            }
        }
    }
}
