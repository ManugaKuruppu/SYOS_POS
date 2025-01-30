package service;

import dao.ItemsDAO;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ItemsServiceTest {

    private ItemsService itemsService;
    private ItemsDAO itemsDAOMock;

    @BeforeEach
    public void setUp() {
        itemsDAOMock = mock(ItemsDAO.class);
        itemsService = new ItemsService();
        itemsService.itemsDAO = itemsDAOMock;
    }

    @Test
    public void testAddItem_ItemCodeExists() {
        // Setup mock return value
        when(itemsDAOMock.itemCodeExists("001")).thenReturn(true);

        // Create an item
        Item item = new Item("001", "Test Item", 1, 100.0, 10.0);

        // Test addItem method
        boolean result = itemsService.addItem(item);

        // Verify the result
        assertFalse(result);
        verify(itemsDAOMock, never()).addItem(item);
    }

    @Test
    public void testAddItem_ItemCodeDoesNotExist() {
        // Setup mock return value
        when(itemsDAOMock.itemCodeExists("001")).thenReturn(false);
        when(itemsDAOMock.addItem(any(Item.class))).thenReturn(true);

        // Create an item
        Item item = new Item("001", "Test Item", 1, 100.0, 10.0);

        // Test addItem method
        boolean result = itemsService.addItem(item);

        // Verify the result
        assertTrue(result);
        verify(itemsDAOMock, times(1)).addItem(item);
    }
}