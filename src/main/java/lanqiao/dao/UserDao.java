package lanqiao.dao;

import lanqiao.bean.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    List<Users> getAllUser() throws SQLException, IOException;
}
