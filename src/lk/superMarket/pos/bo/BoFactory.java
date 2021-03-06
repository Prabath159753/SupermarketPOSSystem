package lk.superMarket.pos.bo;

import lk.superMarket.pos.bo.custom.impl.*;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

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
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case ORDER:
                return new OrderBOImpl();
            case CUSTOMERINCOME:
                return new CustomerIncomeBOImpl();
            case ITEMMOVE:
                return new ItemMoveBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, ORDER, CUSTOMERINCOME, ITEMMOVE
    }
}
