package serialization;

import yt.sehrschlecht.classconfig.serialization.annotation.SerializableClass;
import yt.sehrschlecht.classconfig.serialization.annotation.Serialize;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@SerializableClass(constructorType = SerializableClass.ConstructorType.ALL_ARGS)
public class Customer {

    private @Serialize String name;
    private @Serialize Address address;
    private @Serialize int age;

    public Customer(String name, Address address, int age) {
        this.name = name;
        this.address = address;
        this.age = age;
    }

    //constructor used for deserialization
    public Customer() {

    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
