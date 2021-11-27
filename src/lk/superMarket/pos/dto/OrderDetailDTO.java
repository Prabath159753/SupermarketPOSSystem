package lk.superMarket.pos.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class OrderDetailDTO implements Serializable {
    private String orderId;
    private String itemCode;
    private double discount;
    private int qty;
    private BigDecimal unitPrice;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String orderId, String itemCode, double discount, int qty, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.discount = discount;
        this.qty = qty;
        this.setUnitPrice(unitPrice);
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", discount=" + discount +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
