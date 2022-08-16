package server.bo.custom.impl;



import server.bo.custom.UserBO;
import server.dao.DAOFactory;
import server.dao.custom.UserDAO;
import server.dto.LoginUserDTO;
import server.dto.UserDTO;
import server.dto.UserDetailsDTO;
import server.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    private final UserDAO userDAO = (UserDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean registerUser(UserDTO userDTO) throws SQLException, ClassNotFoundException {
      return userDAO.add(new User(userDTO.getUserName(),userDTO.getPassword(),userDTO.getFullName(),userDTO.getEmail(),userDTO.getGender(),userDTO.getPhoneNo()));
    }

    @Override
    public UserDetailsDTO loginAccount(LoginUserDTO loginUserDTO) throws SQLException, ClassNotFoundException {
        User user = userDAO.existUser(loginUserDTO.getUserName());
        if (user!=null){
            if (user.getPassword().equals(loginUserDTO.getPassword())){
                return new UserDetailsDTO(user.getUserName());
            }
            return null;
        }
        return null;
    }
}
