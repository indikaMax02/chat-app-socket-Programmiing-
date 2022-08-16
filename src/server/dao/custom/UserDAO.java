package server.dao.custom;

import server.dao.CrudDAO;
import server.entity.User;

import java.sql.SQLException;


public interface UserDAO extends CrudDAO<User, String> {
      public User existUser(String userName) throws SQLException, ClassNotFoundException;
}
