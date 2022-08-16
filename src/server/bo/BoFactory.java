package server.bo;

import server.bo.custom.impl.UserBOImpl;


public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        USER
    }
}
