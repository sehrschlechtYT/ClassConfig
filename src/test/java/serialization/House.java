package serialization;

import java.awt.*;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class House {
    private Address address;
    private Color color;
    private boolean hasGarage;
    private int numberOfRooms;

    public House(Address address, Color color, boolean hasGarage, int numberOfRooms) {
        this.address = address;
        this.color = color;
        this.hasGarage = hasGarage;
        this.numberOfRooms = numberOfRooms;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean hasGarage() {
        return hasGarage;
    }

    public void setGarage(boolean hasGarage) {
        this.hasGarage = hasGarage;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }
}
