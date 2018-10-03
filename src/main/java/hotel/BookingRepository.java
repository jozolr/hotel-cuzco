package hotel;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    private List<Booking> bookings;

    public BookingRepository() {
        this.bookings = new ArrayList<>();
    }

    public void add(Room room, DateTime startDate, DateTime endDate, Integer numberOfGuests) {
        bookings.add(new Booking(room, startDate, endDate));
    }

    public List<Booking> findAll() {
        return this.bookings;
    }
}
