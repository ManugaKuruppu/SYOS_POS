package service;

import dao.ShelfDAO;
import model.ShelfItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ShelfServiceTest {

    private ShelfService shelfService;
    private ShelfDAO shelfDAOMock;

    @BeforeEach
    public void setUp() {
        shelfDAOMock = mock(ShelfDAO.class);
        shelfService = new ShelfService();
        shelfService.shelfDAO = shelfDAOMock;
    }

    @Test
    public void testGetAllShelfItems() {
        // Setup mock return value
        ShelfItem item1 = new ShelfItem("001", "Item 1", 10, "2025-12-31");
        ShelfItem item2 = new ShelfItem("002", "Item 2", 20, "2025-12-31");

        List<ShelfItem> expectedItems = Arrays.asList(item1, item2);
        when(shelfDAOMock.getAllShelfItems()).thenReturn(expectedItems);

        // Test getAllShelfItems method
        List<ShelfItem> actualItems = shelfService.getAllShelfItems();

        // Verify the result
        assertEquals(expectedItems, actualItems);
        verify(shelfDAOMock, times(1)).getAllShelfItems();

        // Print information and show test passed
        System.out.println("Expected Shelf Items: " + expectedItems);
        System.out.println("Actual Shelf Items: " + actualItems);
        System.out.println("Test Passed: getAllShelfItems method works correctly.");
    }
}