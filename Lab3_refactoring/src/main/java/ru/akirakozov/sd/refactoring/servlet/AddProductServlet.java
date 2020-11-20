package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends AbstractServlet {

    public AddProductServlet(DBManager dbManager) {
        super(dbManager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        dbManager.addProduct(name, price);

        HTMLBuilder htmlBuilder = new HTMLBuilder();
        htmlBuilder.setDirectText("OK");

        response.getWriter().println(htmlBuilder.build());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
