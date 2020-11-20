package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServletRequest;

public class QueryServlet extends AbstractServlet {
    public QueryServlet(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    protected void doSuccessfulGet(HttpServletRequest request, HTMLBuilder responseBuilder) {
        String command = request.getParameter("command");

        switch (command) {
            case "max":
                responseBuilder.addText("<h1>Product with max price: </h1>")
                        .addProductTable(dbManager.getMostExpensiveProduct());
                break;
            case "min":
                responseBuilder.addText("<h1>Product with min price: </h1>")
                        .addProductTable(dbManager.getCheapestProduct());
                break;
            case "sum":
                responseBuilder.addText("Summary price: ")
                        .addNumber(dbManager.getSumOfAllProducts());
                break;
            case "count":
                responseBuilder.addText("Number of products: ")
                        .addNumber(dbManager.getProductNumber());
                break;
            default:
                responseBuilder.setDirectText("Unknown command: " + command);
                break;
        }
    }
}
