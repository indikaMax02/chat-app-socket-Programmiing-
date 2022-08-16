package server.bo.custom;


import server.bo.SuperBO;
import server.dto.LoginUserDTO;
import server.dto.UserDTO;
import server.dto.UserDetailsDTO;

import java.sql.SQLException;

public interface UserBO extends SuperBO {

    public boolean registerUser(UserDTO userDTO) throws SQLException, ClassNotFoundException;
    public UserDetailsDTO loginAccount(LoginUserDTO loginUserDTO) throws SQLException, ClassNotFoundException;

}
