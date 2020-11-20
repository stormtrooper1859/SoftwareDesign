package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> allProducts = dbManager.getAllProducts();

        HTMLBuilder htmlBuilder = new HTMLBuilder();

        htmlBuilder.addProductTable(allProducts);

        response.getWriter().println(htmlBuilder.build());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
