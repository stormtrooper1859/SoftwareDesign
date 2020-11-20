package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.db.DBManager;

import javax.servlet.http.HttpServlet;

abstract public class AbstractServlet extends HttpServlet {
    protected final DBManager dbManager;

    public AbstractServlet(DBManager dbManager) {
        this.dbManager = dbManager;
    }
}
