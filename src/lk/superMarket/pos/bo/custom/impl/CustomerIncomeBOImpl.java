package lk.superMarket.pos.bo.custom.impl;

import lk.superMarket.pos.bo.custom.CustomerIncomeBO;
import lk.superMarket.pos.dao.DAOFactory;
import lk.superMarket.pos.dao.custom.CustomerIncomeDAO;
import lk.superMarket.pos.entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class CustomerIncomeBOImpl implements CustomerIncomeBO {

    private final CustomerIncomeDAO customerIncomeDAO = (CustomerIncomeDAO) DAOFactory.getDAOFactory().getDAO
            (DAOFactory.DAOTypes.CUSTOMERINCOME);

    @Override
    public ArrayList<Orders> getAllCustomerIncome() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> allCustomerIncome = new ArrayList<>();
        ArrayList<Orders> all = customerIncomeDAO.getCustomerIncome();
        for (Orders order : all) {
            allCustomerIncome.add(new Orders(order.getOrderId(), order.getCustId(), order.getOrderDate(),
                    order.getOrderTime(), order.getTotal()));
        }
        return allCustomerIncome;
    }
}