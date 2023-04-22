package serialization;

import yt.sehrschlecht.classconfig.serialization.annotation.SerializableClass;
import yt.sehrschlecht.classconfig.serialization.annotation.Serialize;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@SerializableClass(constructorType = SerializableClass.ConstructorType.ALL_ARGS)
public class Address {
    private @Serialize String street;
    private @Serialize String city;
    private @Serialize String state;

    public Address(String street, String city, String state) {
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public String  getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
