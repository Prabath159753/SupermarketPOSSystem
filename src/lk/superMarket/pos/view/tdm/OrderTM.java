package lk.superMarket.pos.view.tdm;

import java.sql.Date;
import java.sql.Time;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class OrderTM {
    private String orderId;
    private Date orderDate;
    private Time orderTime;
    private String customerId;
    private double orderTotal;

    public OrderTM() {
    }

    public OrderTM(String orderId, Date orderDate, Time orderTime, String customerId, double orderTotal) {
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCustomerId(customerId);
        this.setOrderTotal(orderTotal);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Time getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Time orderTime) {
        this.orderTime = orderTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", customerId='" + customerId + '\'' +
                ", orderTotal=" + orderTotal +
                '}';
    }
}

