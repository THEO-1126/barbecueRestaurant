package lanqiao.bean;

/**
 * @Author 钟荣钊
 * @Date 2022/12/02
 * @Version 1.0
 */
public class Commodity {
    private int id;
    private String name;
    private int number;
    private double purchase;
    private double sell;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    private String picAddress;
    private String unit;

    public Commodity(int id, String name, int number, double purchase, double sell) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.purchase = purchase;
        this.sell = sell;
        this.picAddress=picAddress;
        this.unit=unit;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", purchase=" + purchase +
                ", sell=" + sell +
                '}';
    }
}
