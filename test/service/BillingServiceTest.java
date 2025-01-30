package service;

import dao.BillDAO;
import dao.BillItemDAO;
import dao.ItemDAO;
import dao.ShelfDAO;
import model.Bill;
import model.BillItem;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BillingServiceTest {

    private BillingService billingService;
    private BillDAO billDAOMock;
    private BillItemDAO billItemDAOMock;
    private ItemDAO itemDAOMock;
    private ShelfDAO shelfDAOMock;

    @BeforeEach
    public void setUp() {
        billDAOMock = mock(BillDAO.class);
        billItemDAOMock = mock(BillItemDAO.class);
        itemDAOMock = mock(ItemDAO.class);
        shelfDAOMock = mock(ShelfDAO.class);

        billingService = new BillingService();
        billingService.billDAO = billDAOMock;
        billingService.billItemDAO = billItemDAOMock;
        billingService.itemDAO = itemDAOMock;
        billingService.shelfDAO = shelfDAOMock;
    }


    @Test
    public void testCompleteBillFailsOnShelfUpdate() {
        // Setup the bill and bill items
        Bill bill = new Bill();
        bill.setBillId(1);
        bill.setFinalTotal(270.0);
        bill.setBillItems(Arrays.asList(
                new BillItem("001", "Test Item 1", 2, 100.0, 10.0, 180.0),
                new BillItem("002", "Test Item 2", 3, 50.0, 20.0, 90.0)
        ));

        // Setup mock return values
        when(shelfDAOMock.reduceShelfQuantity("001", 2)).thenReturn(false);

        // Complete the bill
        Bill completedBill = billingService.completeBill(bill, 300.0);

        // Verify the results
        assertNull(completedBill);
        verify(billDAOMock, never()).addBill(any(Bill.class));
        verify(billItemDAOMock, never()).addBillItem(any(BillItem.class));
    }
}