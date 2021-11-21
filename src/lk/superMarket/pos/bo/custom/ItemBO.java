package lk.superMarket.pos.bo.custom;

import lk.superMarket.pos.bo.SuperBO;
import lk.superMarket.pos.dto.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public interface ItemBO extends SuperBO {

    boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    boolean ifItemExist(String code) throws SQLException, ClassNotFoundException;

    String generateNewID() throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

    boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

}
