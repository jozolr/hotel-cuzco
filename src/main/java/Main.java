import hotel.*;

public class Main {
    public static void main(String args[]) {
        RoomRepository roomRepository = new RoomRepository();
        BookingRepository bookingRepository = new BookingRepository();
        Hotel hotel = new Hotel(roomRepository, bookingRepository);

        View view = new View(new Console());
        HotelController hotelController = new HotelController(hotel, view, new Reader());
        hotelController.listen();
    }
}
