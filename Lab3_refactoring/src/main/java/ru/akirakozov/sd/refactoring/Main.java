package ru.akirakozov.sd.refactoring;

import ru.akirakozov.sd.refactoring.db.DBManager;

public class Main {
    private static final int PORT = 8081;

    public static void main(String[] args) throws Exception {
        DBManager dbManager = new DBManager("jdbc:sqlite:test.db");
        dbManager.createTableIfNotExist();

        Server server = new Server(dbManager, PORT);

        server.start();
        server.join();
    }
}
