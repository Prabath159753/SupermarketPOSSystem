package lk.superMarket.pos.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ReportDTO implements Serializable {
    private String orderId;
    private String custId;
    private Date date;
    private Time time;
    private double total;

    public ReportDTO() {
    }

    public ReportDTO(String orderId, String custId, Date date, Time time, double total) {
        this.setOrderId(orderId);
        this.setCustId(custId);
        this.setDate(date);
        this.setTime(time);
        this.setTotal(total);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "orderId='" + orderId + '\'' +
                ", custId='" + custId + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", total=" + total +
                '}';
    }
}
