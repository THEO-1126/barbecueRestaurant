package lanqiao.service.impl;

import lanqiao.bean.Users;
import lanqiao.dao.UserDao;
import lanqiao.dao.impl.UserDaoImpl;
import lanqiao.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();


    @Override
    public List<Users> getAllUser() throws SQLException, IOException {
        return userDao.getAllUser();
    }

    /*
    测试方法
     */
    public static void main(String[] args) throws SQLException, IOException {
        UserService userService = new UserServiceImpl();
        System.out.println(userService.getAllUser());
    }
}
