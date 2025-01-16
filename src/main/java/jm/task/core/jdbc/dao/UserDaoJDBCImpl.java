package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        try (PreparedStatement prepared = connection.prepareStatement("CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(45), "
                + "lastName VARCHAR(45), "
                + "age TINYINT)");) {

            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement prepared = connection.prepareStatement("INSERT INTO users (name, lastName, age) " +
                "SELECT ?, ?, ? " +
                "WHERE NOT EXISTS (" +
                "SELECT 1 FROM users WHERE name = ? AND lastName = ? AND age = ?);")) {
            prepared.setString(1, name);
            prepared.setString(2, lastName);
            prepared.setByte(3, age);
            prepared.setString(4, name);
            prepared.setString(5, lastName);
            prepared.setByte(6, age);

            if (prepared.executeUpdate() > 0) {
                System.out.println("Пользователь с именем " + name + " добавлен в базу данных.");
            } else {
                System.out.println("Пользователь с именем " + name + " уже существует в базе данных.");
            }
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement prepared = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            prepared.setLong(1, id);
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"), resultSet.getString("lastName"), resultSet.getByte("age")));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (PreparedStatement checkStmt = connection.prepareStatement("SHOW TABLES LIKE 'users'");
             ResultSet rs = checkStmt.executeQuery()) {
            if (rs.next()) {
                try (PreparedStatement truncateStmt = connection.prepareStatement("TRUNCATE TABLE users")) {
                    truncateStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверке или очистке таблицы users: " + e.getMessage(), e);
        }
    }
}
