package controller;

import model.ShelfItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ShelfService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ShelfControllerTest {

    @Mock
    private ShelfService shelfService;

    @InjectMocks
    private ShelfController shelfController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewShelfItems() {
        // Mock the shelf items
        List<ShelfItem> mockShelfItems = createMockShelfItems();
        when(shelfService.getAllShelfItems()).thenReturn(mockShelfItems);

        // Call the method under test
        shelfController.viewShelfItems();

        // Verify interactions with the mock objects
        verify(shelfService, times(1)).getAllShelfItems();
    }

    private List<ShelfItem> createMockShelfItems() {
        List<ShelfItem> shelfItems = new ArrayList<>();
        shelfItems.add(new ShelfItem("code123", "Item 1", 10, "2025-12-31"));
        shelfItems.add(new ShelfItem("code456", "Item 2", 5, "2025-06-30"));
        return shelfItems;
    }
}