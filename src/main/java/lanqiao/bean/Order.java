package lanqiao.bean;

public class Order {
    private int OrderId;
    private String commodity;
    private int number;
    private double price;

    public Order(int orderId, String commodity, int number, double price) {
        OrderId = orderId;
        this.commodity = commodity;
        this.number = number;
        this.price = price;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderId=" + OrderId +
                ", commodity='" + commodity + '\'' +
                ", number=" + number +
                ", price=" + price +
                '}';
    }
}
