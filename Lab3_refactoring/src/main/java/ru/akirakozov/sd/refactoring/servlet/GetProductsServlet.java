package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetProductsServlet extends AbstractServlet {

    public GetProductsServlet(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    protected void doSuccessfulGet(HttpServletRequest request, HTMLBuilder responseBuilder) {
        List<Product> allProducts = dbManager.getAllProducts();

        responseBuilder.addProductTable(allProducts);
    }
}
