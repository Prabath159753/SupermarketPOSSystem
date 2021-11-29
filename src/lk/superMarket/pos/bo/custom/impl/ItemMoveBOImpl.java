package lk.superMarket.pos.bo.custom.impl;

import lk.superMarket.pos.bo.custom.ItemMoveBO;
import lk.superMarket.pos.dao.DAOFactory;
import lk.superMarket.pos.dao.custom.ItemMoveDAO;
import lk.superMarket.pos.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ItemMoveBOImpl implements ItemMoveBO {

    private final ItemMoveDAO itemMoveDAO = (ItemMoveDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEMMOVE);

    @Override
    public ArrayList<OrderDetails> getAllOrderDetails() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> allOrderDetails = new ArrayList<>();
        ArrayList<OrderDetails> all = itemMoveDAO.getItemMovable();
        for (OrderDetails orderDetails : all) {
            allOrderDetails.add(new OrderDetails(orderDetails.getOrderId(), orderDetails.getItemCode(), orderDetails.getOrderQty(),
                    orderDetails.getDiscount(), orderDetails.getUnitPrice()));
        }
        return allOrderDetails;
    }
}
