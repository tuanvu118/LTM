package TCP;

import java.io.Serializable;

public class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;
    private int  id, quantity;
    private String code,name;

    public Laptop(int id, int quantity, String code, String name) {
        this.id = id;
        this.quantity = quantity;
        this.code = code;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
