package dev.crawford;

import java.util.List;
import java.util.Scanner;

import dev.crawford.models.User;
import dev.crawford.repositories.UserDAO;
import dev.crawford.services.AuthService;
import dev.crawford.services.UserService;

public class Driver {

    private static Scanner scanner = new Scanner(System.in);
    private static AuthService authService = new AuthService();
    private static UserService userService = new UserService();

    public static void main(String[] args) {

        List<User> users = UserDAO.getAllUsers();
        System.out.println(users);

        System.out.println("Welcome to the ERS System");
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        try {
            if(authService.login(username, password)) {
                System.out.println("Login successful");
                System.out.println("Welcome " + username);
            } else {
                System.out.println("Login unsuccessful");
            }
        } catch (Exception e) {
            System.out.println("Invalid username or password");
        }
    }
}
