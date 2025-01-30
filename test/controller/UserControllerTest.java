package controller;

import dao.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private Scanner scanner;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userDAO, scanner);
    }

    @Test
    public void testRegisterUserSuccess() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("testuser")
                .thenReturn("testpassword");

        // Mock the DAO call
        when(userDAO.registerUser(anyString(), anyString())).thenReturn(true);

        // Call the method under test
        userController.registerUser();

        // Verify interactions with the mock objects
        verify(scanner, times(2)).nextLine();
        verify(userDAO, times(1)).registerUser("testuser", "testpassword");

        // Print information and show test passed
        System.out.println("Test Passed: testRegisterUserSuccess");
        System.out.println("Scanner inputs: testuser, testpassword");
        System.out.println("User registration succeeded.");
    }

    @Test
    public void testRegisterUserFailure() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("testuser")
                .thenReturn("testpassword");

        // Mock the DAO call
        when(userDAO.registerUser(anyString(), anyString())).thenReturn(false);

        // Call the method under test
        userController.registerUser();

        // Verify interactions with the mock objects
        verify(scanner, times(2)).nextLine();
        verify(userDAO, times(1)).registerUser("testuser", "testpassword");

        // Print information and show test passed
        System.out.println("Test Passed: testRegisterUserFailure");
        System.out.println("Scanner inputs: testuser, testpassword");
        System.out.println("User registration failed.");
    }

    @Test
    public void testLoginUserSuccess() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("testuser")
                .thenReturn("testpassword");

        // Mock the DAO call
        when(userDAO.validateUser(anyString(), anyString())).thenReturn(true);

        // Call the method under test
        boolean result = userController.loginUser();

        // Verify interactions with the mock objects
        verify(scanner, times(2)).nextLine();
        verify(userDAO, times(1)).validateUser("testuser", "testpassword");

        // Assert the expected result
        assertTrue(result);

        // Print information and show test passed
        System.out.println("Test Passed: testLoginUserSuccess");
        System.out.println("Scanner inputs: testuser, testpassword");
        System.out.println("User login succeeded.");
    }

    @Test
    public void testLoginUserFailure() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("testuser")
                .thenReturn("testpassword");

        // Mock the DAO call
        when(userDAO.validateUser(anyString(), anyString())).thenReturn(false);

        // Call the method under test
        boolean result = userController.loginUser();

        // Verify interactions with the mock objects
        verify(scanner, times(2)).nextLine();
        verify(userDAO, times(1)).validateUser("testuser", "testpassword");

        // Assert the expected result
        assertFalse(result);

        // Print information and show test passed
        System.out.println("Test Passed: testLoginUserFailure");
        System.out.println("Scanner inputs: testuser, testpassword");
        System.out.println("User login failed.");
    }
}