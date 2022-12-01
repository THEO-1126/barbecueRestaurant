package lanqiao.service;

import lanqiao.bean.Users;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    List<Users> getAllUser() throws SQLException, IOException;
}
