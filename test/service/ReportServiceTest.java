package service;

import dao.BillDAO;
import dao.BillItemDAO;
import dao.ItemDAO;
import model.Bill;
import model.BillItem;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ReportServiceTest {

    private ReportService reportService;
    private BillDAO billDAOMock;
    private BillItemDAO billItemDAOMock;
    private ItemDAO itemDAOMock;

    @BeforeEach
    public void setUp() {
        billDAOMock = mock(BillDAO.class);
        billItemDAOMock = mock(BillItemDAO.class);
        itemDAOMock = mock(ItemDAO.class);

        reportService = new ReportService();
        reportService.billDAO = billDAOMock;
        reportService.billItemDAO = billItemDAOMock;
        reportService.itemDAO = itemDAOMock;
    }

    @Test
    public void testGenerateTotalSaleReport() {
        // Setup mock data
        Timestamp date = Timestamp.valueOf("2025-01-01 00:00:00");
        Bill bill1 = new Bill(1, date, 100.0, 90.0, 100.0, 10.0);
        Bill bill2 = new Bill(2, date, 200.0, 180.0, 200.0, 20.0);
        BillItem billItem1 = new BillItem("001", "Item 1", 2, 50.0, 0.0, 100.0);
        BillItem billItem2 = new BillItem("002", "Item 2", 3, 50.0, 0.0, 150.0);

        when(billDAOMock.getBillsByDate(date)).thenReturn(Arrays.asList(bill1, bill2));
        when(billItemDAOMock.getBillItemsByBillId(1)).thenReturn(Arrays.asList(billItem1));
        when(billItemDAOMock.getBillItemsByBillId(2)).thenReturn(Arrays.asList(billItem2));
        when(itemDAOMock.getItemByCode("001")).thenReturn(new Item("001", "Item 1", 1, 50.0, 0.0));
        when(itemDAOMock.getItemByCode("002")).thenReturn(new Item("002", "Item 2", 1, 50.0, 0.0));

        // Capture the output
        reportService.generateTotalSaleReport(date);
    }

    @Test
    public void testGenerateReshelveReport() {
        // Setup mock data
        Item item1 = new Item("001", "Item 1", 1, 50.0, 0.0);
        Item item2 = new Item("002", "Item 2", 1, 30.0, 0.0);

        when(itemDAOMock.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        // Capture the output
        reportService.generateReshelveReport();
    }

    @Test
    public void testGenerateReorderReport() {
        // Setup mock data
        Item item1 = new Item("001", "Item 1", 1, 50.0, 0.0);
        Item item2 = new Item("002", "Item 2", 1, 30.0, 0.0);

        when(itemDAOMock.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        // Capture the output
        reportService.generateReorderReport();
    }

    @Test
    public void testGenerateStockReport() {
        // Setup mock data
        Item item1 = new Item("001", "Item 1", 1, 50.0, 0.0);
        Item item2 = new Item("002", "Item 2", 1, 30.0, 0.0);

        when(itemDAOMock.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        // Capture the output
        reportService.generateStockReport();
    }

    @Test
    public void testGenerateBillReport() {
        // Setup mock data
        Bill bill1 = new Bill(1, Timestamp.valueOf("2025-01-01 00:00:00"), 100.0, 90.0, 100.0, 10.0);
        Bill bill2 = new Bill(2, Timestamp.valueOf("2025-01-02 00:00:00"), 200.0, 180.0, 200.0, 20.0);

        when(billDAOMock.getAllBills()).thenReturn(Arrays.asList(bill1, bill2));

        // Capture the output
        reportService.generateBillReport();
    }
}