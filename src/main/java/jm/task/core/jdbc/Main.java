package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        userDao.createUsersTable();
        userDao.saveUser("негр1","чорный1",(byte)1);
        userDao.saveUser("негр2","чорный2",(byte)2);
        userDao.saveUser("негр3","чорный3",(byte)3);
        userDao.saveUser("негр4","чорный4",(byte)4);
        System.out.println(userDao.getAllUsers());
        //userDao.createUsersTable();
        //userDao.dropUsersTable();
    }
}