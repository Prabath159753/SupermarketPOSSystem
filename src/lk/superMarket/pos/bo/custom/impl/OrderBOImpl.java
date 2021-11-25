package lk.superMarket.pos.bo.custom.impl;

import lk.superMarket.pos.bo.custom.OrderBO;
import lk.superMarket.pos.dao.DAOFactory;
import lk.superMarket.pos.dao.custom.CustomerDAO;
import lk.superMarket.pos.dto.CustomerDTO;
import lk.superMarket.pos.entity.Customer;

import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class OrderBOImpl implements OrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(s);
        return new CustomerDTO(c.getId(), c.getTitle(), c.getName(), c.getAddress(), c.getCity(), c.getProvince(), c.getPostalCode());
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public String generateNewCustomerID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(), dto.getTitle(), dto.getName(), dto.getAddress(),
                dto.getCity(), dto.getProvince(), dto.getPostalCode()));
    }
}
