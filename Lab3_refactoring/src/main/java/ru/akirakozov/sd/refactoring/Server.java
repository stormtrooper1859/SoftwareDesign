package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.db.DBManager;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

public class Server {
    private final org.eclipse.jetty.server.Server server;
    private final DBManager dbManager;

    public Server(DBManager dbManager, int port) {
        this.dbManager = dbManager;
        server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(contextSetup());
    }

    private ServletContextHandler contextSetup() {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        context.addServlet(new ServletHolder(new AddProductServlet(dbManager)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(dbManager)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(dbManager)), "/query");

        return context;
    }

    public void start() throws Exception {
        server.start();
    }

    public void join() throws InterruptedException {
        server.join();
    }
}
