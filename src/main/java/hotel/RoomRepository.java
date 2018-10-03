package hotel;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class RoomRepository {
    private final List<Room> rooms;

    public RoomRepository() {
        this.rooms = asList(
                new Room("101", 2),
                new Room("102", 4),
                new Room("103", 3),
                new Room("201", 2),
                new Room("202", 2),
                new Room("203", 5),
                new Room("204", 1),
                new Room("205", 2),
                new Room("301", 2),
                new Room("302", 2),
                new Room("303", 3),
                new Room("304", 2));
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public Room getRoomByNumber(String roomNumber) {
        return this.rooms.stream()
                .filter(room -> room.getNumber().equals(roomNumber))
                .collect(Collectors.toList())
                .get(0);
    }
}
