package lk.superMarket.pos.dto;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class OrderDTO {
    private String orderId;
    private String customerId;
    private String customerName;
    private Date orderDate;
    private Time orderTime;
    private double orderTotal;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String customerId, String customerName, Date orderDate, Time orderTime, double orderTotal, List<OrderDetailDTO> orderDetail) {
        this.setOrderId(orderId);
        this.setCustomerId(customerId);
        this.setCustomerName(customerName);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setOrderTotal(orderTotal);
        this.setOrderDetail(orderDetail);
    }

    public OrderDTO(String orderId, String customerId, Date orderDate, Time orderTime, double orderTotal, List<OrderDetailDTO> orderDetail) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderTotal = orderTotal;
        this.orderDetail = orderDetail;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", orderTime=" + orderTime +
                ", orderTotal=" + orderTotal +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
