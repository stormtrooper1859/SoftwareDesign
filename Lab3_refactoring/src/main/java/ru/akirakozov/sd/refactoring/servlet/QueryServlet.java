package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QueryServlet extends AbstractServlet {
    public QueryServlet(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        HTMLBuilder htmlBuilder = new HTMLBuilder();

        if ("max".equals(command)) {
            List<Product> result = dbManager.getMostExpensiveProduct();
            htmlBuilder.addText("<h1>Product with max price: </h1>").addProductTable(result);
        } else if ("min".equals(command)) {
            List<Product> result = dbManager.getCheapestProduct();
            htmlBuilder.addText("<h1>Product with min price: </h1>").addProductTable(result);
        } else if ("sum".equals(command)) {
            List<Integer> result = dbManager.getSumOfAllProducts();
            htmlBuilder.addText("Summary price: ");

            for (Integer s : result) {
                htmlBuilder.addText(Integer.toString(s));
            }
        } else if ("count".equals(command)) {
            List<Integer> result = dbManager.getProductNumber();

            htmlBuilder.addText("Number of products: ");

            for (Integer s : result) {
                htmlBuilder.addText(Integer.toString(s));
            }
        } else {
            htmlBuilder.setDirectText("Unknown command: " + command);
        }

        response.getWriter().println(htmlBuilder.build());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
