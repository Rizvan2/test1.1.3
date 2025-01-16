package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении соединения: " + e.getMessage(), e);
        }
    }
}

