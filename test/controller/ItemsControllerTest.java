package controller;

import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ItemsService;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class ItemsControllerTest {

    @Mock
    private ItemsService itemsService;

    @Mock
    private Scanner scanner;

    @InjectMocks
    private ItemsController itemsController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddItemSuccess() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("code123")
                .thenReturn("Item Name")
                .thenReturn("1")
                .thenReturn("100.0")
                .thenReturn("10.0");

        // Mock the service call
        when(itemsService.addItem(any(Item.class))).thenReturn(true);

        // Call the method under test
        itemsController.addItem();

        // Verify interactions with the mock objects
        verify(scanner, times(5)).nextLine();
        verify(itemsService, times(1)).addItem(any(Item.class));
    }

    @Test
    public void testAddItemFailure() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("code123")
                .thenReturn("Item Name")
                .thenReturn("1")
                .thenReturn("100.0")
                .thenReturn("10.0");

        // Mock the service call
        when(itemsService.addItem(any(Item.class))).thenReturn(false);

        // Call the method under test
        itemsController.addItem();

        // Verify interactions with the mock objects
        verify(scanner, times(5)).nextLine();
        verify(itemsService, times(1)).addItem(any(Item.class));
    }
}