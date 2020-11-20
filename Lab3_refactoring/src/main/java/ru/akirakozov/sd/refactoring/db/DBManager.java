package ru.akirakozov.sd.refactoring.db;

import ru.akirakozov.sd.refactoring.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DBManager {
    private static final Function<ResultSet, List<Product>> TO_LIST = (resultSet -> {
        List<Product> result = new ArrayList<>();

        try {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                result.add(new Product(name, price));
            }
        } catch (SQLException throwables) {
            System.err.println(throwables.getMessage());
        }

        return result;
    });
    private static final Function<ResultSet, Integer> TO_INTEGER = (resultSet -> {
        Integer result = null;

        try {
            if (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            System.err.println(throwables.getMessage());
        }

        return result;
    });
    private final String dbUrl;

    public DBManager(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    private void executeUpdate(String sqlQuery) {
        try (Connection c = DriverManager.getConnection(dbUrl)) {
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sqlQuery);
            stmt.close();
        } catch (SQLException throwables) {
            System.err.println(throwables.getMessage());
        }
    }

    private <T> T executeQuery(String sqlQuery, Function<ResultSet, T> func) {
        T result = null;
        try (Connection c = DriverManager.getConnection(dbUrl)) {
            Statement stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(sqlQuery);
            result = func.apply(resultSet);
            stmt.close();
        } catch (SQLException throwables) {
            System.err.println(throwables.getMessage());
        }
        return result;
    }

    public void createTableIfNotExist() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";

        executeUpdate(sql);
    }

    public void addProduct(String name, long price) {
        String sql = "INSERT INTO PRODUCT " +
                "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
        executeUpdate(sql);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM PRODUCT";

        return executeQuery(sql, TO_LIST);
    }

    public List<Product> getMostExpensiveProduct() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";

        return executeQuery(sql, TO_LIST);
    }

    public List<Product> getCheapestProduct() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";

        return executeQuery(sql, TO_LIST);
    }

    public Integer getSumOfAllProducts() {
        String sql = "SELECT SUM(price) FROM PRODUCT";

        return executeQuery(sql, TO_INTEGER);
    }

    public Integer getProductNumber() {
        String sql = "SELECT COUNT(*) FROM PRODUCT";

        return executeQuery(sql, TO_INTEGER);
    }
}
