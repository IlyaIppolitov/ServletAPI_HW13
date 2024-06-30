package com.itexclusive.servletapi_hw13.dao;

import com.itexclusive.servletapi_hw13.model.Brand;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BrandDAO {
    private Connection connection;

    public BrandDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Brand> getAllBrands() throws SQLException {
        List<Brand> brands = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM Brand");
        while (rs.next()) {
            Brand brand = new Brand();
            brand.setId(rs.getInt("id"));
            brand.setName(rs.getString("name"));
            brands.add(brand);
        }
        return brands;
    }

    public Brand getBrandById(int id) throws SQLException {
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

    public void addBrand(Brand brand) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Brand (name) VALUES (?)");
        ps.setString(1, brand.getName());
        ps.executeUpdate();
    }

    public void updateBrand(Brand brand) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Brand SET name = ? WHERE id = ?");
        ps.setString(1, brand.getName());
        ps.setInt(2, brand.getId());
        ps.executeUpdate();
    }

    public void deleteBrand(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM Brand WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
