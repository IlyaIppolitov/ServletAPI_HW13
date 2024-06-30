package com.itexclusive.servletapi_hw13.servlet;

import com.itexclusive.servletapi_hw13.dao.BrandDAO;
import com.itexclusive.servletapi_hw13.model.Brand;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet(name = "BrandServlet", value = "/brands")
public class BrandServlet extends HttpServlet {
    private BrandDAO brandDAO;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ForTests", "postgres", "password");
            brandDAO = new BrandDAO(connection);
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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
                    insertBrand(request, response);
                    break;
                case "delete":
                    deleteBrand(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateBrand(request, response);
                    break;
                default:
                    listBrands(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listBrands(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Brand> listBrand = brandDAO.getAllBrands();
        request.setAttribute("listBrand", listBrand);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/brand/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/brand/form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Brand existingBrand = brandDAO.getBrandById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("views/brand/form.jsp");
        request.setAttribute("brand", existingBrand);
        dispatcher.forward(request, response);
    }

    private void insertBrand(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String name = request.getParameter("name");
        Brand newBrand = new Brand();
        newBrand.setName(name);
        brandDAO.addBrand(newBrand);
        response.sendRedirect("brands");
    }

    private void updateBrand(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(name);
        brandDAO.updateBrand(brand);
        response.sendRedirect("brands");
    }

    private void deleteBrand(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        brandDAO.deleteBrand(id);
        response.sendRedirect("brands");
    }
}
