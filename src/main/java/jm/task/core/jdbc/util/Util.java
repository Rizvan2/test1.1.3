package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Util {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "root");
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении соединения: " + e.getMessage(), e);
        }
    }
}

