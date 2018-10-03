package hotel;

import org.joda.time.DateTime;

public class Booking {
    private DateTime startDate;
    private DateTime endDate;
    private Room room;

    public Booking(Room room, DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public Room getRoom() {
        return room;
    }
}
