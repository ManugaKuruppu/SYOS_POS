package controller;

import model.Stock;
import observer.StockObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.StockService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class StockControllerTest {

    @Mock
    private StockService stockService;

    @Mock
    private Scanner scanner;

    @Mock
    private StockObserver stockObserver;

    @InjectMocks
    private StockController stockController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stockController = new StockController(stockService, scanner, stockObserver);
    }

    @Test
    public void testManageStock() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("item123")
                .thenReturn("2025-01-01")
                .thenReturn("10")
                .thenReturn("2025-12-31");

        // Mock the service call
        when(stockService.addStock(any(Stock.class))).thenReturn(true);

        // Call the method under test
        stockController.manageStock();

        // Verify interactions with the mock objects
        verify(scanner, times(4)).nextLine();
        verify(stockService, times(1)).addStock(any(Stock.class));

        // Print information and show test passed
        System.out.println("Test Passed: testManageStock");
        System.out.println("Scanner inputs: item123, 2025-01-01, 10, 2025-12-31");
        System.out.println("Stock added successfully.");
    }

    @Test
    public void testMoveStockToShelf() {
        // Mock user input
        when(scanner.nextLine())
                .thenReturn("item123")
                .thenReturn("5");

        // Mock the service call
        when(stockService.moveStockToShelf(anyString(), anyInt())).thenReturn(true);

        // Call the method under test
        stockController.moveStockToShelf();

        // Verify interactions with the mock objects
        verify(scanner, times(2)).nextLine();
        verify(stockService, times(1)).moveStockToShelf(anyString(), anyInt());

        // Print information and show test passed
        System.out.println("Test Passed: testMoveStockToShelf");
        System.out.println("Scanner inputs: item123, 5");
        System.out.println("Stock moved to shelf successfully.");
    }

    @Test
    public void testViewStock() {
        // Mock the stock list
        List<Stock> mockStockList = createMockStockList();
        when(stockService.getAllStock()).thenReturn(mockStockList);

        // Call the method under test
        stockController.viewStock();

        // Verify interactions with the mock objects
        verify(stockService, times(1)).getAllStock();

        // Print information and show test passed
        System.out.println("Test Passed: testViewStock");
        System.out.println("Mock Stock List: " + mockStockList);
    }

    @Test
    public void testViewStockStatus() {
        // Call the method under test
        stockController.viewStockStatus();

        // Verify interactions with the mock objects
        verify(stockObserver, times(1)).displayLowStockItems();

        // Print information and show test passed
        System.out.println("Test Passed: testViewStockStatus");
        System.out.println("Low stock items displayed successfully.");
    }

    private List<Stock> createMockStockList() {
        List<Stock> stockList = new ArrayList<>();
        stockList.add(new Stock(1, "item123", Date.valueOf("2025-01-01"), 10, Date.valueOf("2025-12-31")));
        stockList.add(new Stock(2, "item456", Date.valueOf("2025-01-01"), 20, Date.valueOf("2025-12-31")));
        return stockList;
    }
}