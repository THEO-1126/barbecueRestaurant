package lanqiao.bean;

import java.sql.Date;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public class Order {
    private int id;
    private String commodity;
    private int number;
    private double purchase;
    private double sell;
    Date date;

    public Order(int id, String commodity, int number, double purchase, double sell, Date date) {
        this.id = id;
        this.commodity = commodity;
        this.number = number;
        this.purchase = purchase;
        this.sell = sell;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getPurchase() {
        return purchase;
    }

    public void setPurchase(double purchase) {
        this.purchase = purchase;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
