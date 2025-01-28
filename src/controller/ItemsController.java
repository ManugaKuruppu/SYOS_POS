package controller;

import model.Item;
import service.ItemsService;

import java.util.Scanner;

public class ItemsController {
    private ItemsService itemsService;
    private Scanner scanner;

    public ItemsController() {
        itemsService = new ItemsService();
        scanner = new Scanner(System.in);
    }

    public void addItem() {
        System.out.print("Enter item code: ");
        String itemCode = scanner.nextLine().trim();
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine().trim();
        System.out.print("Enter category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter discount: ");
        double discount = Double.parseDouble(scanner.nextLine());

        Item item = new Item();
        item.setItemCode(itemCode);
        item.setItemName(itemName);
        item.setCategoryId(categoryId);
        item.setPrice(price);
        item.setDiscount(discount);

        if (itemsService.addItem(item)) {
            System.out.println("Item added successfully!");
        } else {
            System.out.println("Failed to add item. Item code may already exist.");
        }
    }
}