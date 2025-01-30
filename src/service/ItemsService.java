package service;

import dao.ItemsDAO;
import model.Item;

public class ItemsService {
    ItemsDAO itemsDAO;

    public ItemsService() {
        itemsDAO = new ItemsDAO();
    }

    public boolean addItem(Item item) {
        if (itemsDAO.itemCodeExists(item.getItemCode())) {
            System.out.println("Item code already exists: " + item.getItemCode());
            return false;
        }
        return itemsDAO.addItem(item);
    }
}