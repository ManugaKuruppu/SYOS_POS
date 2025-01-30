package service;

import dao.BillDAO;
import dao.BillItemDAO;
import dao.ItemDAO;
import model.Bill;
import model.BillItem;
import model.Item;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ReportService {
    BillDAO billDAO;
    BillItemDAO billItemDAO;
    ItemDAO itemDAO;

    public ReportService() {
        billDAO = new BillDAO();
        billItemDAO = new BillItemDAO();
        itemDAO = new ItemDAO();
    }

    // Report a: Total sale for a given day
    public void generateTotalSaleReport(Timestamp date) {
        List<Bill> bills = billDAO.getBillsByDate(date);
        Map<String, Integer> itemQuantities = new HashMap<>();
        Map<String, Double> itemRevenues = new HashMap<>();

        for (Bill bill : bills) {
            for (BillItem item : billItemDAO.getBillItemsByBillId(bill.getBillId())) {
                String itemCode = item.getItemCode();
                itemQuantities.put(itemCode, itemQuantities.getOrDefault(itemCode, 0) + item.getQuantity());
                itemRevenues.put(itemCode, itemRevenues.getOrDefault(itemCode, 0.0) + item.getTotalPrice());
            }
        }

        System.out.println("\nTotal Sale Report for " + date);
        System.out.println("Item Code | Item Name | Total Quantity | Total Revenue");
        System.out.println("-------------------------------------------------------");

        for (Map.Entry<String, Integer> entry : itemQuantities.entrySet()) {
            String itemCode = entry.getKey();
            int totalQuantity = entry.getValue();
            double totalRevenue = itemRevenues.get(itemCode);
            Item item = itemDAO.getItemByCode(itemCode);
            System.out.printf("%-10s | %-10s | %-15d | %-12.2f%n", itemCode, item.getItemName(), totalQuantity, totalRevenue);
        }
    }

    // Report b: Total number of items to be reshelved at the end of the day
    public void generateReshelveReport() {
        List<Item> items = itemDAO.getAllItems();

        System.out.println("\nReshelve Report");
        System.out.println("Item Code | Item Name | Quantity");
        System.out.println("------------------------------");

        for (Item item : items) {
            if (item.getQuantity() < 50) {
                System.out.printf("%-10s | %-10s | %-8d%n", item.getItemCode(), item.getItemName(), item.getQuantity());
            }
        }
    }

    // Report c: Reorder levels of stock
    public void generateReorderReport() {
        List<Item> items = itemDAO.getAllItems();

        System.out.println("\nReorder Report");
        System.out.println("Item Code | Item Name | Quantity");
        System.out.println("------------------------------");

        for (Item item : items) {
            if (item.getQuantity() < 50) {
                System.out.printf("%-10s | %-10s | %-8d%n", item.getItemCode(), item.getItemName(), item.getQuantity());
            }
        }
    }

    // Report d: Stock report
    public void generateStockReport() {
        List<Item> items = itemDAO.getAllItems();

        System.out.println("\nStock Report");
        System.out.println("Item Code | Item Name | Quantity | Price | Total Value");
        System.out.println("-------------------------------------------------------");

        for (Item item : items) {
            double totalValue = item.getQuantity() * item.getPrice();
            System.out.printf("%-10s | %-10s | %-8d | %-7.2f | %-11.2f%n", item.getItemCode(), item.getItemName(), item.getQuantity(), item.getPrice(), totalValue);
        }
    }

    // Report e: Bill report
    public void generateBillReport() {
        List<Bill> bills = billDAO.getAllBills();

        System.out.println("\nBill Report");
        System.out.println("Bill ID | Date | Total | Final Total | Cash Tendered | Change Amount");
        System.out.println("------------------------------------------------------");

        for (Bill bill : bills) {
            System.out.printf("%-7d | %-20s | %-6.2f | %-11.2f | %-14.2f | %-13.2f%n",
                    bill.getBillId(), bill.getBillDate(), bill.getTotal(), bill.getFinalTotal(), bill.getCashTendered(), bill.getChangeAmount());
        }
    }
}