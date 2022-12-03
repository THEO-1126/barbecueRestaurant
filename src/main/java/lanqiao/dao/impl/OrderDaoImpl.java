package lanqiao.dao.impl;

import lanqiao.bean.Order;
import lanqiao.connection.Conn;
import lanqiao.dao.OrderDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class OrderDaoImpl implements OrderDao {
    /* 增加订单信息 */
    @Override
    public void addOrder(Order[] order) throws SQLException {
        //连接数据库
        Conn conn = new Conn();
        Connection connection= conn.getConnect();
        PreparedStatement pstmt;
        //执行SQL
        String sql="INSERT INTO `order`(id,commodity,number,purchase,sell,date) values(?,?,?,?,?,?)";
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        for(Order o:order){
            pstmt.setInt(1,o.getId()); // 插入第一个？符中
            pstmt.setString(2,o.getCommodity());
            pstmt.setInt(3,o.getNumber());
            pstmt.setDouble(4,o.getPurchase());
            pstmt.setDouble(5,o.getSell());
            pstmt.setDate(6,o.getDate());
            pstmt.executeUpdate();
        }
    }

}
