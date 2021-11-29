package lk.superMarket.pos.dao.custom;

import lk.superMarket.pos.dao.CrudDAO;
import lk.superMarket.pos.entity.OrderDetails;
import lk.superMarket.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface ItemMoveDAO extends CrudDAO<Orders, String> {
    ArrayList<OrderDetails> getItemMovable() throws SQLException, ClassNotFoundException;
}
