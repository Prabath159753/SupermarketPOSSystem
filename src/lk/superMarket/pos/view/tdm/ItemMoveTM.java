package lk.superMarket.pos.view.tdm;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ItemMoveTM implements Comparable<ItemMoveTM> {
    private String itemCode;
    private int orderQty;

    public ItemMoveTM() {
    }

    public ItemMoveTM(String itemCode, int orderQty) {
        this.itemCode = itemCode;
        this.orderQty = orderQty;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public String toString() {
        return "CustomTM{" +
                "itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                '}';
    }

    @Override
    public int compareTo(ItemMoveTM o) {
        return itemCode.compareTo(o.getItemCode());
    }
}
