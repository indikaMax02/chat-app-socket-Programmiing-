package server.dao;

import server.dao.custom.impl.UserDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    //factory method
    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case USER:
                return new UserDaoImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        USER
    }

}
