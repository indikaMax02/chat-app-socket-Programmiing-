package server.dao.custom.impl;

import server.dao.CrudUtil;
import server.dao.custom.UserDAO;
import server.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDaoImpl implements UserDAO {

    @Override
    public boolean add(server.entity.User user) throws SQLException, ClassNotFoundException {
          return CrudUtil.executeUpdate("INSERT INTO user (userName,pass,fullName,email,gender,phoneNo) VALUES (?,?,?,?,?,?)",user.getUserName(),user.getPassword(),user.getFullName(),user.getEmail(),user.getGender(),user.getPhoneNo());
    }


    @Override
    public User existUser(String userName) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("SELECT * FROM user WHERE userName=?", userName);
        rst.next();
        return new User(userName, rst.getString("pass"), rst.getString("fullName"),rst.getString("email"),rst.getString("gender"),rst.getString("phoneNo"));
    }
}
