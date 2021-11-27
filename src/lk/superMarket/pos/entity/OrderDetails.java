package lk.superMarket.pos.entity;

import java.math.BigDecimal;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class OrderDetails {
    private String orderId;
    private String itemCode;
    private int orderQty;
    private double discount;
    private BigDecimal unitPrice;

    public OrderDetails(String orderId, String itemCode, int orderQty, int discount, int i) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
        this.unitPrice = BigDecimal.valueOf(i);
    }

    public OrderDetails(String orderId, String itemCode, int orderQty, double discount, BigDecimal unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.orderQty = orderQty;
        this.discount = discount;
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

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", orderQty=" + orderQty +
                ", discount=" + discount +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
