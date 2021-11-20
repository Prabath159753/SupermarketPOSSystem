package lk.superMarket.pos.bo;

import lk.superMarket.pos.bo.custom.impl.CustomerBOImpl;

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
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER
    }
}
