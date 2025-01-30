package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest {

    private Stock stock;
    private Date purchaseDate;
    private Date expiryDate;

    @BeforeEach
    public void setUp() {
        purchaseDate = Date.valueOf("2025-01-01");
        expiryDate = Date.valueOf("2026-01-01");
        stock = new Stock(1, "001", purchaseDate, 100, expiryDate);
    }

    @Test
    public void testConstructor() {
        assertEquals(1, stock.getStockId());
        assertEquals("001", stock.getItemCode());
        assertEquals(purchaseDate, stock.getPurchaseDate());
        assertEquals(100, stock.getQuantity());
        assertEquals(expiryDate, stock.getExpiryDate());
    }

    @Test
    public void testSetStockId() {
        stock.setStockId(2);
        assertEquals(2, stock.getStockId());
    }

    @Test
    public void testSetItemCode() {
        stock.setItemCode("002");
        assertEquals("002", stock.getItemCode());
    }

    @Test
    public void testSetPurchaseDate() {
        Date newPurchaseDate = Date.valueOf("2025-02-01");
        stock.setPurchaseDate(newPurchaseDate);
        assertEquals(newPurchaseDate, stock.getPurchaseDate());
    }

    @Test
    public void testSetQuantity() {
        stock.setQuantity(150);
        assertEquals(150, stock.getQuantity());
    }

    @Test
    public void testSetExpiryDate() {
        Date newExpiryDate = Date.valueOf("2026-02-01");
        stock.setExpiryDate(newExpiryDate);
        assertEquals(newExpiryDate, stock.getExpiryDate());
    }
}