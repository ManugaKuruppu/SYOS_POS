package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShelfTest {

    private Shelf shelf;

    @BeforeEach
    public void setUp() {
        shelf = new Shelf("001", "Test Item", 50, "2025-12-31");
    }

    @Test
    public void testConstructor() {
        assertEquals("001", shelf.getItemCode());
        assertEquals("Test Item", shelf.getItemName());
        assertEquals(50, shelf.getQuantity());
        assertEquals("2025-12-31", shelf.getExpiryDate());
    }

    @Test
    public void testSetShelfId() {
        shelf.setShelfId(1);
        assertEquals(1, shelf.getShelfId());
    }

    @Test
    public void testSetItemCode() {
        shelf.setItemCode("002");
        assertEquals("002", shelf.getItemCode());
    }

    @Test
    public void testSetItemName() {
        shelf.setItemName("Updated Item");
        assertEquals("Updated Item", shelf.getItemName());
    }

    @Test
    public void testSetQuantity() {
        shelf.setQuantity(75);
        assertEquals(75, shelf.getQuantity());
    }

    @Test
    public void testSetExpiryDate() {
        shelf.setExpiryDate("2026-01-01");
        assertEquals("2026-01-01", shelf.getExpiryDate());
    }
}