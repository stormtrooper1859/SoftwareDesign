package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        DBManager dbManager = new DBManager();

        if ("max".equals(command)) {
            List<String> result = dbManager.getMostExpensiveProduct();

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");

            for (String s : result) {
                response.getWriter().println(s);
            }

            response.getWriter().println("</body></html>");
        } else if ("min".equals(command)) {
            List<String> result = dbManager.getCheapestProduct();

            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            for (String s : result) {
                response.getWriter().println(s);
            }

            response.getWriter().println("</body></html>");
        } else if ("sum".equals(command)) {
            List<Integer> result = dbManager.getSumOfAllProducts();

            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            for (Integer s : result) {
                response.getWriter().println(s);
            }

            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            List<Integer> result = dbManager.getProductNumber();

            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            for (Integer s : result) {
                response.getWriter().println(s);
            }

            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
