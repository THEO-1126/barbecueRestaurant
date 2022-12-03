package lanqiao.dao.impl;

import lanqiao.bean.Commodity;
import lanqiao.bean.Order;
import lanqiao.connection.Conn;
import lanqiao.dao.CommodityDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class CommodityDaoImpl implements CommodityDao {

    /*
       获取所有的商品信息
     */
    @Override
    public List<Commodity> getAllCommodity() throws SQLException{
        //连接数据库
        Conn conn = new Conn();
        Connection connection= conn.getConnect();
        PreparedStatement pstmt;
        //执行SQL
        String sql="SELECT * From `commoditys`";
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        ResultSet rs = pstmt.executeQuery();
        List<Commodity> CommodityList = new ArrayList<>();
        //从结果集取数据
        while (rs.next()) {// 游标向下移动一次
            int id = rs.getInt("ID");
            String name = rs.getString("name");
            int number = rs.getInt("number");
            int purchase = rs.getInt("purchase");
            int sell = rs.getInt("sell");
            Commodity commodity=new Commodity(id,name,number,purchase,sell);
            CommodityList.add(commodity);
        }
        return CommodityList;
    }

    /*
        计算商品的数量
     */
    public int getCommodityNumber() throws SQLException{
        //连接数据库
        Conn conn = new Conn();
        Connection connection= conn.getConnect();
        PreparedStatement pstmt;
        //执行SQL
        String sql="SELECT COUNT(*) CommodityNumber FROM `commoditys`";
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        ResultSet rs = pstmt.executeQuery();
        int CommodityNumber=0;
        if(rs.next()){
            CommodityNumber=rs.getInt("CommodityNumber");
        }
        return CommodityNumber;
    }

    /*
       获取商品名字，数量，单价,售价
     */
    public Object[][] getCommoditySellPurchaser() throws SQLException{
        int size=getCommodityNumber(); //获取商品的数量
        Object [][]data = new Object[size][4];
        List<Commodity> list=getAllCommodity();
        int i=0;
        for (Commodity commodity : list) {
            data[i][0]=commodity.getName();
            data[i][1]=commodity.getSell();
            data[i][2]=commodity.getNumber();
            data[i][3]=commodity.getPurchase();
            i++;
        }
        return data;
    }

    /*
    * 减去已下单的商品数量
    * */
    public void updateCommoditynumber(Order[] orders) throws SQLException{
        //连接数据库
        Conn conn = new Conn();
        Connection connection= conn.getConnect();
        PreparedStatement pstmt;
        //执行SQL
        String sql="UPDATE `commoditys` set number=number-? WHERE name =?";
        pstmt = connection.prepareStatement(sql);// pstmt和sql语句做关联
        for(Order o:orders){
            pstmt.setInt(1,o.getNumber());
            pstmt.setString(2,o.getCommodity());
            pstmt.executeUpdate();
        }
    }
}
