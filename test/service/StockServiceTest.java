package service;

import dao.StockDAO;
import dao.ShelfDAO;
import model.Stock;
import model.Shelf;
import observer.Observer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockServiceTest {

    @Mock
    private StockDAO stockDAOMock;

    @Mock
    private ShelfDAO shelfDAOMock;

    @Mock
    private Observer observerMock;

    @InjectMocks
    private StockService stockService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService();
        stockService.stockDAO = stockDAOMock;
        stockService.shelfDAO = shelfDAOMock;
        stockService.registerObserver(observerMock);
    }

    @Test
    public void testAddStock_ItemCodeDoesNotExist() {
        // Setup mock return value
        when(stockDAOMock.itemCodeExists("item123")).thenReturn(false);

        // Create a stock item
        Stock stock = new Stock(1, "item123", Date.valueOf("2025-01-01"), 10, Date.valueOf("2025-12-31"));

        // Test addStock method
        boolean result = stockService.addStock(stock);

        // Verify the result
        assertFalse(result);
        verify(stockDAOMock, never()).addStock(stock);
        System.out.println("Test Passed: testAddStock_ItemCodeDoesNotExist");
    }

    @Test
    public void testAddStock_ItemCodeExists() {
        // Setup mock return value
        when(stockDAOMock.itemCodeExists("item123")).thenReturn(true);
        when(stockDAOMock.addStock(any(Stock.class))).thenReturn(true);

        // Create a stock item
        Stock stock = new Stock(1, "item123", Date.valueOf("2025-01-01"), 10, Date.valueOf("2025-12-31"));

        // Test addStock method
        boolean result = stockService.addStock(stock);

        // Verify the result
        assertTrue(result);
        verify(stockDAOMock, times(1)).addStock(stock);
        System.out.println("Test Passed: testAddStock_ItemCodeExists");
    }

    @Test
    public void testMoveStockToShelf() {
        // Setup mock data
        Stock stock1 = new Stock(1, "item123", Date.valueOf("2025-01-01"), 10, Date.valueOf("2025-12-31"));
        Stock stock2 = new Stock(2, "item123", Date.valueOf("2025-01-01"), 5, Date.valueOf("2025-12-31"));
        when(stockDAOMock.getStockByItemCodeOrderedByExpiryDate("item123")).thenReturn(Arrays.asList(stock1, stock2));
        when(stockDAOMock.getTotalStockByItemCode("item123")).thenReturn(5); // assuming low stock after move

        Shelf existingShelf = new Shelf("item123", 5, "2025-12-31");
        when(shelfDAOMock.getShelfItemByItemCodeAndExpiryDate("item123", "2025-12-31")).thenReturn(existingShelf);

        // Test moveStockToShelf method
        boolean result = stockService.moveStockToShelf("item123", 10);

        // Verify the result
        assertTrue(result);
        verify(stockDAOMock, times(2)).updateStock(any(Stock.class));
        verify(shelfDAOMock, times(1)).updateShelfItem(any(Shelf.class));
        verify(stockDAOMock, times(1)).getTotalStockByItemCode("item123");
        verify(observerMock, times(1)).update("item123", 5);
        System.out.println("Test Passed: testMoveStockToShelf");
    }

    @Test
    public void testGetAllStock() {
        // Setup mock return value
        List<Stock> expectedStockList = createMockStockList();
        when(stockDAOMock.getAllStock()).thenReturn(expectedStockList);

        // Test getAllStock method
        List<Stock> actualStockList = stockService.getAllStock();

        // Verify the result
        assertEquals(expectedStockList, actualStockList);
        verify(stockDAOMock, times(1)).getAllStock();
        System.out.println("Test Passed: testGetAllStock");
        System.out.println("Actual Stock List: " + actualStockList);
    }

    private List<Stock> createMockStockList() {
        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock(1, "item123", Date.valueOf("2025-01-01"), 10, Date.valueOf("2025-12-31")));
        stockList.add(new Stock(2, "item456", Date.valueOf("2025-01-01"), 20, Date.valueOf("2025-12-31")));
        return stockList;
    }
}