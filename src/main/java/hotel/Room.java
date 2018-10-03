package hotel;

public class Room {

    private final String number;
    private final int capacity;

    public Room(String number, int capacity) {
        this.number = number;
        this.capacity = capacity;
    }

    public String getNumber() {
        return number;
    }

    public int getCapacity() {
        return capacity;
    }
}
