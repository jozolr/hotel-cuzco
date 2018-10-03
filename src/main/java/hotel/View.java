package hotel;

import java.util.List;

public class View {
    private final Console console;

    public View(Console console) {
        this.console = console;
    }

    public void displayRooms(List<Room> rooms) {
        String header = "LISTE DES CHAMBRES";
        console.print(header);
        rooms.forEach(room -> console.print("Chambre " + room.getNumber()));
    }

    public void displayText(String text) {
        console.print(text);
    }
}
