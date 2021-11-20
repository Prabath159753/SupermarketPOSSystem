package lk.superMarket.pos.bo.custom.impl;

import lk.superMarket.pos.bo.custom.CustomerBO;
import lk.superMarket.pos.dao.DAOFactory;
import lk.superMarket.pos.dao.custom.CustomerDAO;
import lk.superMarket.pos.dto.CustomerDTO;
import lk.superMarket.pos.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class CustomerBOImpl implements CustomerBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getId(), dto.getTitle(), dto.getName(), dto.getAddress(),
                dto.getCity(), dto.getProvince(), dto.getPostalCode()));
    }

    @Override
    public boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.ifCustomerExist(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

}

