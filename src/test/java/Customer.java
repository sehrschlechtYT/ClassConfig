import yt.sehrschlecht.javaconfig.serialization.annotation.SerializableClass;
import yt.sehrschlecht.javaconfig.serialization.annotation.Serialize;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@SerializableClass(constructorType = SerializableClass.ConstructorType.ALL_ARGS)
public class Customer {

    private @Serialize String name;
    private @Serialize String address;
    private @Serialize int age;

    public Customer(String name, String address, int age) {
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

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
