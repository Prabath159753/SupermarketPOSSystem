package lk.superMarket.pos.bo.custom;

import lk.superMarket.pos.bo.SuperBO;
import lk.superMarket.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface CustomerIncomeBO extends SuperBO {
    ArrayList<Orders> getAllCustomerIncome() throws SQLException, ClassNotFoundException;
}
