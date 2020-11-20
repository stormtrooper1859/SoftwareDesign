package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    public void createTableIfNotExist() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    public void addProduct(String name, long price) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = "INSERT INTO PRODUCT " +
                        "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> result = new ArrayList<>();

        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    result.add(new Product(name, price));
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<Product> getMostExpensiveProduct() {
        List<Product> result = new ArrayList<>();

        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    result.add(new Product(name, price));
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<Product> getCheapestProduct() {
        List<Product> result = new ArrayList<>();

        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");

                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    result.add(new Product(name, price));
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<Integer> getSumOfAllProducts() {
        List<Integer> result = new ArrayList<>();

        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");

                if (rs.next()) {
                    result.add(rs.getInt(1));
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public List<Integer> getProductNumber() {
        List<Integer> result = new ArrayList<>();

        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");

                if (rs.next()) {
                    result.add(rs.getInt(1));
                }

                rs.close();
                stmt.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
