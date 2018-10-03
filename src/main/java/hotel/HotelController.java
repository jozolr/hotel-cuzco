package hotel;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class HotelController {
    private final Hotel hotel;
    private final View view;
    private final Reader reader;
    private DateTime startDate = null;
    private DateTime endDate = null;
    private Integer capacity = 0;

    public HotelController(Hotel hotel, View view, Reader reader) {
        this.hotel = hotel;
        this.view = view;
        this.reader = reader;
    }

    public void listen() {

        boolean proceed;

        do {
            view.displayText("Combien d'hotes ?");
            capacity = getNumberOfGuestFromUser();

            view.displayText("Date de début ? Format JJ/MM/AAAA");
            startDate = getDateFromUser();

            view.displayText("Date de fin ? Format JJ/MM/AAAA");
            endDate = getDateFromUser();

            List<Room> availableRooms = hotel.findAvailableRooms(capacity, startDate, endDate);
            view.displayRooms(availableRooms);

            view.displayText("Quel numéro de chambre ?");
            String roomNumber = reader.next();
            hotel.makeReservation(roomNumber, startDate, endDate, capacity);

            view.displayText("Voulez-vous continuer ? O/N");
            proceed = reader.next().equals("O");

        } while (proceed);
    }

    private Integer getNumberOfGuestFromUser() {
        boolean incorrect;
        do {
            try {
                capacity = Integer.valueOf(reader.next());
                incorrect = false;
            } catch (NumberFormatException e) {
                view.displayText("Veuillez-rentrer un nombre d'hotes valide");
                incorrect = true;
            }
        } while (incorrect);
        return capacity;
    }

    private DateTime getDateFromUser() {
        boolean incorrect;
        do {
            try {
                startDate = toDate(reader.next());
                incorrect = false;
            } catch (IllegalArgumentException e) {
                view.displayText("Veuillez-rentrer une date valide");
                incorrect = true;
            }
        } while (incorrect);
        return startDate;
    }


    private static DateTime toDate(String dateInString) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        return DateTime.parse(dateInString, formatter);
    }
}
