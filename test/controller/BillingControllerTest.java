package controller;

import model.Bill;
import model.BillItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.BillingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class BillingControllerTest {

    @Mock
    private BillingService billingService;

    @Mock
    private Scanner scanner;

    @InjectMocks
    private BillingController billingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartBilling() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("item1")
                .thenReturn("2")
                .thenReturn("item2")
                .thenReturn("3")
                .thenReturn("done")
                .thenReturn("100.0");

        // Mock the prepared bill
        Bill preparedBill = createMockBill();
        when(billingService.prepareBill(anyList(), anyList())).thenReturn(preparedBill);

        // Mock the completed bill
        Bill completedBill = createMockBill();
        completedBill.setCashTendered(100.0);
        completedBill.setChangeAmount(41.5);
        when(billingService.completeBill(any(Bill.class), anyDouble())).thenReturn(completedBill);

        // Call the method under test
        billingController.startBilling();

        // Verify interactions with the mock objects
        verify(billingService, times(1)).prepareBill(anyList(), anyList());
        verify(billingService, times(1)).completeBill(any(Bill.class), anyDouble());
    }

    @Test
    public void testDisplayPreparedBill() {
        Bill bill = createMockBill();
        billingController.displayPreparedBill(bill);
    }

    @Test
    public void testDisplayBill() {
        Bill bill = createMockBill();
        bill.setCashTendered(100.0);
        bill.setChangeAmount(41.5);
        billingController.displayBill(bill);
    }

    private Bill createMockBill() {
        Bill bill = new Bill();
        List<BillItem> billItems = new ArrayList<>();

        bill.setBillItems(billItems);
        bill.setFinalTotal(58.5);

        return bill;
    }
}