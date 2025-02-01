package controller;

import dao.UserDAO;

import java.util.Scanner;

public class UserController {
    private UserDAO userDAO;
    private Scanner scanner;

    public UserController() {
        userDAO = new UserDAO();
        scanner = new Scanner(System.in);
    }
    // Constructor with parameters
    public UserController(UserDAO userDAO, Scanner scanner) {
        this.userDAO = userDAO;
        this.scanner = scanner;
    }

    public void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userDAO.registerUser(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username may already be taken.");
        }
    }

    public boolean loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        // In a real application, compare the hashed password

        if (userDAO.validateUser(username, password)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }
}