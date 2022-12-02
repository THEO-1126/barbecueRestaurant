package lanqiao.dao;

import lanqiao.bean.Commodity;
import lanqiao.bean.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @Author 李冰冰
 * @Date 2022/12/02
 * @Version 17.0.5
 */

public interface CommodityDao {
    /* 获取所有的商品信息 */
    List<Commodity> getAllCommodity() throws SQLException,IOException;

    /* sortName：商品类别名称 根据sortName计算各商品类别的数量 */
    int getSortNumber(String sortName) throws SQLException, IOException;

    /* 根据sortName商品类别，获取商品名字，单价，图片地址 */
    Object[][] getSortpicAdress(String sortName) throws SQLException,IOException;
}
