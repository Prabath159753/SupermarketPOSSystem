package lk.superMarket.pos.bo.custom;

import lk.superMarket.pos.bo.SuperBO;
import lk.superMarket.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface ItemMoveBO extends SuperBO {
    ArrayList<OrderDetails> getAllOrderDetails() throws SQLException, ClassNotFoundException;
}
