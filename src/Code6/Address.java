package Code6;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = 16L;
    private String zipCode; //Длина строки не должна быть больше 15, Поле не может быть null

    public Address(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String toString() {
        return "{address: " + zipCode + "}";
    }
}
