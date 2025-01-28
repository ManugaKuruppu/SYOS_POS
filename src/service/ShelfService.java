package service;

import dao.ShelfDAO;
import model.ShelfItem;

import java.util.List;

public class ShelfService {
    private ShelfDAO shelfDAO;

    public ShelfService() {
        shelfDAO = new ShelfDAO();
    }

    public List<ShelfItem> getAllShelfItems() {
        return shelfDAO.getAllShelfItems();
    }
}