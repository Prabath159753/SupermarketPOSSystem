package lk.superMarket.pos.dao;

import lk.superMarket.pos.dao.custom.impl.CustomerDAOImpl;
import lk.superMarket.pos.dao.custom.impl.ItemDAOImpl;

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
            case ITEM:
                return new ItemDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER, ITEM
    }
}
