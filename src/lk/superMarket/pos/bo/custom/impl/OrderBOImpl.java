package lk.superMarket.pos.bo.custom.impl;

import lk.superMarket.pos.bo.custom.OrderBO;
import lk.superMarket.pos.dao.DAOFactory;
import lk.superMarket.pos.dao.custom.CustomerDAO;
import lk.superMarket.pos.dao.custom.ItemDAO;
import lk.superMarket.pos.dao.custom.OrderDAO;
import lk.superMarket.pos.dao.custom.OrderDetailDAO;
import lk.superMarket.pos.db.DbConnection;
import lk.superMarket.pos.dto.CustomerDTO;
import lk.superMarket.pos.dto.ItemDTO;
import lk.superMarket.pos.dto.OrderDTO;
import lk.superMarket.pos.dto.OrderDetailDTO;
import lk.superMarket.pos.entity.Customer;
import lk.superMarket.pos.entity.Item;
import lk.superMarket.pos.entity.OrderDetails;
import lk.superMarket.pos.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class OrderBOImpl implements OrderBO {

    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    private final OrderDAO orderDAO = (OrderDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    private final OrderDetailDAO orderDetailsDAO = (OrderDetailDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

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

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        ArrayList<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(), item.getDiscount()));
        }
        return allItems;
    }

    @Override
    public boolean ifItemExist(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(code);
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemDTO(item.getItemCode(), item.getDescription(), item.getPackSize(), item.getUnitPrice(), item.getQtyOnHand(), item.getDiscount());
    }

    @Override
    public String generateNewOrderId() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewOrderId();
    }

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        /* Transaction */
        Connection connection = null;

        connection = DbConnection.getDbConnection().getConnection();
        boolean orderAvailable = orderDAO.ifOrderExist(dto.getOrderId());

        /* if order id already exist */
        if (orderAvailable) {
            return false;
        }

        connection.setAutoCommit(false);

        /* Add Order */
        Orders order = new Orders(dto.getOrderId(), dto.getCustomerId(), dto.getOrderDate(), dto.getOrderTime(),
                dto.getOrderTotal());
        boolean orderAdded = orderDAO.add(order);
        if (!orderAdded) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        for (OrderDetailDTO detail : dto.getOrderDetail()) {
            OrderDetails orderDetailDTO = new OrderDetails(dto.getOrderId(), detail.getItemCode(),
                    detail.getQty(), detail.getDiscount(), detail.getUnitPrice());
            boolean orderDetailsAdded = orderDetailsDAO.add(orderDetailDTO);
            if (!orderDetailsAdded) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            /* Search & Update Item */
            Item search = itemDAO.search(detail.getItemCode());
            search.setQtyOnHand(search.getQtyOnHand() - detail.getQty());
            boolean update = itemDAO.update(search);
            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        /* if every thing ok and Transaction complete */
        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }
}
