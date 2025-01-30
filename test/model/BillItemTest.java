package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillItemTest {

    private BillItem billItem;

    @BeforeEach
    public void setUp() {
        billItem = new BillItem("123", "Item 1", 2, 10.0, 10.0, 18.0);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test completed.");
    }

    @Test
    public void testConstructor() {
        assertEquals("123", billItem.getItemCode());
        assertEquals("Item 1", billItem.getItemName());
        assertEquals(2, billItem.getQuantity());
        assertEquals(10.0, billItem.getPrice());
        assertEquals(10.0, billItem.getDiscount());
        assertEquals(18.0, billItem.getTotalPrice());
        System.out.println("Test Passed: testConstructor");
    }

    @Test
    public void testSetQuantity() {
        billItem.setQuantity(3);
        assertEquals(3, billItem.getQuantity());
        assertEquals(27.0, billItem.getTotalPrice(), 0.01); // 3 * 10 - 10%
        System.out.println("Test Passed: testSetQuantity");
    }

    @Test
    public void testSetPrice() {
        billItem.setPrice(15.0);
        assertEquals(15.0, billItem.getPrice());
        assertEquals(27.0, billItem.getTotalPrice(), 0.01); // 2 * 15 - 10%
        System.out.println("Test Passed: testSetPrice");
    }

    @Test
    public void testSetDiscount() {
        billItem.setDiscount(20.0);
        assertEquals(20.0, billItem.getDiscount());
        assertEquals(16.0, billItem.getTotalPrice(), 0.01); // 2 * 10 - 20%
        System.out.println("Test Passed: testSetDiscount");
    }

    @Test
    public void testSetTotalPrice() {
        billItem.setTotalPrice(25.0);
        assertEquals(25.0, billItem.getTotalPrice());
        System.out.println("Test Passed: testSetTotalPrice");
    }

    @Test
    public void testGetDiscountAmount() {
        assertEquals(2.0, billItem.getDiscountAmount(), 0.01); // 2 * 10 * 10%
        System.out.println("Test Passed: testGetDiscountAmount");
    }
}