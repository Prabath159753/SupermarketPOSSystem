package lk.superMarket.pos.dao.custom;

import lk.superMarket.pos.dao.CrudDAO;
import lk.superMarket.pos.entity.Item;

import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface ItemDAO extends CrudDAO<Item, String> {

    boolean ifItemExist(String itemCode) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

}
