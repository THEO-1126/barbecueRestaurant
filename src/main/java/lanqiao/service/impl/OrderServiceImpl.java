package lanqiao.service.impl;

import lanqiao.bean.Order;
import lanqiao.dao.OrderDao;
import lanqiao.dao.impl.OrderDaoImpl;
import lanqiao.service.OrderService;

import java.sql.SQLException;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=new OrderDaoImpl();

    // 将订单表加入数据库
    @Override
    public void addOrder(Order[] order) throws SQLException {
        orderDao.addOrder(order);
    }
}
