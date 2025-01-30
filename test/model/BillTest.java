package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BillTest {

    private Bill bill;

    @BeforeEach
    public void setUp() {
        bill = new Bill();
    }

    @Test
    public void testSettersAndGetters() {
        Timestamp billDate = new Timestamp(System.currentTimeMillis());
        bill.setBillId(1);
        bill.setBillDate(billDate);
        bill.setTotal(100.0);
        bill.setFinalTotal(90.0);
        bill.setCashTendered(100.0);
        bill.setChangeAmount(10.0);

        assertEquals(1, bill.getBillId());
        assertEquals(billDate, bill.getBillDate());
        assertEquals(100.0, bill.getTotal());
        assertEquals(90.0, bill.getFinalTotal());
        assertEquals(100.0, bill.getCashTendered());
        assertEquals(10.0, bill.getChangeAmount());
    }

    @Test
    public void testAddBillItem() {
        BillItem item = new BillItem("123", "Item 1", 2, 10.0, 2.0, 18.0);
        bill.addBillItem(item);

        List<BillItem> items = bill.getBillItems();
        assertEquals(1, items.size());
        assertEquals(item, items.get(0));
        assertEquals(18.0, bill.getTotal());
        assertEquals(20.0, bill.getFinalTotal());
        assertEquals(2.0, bill.getTotalDiscount());
    }

    @Test
    public void testRemoveBillItem() {
        BillItem item = new BillItem("123", "Item 1", 2, 10.0, 2.0, 18.0);
        bill.addBillItem(item);
        bill.removeBillItem(item);

        List<BillItem> items = bill.getBillItems();
        assertTrue(items.isEmpty());
        assertEquals(0.0, bill.getTotal());
        assertEquals(0.0, bill.getFinalTotal());
        assertEquals(0.0, bill.getTotalDiscount());
    }

    @Test
    public void testSetBillItems() {
        List<BillItem> items = new ArrayList<>();
        items.add(new BillItem("123", "Item 1", 2, 10.0, 2.0, 18.0));
        items.add(new BillItem("456", "Item 2", 1, 20.0, 5.0, 15.0));
        bill.setBillItems(items);

        assertEquals(2, bill.getBillItems().size());
        assertEquals(33.0, bill.getTotal());
        assertEquals(40.0, bill.getFinalTotal());
        assertEquals(7.0, bill.getTotalDiscount());
    }

    @Test
    public void testCalculateChangeAmount() {
        bill.setFinalTotal(90.0);
        bill.setCashTendered(100.0);

        assertEquals(10.0, bill.getChangeAmount());
    }
}