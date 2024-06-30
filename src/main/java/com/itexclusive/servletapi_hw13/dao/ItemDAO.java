package com.itexclusive.servletapi_hw13.dao;

import com.itexclusive.servletapi_hw13.model.Item;
import com.itexclusive.servletapi_hw13.model.Brand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private Connection connection;

    public ItemDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Item> getAllItems() throws SQLException {
        List<Item> items = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Item");
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setBrand(getBrandById(rs.getInt("brand_id")));
            items.add(item);
        }
        return items;
    }

    public Item getItemById(int id) throws SQLException {
        Item item = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Item WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setBrand(getBrandById(rs.getInt("brand_id")));
        }
        return item;
    }

    public void addItem(Item item) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Item (name, brand_id) VALUES (?, ?)");
        ps.setString(1, item.getName());
        ps.setInt(2, item.getBrand().getId());
        ps.executeUpdate();
    }

    public void updateItem(Item item) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Item SET name = ?, brand_id = ? WHERE id = ?");
        ps.setString(1, item.getName());
        ps.setInt(2, item.getBrand().getId());
        ps.setInt(3, item.getId());
        ps.executeUpdate();
    }

    public void deleteItem(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM Item WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    private Brand getBrandById(int id) throws SQLException {
        Brand brand = null;
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Brand WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            brand = new Brand();
            brand.setId(rs.getInt("id"));
            brand.setName(rs.getString("name"));
        }
        return brand;
    }
}
