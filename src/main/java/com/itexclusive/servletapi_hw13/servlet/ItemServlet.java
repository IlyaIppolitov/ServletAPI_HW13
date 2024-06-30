package com.itexclusive.servletapi_hw13.servlet;

import com.itexclusive.servletapi_hw13.dao.ItemDAO;
import com.itexclusive.servletapi_hw13.dao.BrandDAO;
import com.itexclusive.servletapi_hw13.model.Item;
import com.itexclusive.servletapi_hw13.model.Brand;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet(name = "ItemServlet", value = "/items")
public class ItemServlet extends HttpServlet {
    private ItemDAO itemDAO;
    private BrandDAO brandDAO;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ForTests", "postgres", "password");
            itemDAO = new ItemDAO(connection);
            brandDAO = new BrandDAO(connection);
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertItem(request, response);
                    break;
                case "delete":
                    deleteItem(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateItem(request, response);
                    break;
                default:
                    listItems(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listItems(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Item> listItem = itemDAO.getAllItems();
        request.setAttribute("listItem", listItem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/item/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Brand> listBrand = brandDAO.getAllBrands();
        request.setAttribute("listBrand", listBrand);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/item/form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Item existingItem = itemDAO.getItemById(id);
        List<Brand> listBrand = brandDAO.getAllBrands();
        request.setAttribute("listBrand", listBrand);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/item/form.jsp");
        request.setAttribute("item", existingItem);
        dispatcher.forward(request, response);
    }

    private void insertItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        int brandId = Integer.parseInt(request.getParameter("brand"));
        Brand brand = brandDAO.getBrandById(brandId);
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setBrand(brand);
        itemDAO.addItem(newItem);
        response.sendRedirect("items");
    }

    private void updateItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int brandId = Integer.parseInt(request.getParameter("brand"));
        Brand brand = brandDAO.getBrandById(brandId);
        Item item = new Item();
        item.setId(id);
        item.setName(name);
        item.setBrand(brand);
        itemDAO.updateItem(item);
        response.sendRedirect("items");
    }

    private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        itemDAO.deleteItem(id);
        response.sendRedirect("items");
    }
}
