package lk.superMarket.pos.bo.custom;

import lk.superMarket.pos.bo.SuperBO;
import lk.superMarket.pos.dto.CustomerDTO;
import lk.superMarket.pos.dto.ItemDTO;
import lk.superMarket.pos.dto.OrderDTO;
import lk.superMarket.pos.dto.ReportDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface OrderBO extends SuperBO {

    CustomerDTO searchCustomer(String s) throws SQLException, ClassNotFoundException;

    boolean ifCustomerExist(String id) throws SQLException, ClassNotFoundException;

    String generateNewCustomerID() throws SQLException, ClassNotFoundException;

    boolean addCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;

    String generateNewOrderId() throws SQLException, ClassNotFoundException;

    boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<ReportDTO> getAllOrders() throws SQLException, ClassNotFoundException;

}
