package lanqiao.dao;

import lanqiao.bean.Order;

import java.sql.SQLException;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public interface OrderDao {

    /* 增加订单信息 */
    void addOrder(Order[] order) throws SQLException;
}
