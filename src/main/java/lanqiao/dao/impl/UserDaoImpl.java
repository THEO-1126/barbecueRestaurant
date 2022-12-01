package lanqiao.dao.impl;

import lanqiao.bean.Users;
import lanqiao.connection.Conn;
import lanqiao.dao.UserDao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<Users> getAllUser() throws SQLException, IOException {
        //连接数据库
        Conn conn = new Conn();
        //执行SQL
        ResultSet rs = conn.getRsSet("SELECT * FROM users");
        //结果集
        List<Users> usersList = new ArrayList<>();
        //从结果集取数据
        while (rs.next()) {// 游标向下移动一次
            int id = rs.getInt("ID");
            String username = rs.getString("USERNAME");
            String password = rs.getString("PASSWORD");

            Users user = new Users(id, username, password);
            usersList.add(user);
        }
        return usersList;
    }

    /*
    测试用例
     */
    public static void main(String[] args) throws SQLException, IOException {
        UserDao userDao = new UserDaoImpl();
        System.out.println(userDao.getAllUser());
    }
}
