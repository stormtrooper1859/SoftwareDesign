package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServletRequest;

public class AddProductServlet extends AbstractServlet {

    public AddProductServlet(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    protected void doSuccessfulGet(HttpServletRequest request, HTMLBuilder responseBuilder) {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        dbManager.addProduct(name, price);

        responseBuilder.setDirectText("OK");
    }
}
