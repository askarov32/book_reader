package com.example.bookreader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/book_reader";
        String user = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, user, password);
    }
}
