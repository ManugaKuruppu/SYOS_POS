package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShelfItemTest {

    private ShelfItem shelfItem;

    @BeforeEach
    public void setUp() {
        shelfItem = new ShelfItem("001", "Test Item", 100, "2025-12-31");
    }

    @Test
    public void testConstructor() {
        assertEquals("001", shelfItem.getItemCode());
        assertEquals("Test Item", shelfItem.getItemName());
        assertEquals(100, shelfItem.getQuantity());
        assertEquals("2025-12-31", shelfItem.getExpiryDate());
    }

    @Test
    public void testSetItemCode() {
        shelfItem.setItemCode("002");
        assertEquals("002", shelfItem.getItemCode());
    }

    @Test
    public void testSetItemName() {
        shelfItem.setItemName("Updated Item");
        assertEquals("Updated Item", shelfItem.getItemName());
    }

    @Test
    public void testSetQuantity() {
        shelfItem.setQuantity(150);
        assertEquals(150, shelfItem.getQuantity());
    }

    @Test
    public void testSetExpiryDate() {
        shelfItem.setExpiryDate("2026-01-01");
        assertEquals("2026-01-01", shelfItem.getExpiryDate());
    }
}