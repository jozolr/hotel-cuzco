package hotel;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Hotel {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public Hotel(RoomRepository roomRepository,
                 BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Room> findAvailableRooms(Integer capacity, DateTime startDate, DateTime endDate) {
        List<Room> bookedRooms = getBookedRooms(startDate, endDate);
        return roomRepository.getRooms()
                .stream()
                .filter(hasEnoughCapacity(capacity))
                .filter(isNotBooked(bookedRooms))
                .collect(toList());
    }


    public void makeReservation(String roomNumber, DateTime startDate, DateTime endDate, Integer numberOfGuests) {
        Room room = roomRepository.getRoomByNumber(roomNumber);
        bookingRepository.add(room, startDate, endDate, numberOfGuests);
    }

    private List<Room> getBookedRooms(DateTime startDate, DateTime endDate) {
        List<Booking> bookings = bookingRepository.findAll();
        List<Booking> bookingsOnDate = bookings.stream()
                .filter(isOnDates(startDate, endDate))
                .collect(Collectors.toList());
        return retrieveRoomsFromBookings(bookingsOnDate);
    }

    private List<Room> retrieveRoomsFromBookings(List<Booking> bookings) {
        return bookings.stream()
                .map(Booking::getRoom)
                .collect(toList());
    }

    private Predicate<Room> hasEnoughCapacity(Integer capacity) {
        return room -> room.getCapacity() >= capacity;
    }

    private Predicate<Room> isNotBooked(List<Room> rooms) {
        return room -> !rooms.contains(room);
    }

    private Predicate<Booking> isOnDates(DateTime startDate, DateTime endDate) {
        return booking -> {
            Interval intervalWanted = new Interval(startDate, endDate);
            Interval intervalBooking = new Interval(booking.getStartDate(), booking.getEndDate());
            return intervalWanted.overlaps(intervalBooking);
        };
    }
}
