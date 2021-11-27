package lk.superMarket.pos.dao.custom;

import lk.superMarket.pos.dao.CrudDAO;
import lk.superMarket.pos.entity.Orders;

import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface OrderDAO extends CrudDAO<Orders, String> {

    boolean ifOrderExist(String oid) throws SQLException, ClassNotFoundException;

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

}
