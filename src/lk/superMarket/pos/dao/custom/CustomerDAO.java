package lk.superMarket.pos.dao.custom;

import lk.superMarket.pos.dao.CrudDAO;
import lk.superMarket.pos.entity.Customer;

import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface CustomerDAO extends CrudDAO<Customer, String> {

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

}
