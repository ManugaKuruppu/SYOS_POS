package service;

import dao.BillDAO;
import dao.BillItemDAO;
import dao.ItemDAO;
import model.Bill;
import model.BillItem;
import model.Item;
import dao.ShelfDAO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BillingService {
    private BillDAO billDAO;
    private BillItemDAO billItemDAO;
    private ItemDAO itemDAO;
    private ShelfDAO shelfDAO;

    public BillingService() {
        billDAO = new BillDAO();
        billItemDAO = new BillItemDAO();
        itemDAO = new ItemDAO();
        shelfDAO = new ShelfDAO();
    }

    // Step 1: Prepare the bill and calculate totals
    public Bill prepareBill(List<String> itemCodes, List<Integer> quantities) {
        List<BillItem> billItems = new ArrayList<>();
        double total = 0;
        double totalDiscount = 0;

        // Calculate the total price for each item and the overall total
        for (int i = 0; i < itemCodes.size(); i++) {
            String code = itemCodes.get(i);
            int quantity = quantities.get(i);
            Item item = itemDAO.getItemByCode(code);
            if (item == null) {
                continue;
            }
            double itemPrice = item.getPrice() * quantity;
            double discountAmount = itemPrice * (item.getDiscount() / 100);
            double itemTotal = itemPrice - discountAmount;

            total += itemTotal;
            totalDiscount += discountAmount;

            BillItem billItem = new BillItem();
            billItem.setItemCode(code);
            billItem.setItemName(item.getItemName()); // Set the item name
            billItem.setQuantity(quantity);
            billItem.setPrice(item.getPrice());
            billItem.setDiscount(item.getDiscount()); // Set the discount percentage
            billItem.setTotalPrice(itemTotal);
            billItems.add(billItem);
        }

        double finalTotal = total;

        // Create the Bill object
        Bill bill = new Bill();
        bill.setBillDate(new Timestamp(System.currentTimeMillis()));
        bill.setTotal(total);
        bill.setFinalTotal(finalTotal); // Ensure finalTotal is set
        bill.setBillItems(billItems);

        // Display the total price and final total to the user
        System.out.println("Total Price: " + total);
        System.out.println("Final Total after Discounts: " + finalTotal);

        return bill;
    }

    // Step 2: Complete the billing process
    public Bill completeBill(Bill bill, double cashTendered) {
        double finalTotal = bill.getFinalTotal();
        double changeAmount = cashTendered - finalTotal;

        bill.setCashTendered(cashTendered);
        bill.setChangeAmount(changeAmount);

        // Update shelf quantities
        if (!completeBilling(bill.getBillItems())) {
            return null; // Return null if updating shelf quantities fails
        }

        // Add the bill to the database
        if (billDAO.addBill(bill)) {
            // Add each bill item to the database
            for (BillItem billItem : bill.getBillItems()) {
                billItem.setBillId(bill.getBillId());
                billItemDAO.addBillItem(billItem);
            }
        }

        return bill;
    }

    private boolean completeBilling(List<BillItem> billItems) {
        for (BillItem billItem : billItems) {
            if (!shelfDAO.reduceShelfQuantity(billItem.getItemCode(), billItem.getQuantity())) {
                System.out.println("Failed to reduce shelf quantity for item code: " + billItem.getItemCode());
                return false;
            }
        }
        System.out.println("Shelf quantities updated successfully.");
        return true;
    }
}