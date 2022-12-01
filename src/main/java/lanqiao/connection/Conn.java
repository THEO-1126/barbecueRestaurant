package lanqiao.connection;

import lanqiao.bean.Users;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*
连接类
 */
public class Conn {
    /*
    传入SQL语句，返回结果集
     */
    public ResultSet getRsSet(String sql) throws SQLException, IOException {
        //读配置文件
        InputStream inputStream = Conn.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        Connection conn = DriverManager.getConnection(url, user, password);// 表示数据库的连接对象
        PreparedStatement pstmt = conn.prepareStatement(sql);// 表示SQL语句的对象
        ResultSet rs = pstmt.executeQuery();//执行SQL

        return rs;
    }
}
