package lk.superMarket.pos.dao;

import lk.superMarket.pos.dao.custom.impl.CustomerDAOImpl;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

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

    public SuperDAO getDAO(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER
    }
}
