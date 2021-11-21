package lk.superMarket.pos.bo.custom.impl;

import lk.superMarket.pos.bo.custom.ItemBO;
import lk.superMarket.pos.dao.DAOFactory;
import lk.superMarket.pos.dao.custom.ItemDAO;
import lk.superMarket.pos.dto.ItemDTO;
import lk.superMarket.pos.entity.Item;

import java.sql.SQLException;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(dto.getItemCode(), dto.getDescription(), dto.getPackSize(), dto.getUnitPrice(), dto.getQtyOnHand(), dto.getDiscount()));
    }

    @Override
    public boolean ifItemExist(String itemCode) throws SQLException, ClassNotFoundException {
        return itemDAO.ifItemExist(itemCode);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }
}
