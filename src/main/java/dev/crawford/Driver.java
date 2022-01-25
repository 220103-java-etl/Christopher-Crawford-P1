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

        System.out.println("Would you like to login or register?");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");

        String choice = scanner.nextLine();
        String lowerChoice = choice.toLowerCase();

        while( lowerChoice != "3" || lowerChoice != "exit") {
            switch (lowerChoice) {
            case "1":
                login();
                break;
            case "login":
                login();
                break;
            case "2":
                register();
                break;
            case "register":
                register();
                break;
            case "3":
                System.out.println("Goodbye!");
                break;
            default:
                System.out.println("Invalid choice, please try again");
                choice = scanner.nextLine();
                lowerChoice = choice.toLowerCase();
            }
        }
    }

    // Login Case 1 & Login
    public static void login() {
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

    // Register Case 2 & Register
    public static void register() {
        String role = "";

        System.out.println("Please enter your First Name");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your Last Name");
        String lastName = scanner.nextLine();
        System.out.println("Please enter your Username");
        String username = scanner.nextLine();
        System.out.println("Please enter your Password");
        String password = scanner.nextLine();
        System.out.println("Please choose your Role");
        System.out.println("1. Employee");
        System.out.println("2. Finance Manager");
        String roleChoice = scanner.nextLine();
        String lowerRole = roleChoice.toLowerCase();
        role = getRole(lowerRole);
        User user = UserDAO.getByUsername(username);
        System.out.println(user);
        if(user == null) {
            try {
                User newUser = new User(0, firstName, lastName, username, password, role);
                UserDAO.create(newUser);
                System.out.println("User created successfully");
            } catch (Exception e) {
                System.out.println("User creation unsuccessful");
            }
            System.out.println("Registration successful");
            System.out.println(UserDAO.getByUsername(username));    
        } else {
            System.out.println("Username already exists");
            System.out.println(user);
        }
    }
    // Method to get role
    public static String getRole(String lowerRole) {
        switch(lowerRole) {
            case "1":
                return "EMPLOYEE";
            case "employee":
                return "EMPLOYEE";
            case "2":
                return "FINANCE MANAGER";
            case "finance manager":
                return "FINANCE MANAGER";
            default:
                System.out.println("Invalid choice, please try again");
                lowerRole = scanner.nextLine();
                return lowerRole = lowerRole.toLowerCase();
        }
    }
}
