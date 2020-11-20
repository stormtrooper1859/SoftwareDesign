package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.utils.HTMLBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract public class AbstractServlet extends HttpServlet {
    protected final DBManager dbManager;

    public AbstractServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    protected abstract void doSuccessfulGet(HttpServletRequest request, HTMLBuilder responseBuilder);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HTMLBuilder htmlBuilder = new HTMLBuilder();

        try {
            doSuccessfulGet(request, htmlBuilder);

            response.getWriter().println(htmlBuilder.build());
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            System.err.println(e.getMessage());

            response.getWriter().println("Internal server error");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
