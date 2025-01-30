package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemTest {

    private Item item;

    @BeforeEach
    public void setUp() {
        item = new Item("001", "Test Item", 1, 100.0, 10.0);
        item.setQuantity(5);
    }

    @Test
    public void testConstructor() {
        assertEquals("001", item.getItemCode());
        assertEquals("Test Item", item.getItemName());
        assertEquals(1, item.getCategoryId());
        assertEquals(100.0, item.getPrice());
        assertEquals(10.0, item.getDiscount());
    }

    @Test
    public void testSetItemCode() {
        item.setItemCode("002");
        assertEquals("002", item.getItemCode());
    }

    @Test
    public void testSetItemName() {
        item.setItemName("Updated Item");
        assertEquals("Updated Item", item.getItemName());
    }

    @Test
    public void testSetCategoryId() {
        item.setCategoryId(2);
        assertEquals(2, item.getCategoryId());
    }

    @Test
    public void testSetPrice() {
        item.setPrice(150.0);
        assertEquals(150.0, item.getPrice());
    }

    @Test
    public void testSetDiscount() {
        item.setDiscount(15.0);
        assertEquals(15.0, item.getDiscount());
    }

    @Test
    public void testCalculateDiscountAmount() {
        assertEquals(10.0, item.calculateDiscountAmount(), 0.01); // 100 * 10%
    }

    @Test
    public void testCalculateFinalPrice() {
        assertEquals(90.0, item.calculateFinalPrice(), 0.01); // 100 - 10
    }

    @Test
    public void testSetQuantity() {
        item.setQuantity(10);
        assertEquals(10, item.getQuantity());
    }

    @Test
    public void testToString() {
        String expected = "Item{itemCode='001', itemName='Test Item', categoryId=1, price=100.0, discount=10.0, discountAmount=10.0, finalPrice=90.0}";
        assertEquals(expected, item.toString());
    }
}