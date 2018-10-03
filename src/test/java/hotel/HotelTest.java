package hotel;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HotelTest {
    @InjectMocks
    private Hotel hotel;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    private static final Room ROOM_ONE = new Room("101", 2);
    private static final Room ROOM_TWO = new Room("102", 3);
    private static final Room ROOM_THREE = new Room("103", 4);
    private static final Room ROOM_FOUR = new Room("104", 5);
    private static final List<Room> ALL_ROOMS = asList(
            ROOM_ONE,
            ROOM_TWO,
            ROOM_THREE,
            ROOM_FOUR
    );

    private static final DateTime JANUARY_1 = new DateTime(2019, 1, 1, 0, 0);
    private static final DateTime JANUARY_2 = new DateTime(2019, 1, 2, 0, 0);
    private static final DateTime JANUARY_3 = new DateTime(2019, 1, 3, 0, 0);
    private static final DateTime JANUARY_4 = new DateTime(2019, 1, 4, 0, 0);
    private static final DateTime JANUARY_5 = new DateTime(2019, 1, 5, 0, 0);
    private static final DateTime JANUARY_6 = new DateTime(2019, 1, 6, 0, 0);
    private static final DateTime JANUARY_7 = new DateTime(2019, 1, 7, 0, 0);

    @Test
    void findAvailableRooms_should_return_all_rooms_for_one_person() {
        // Given
        List<Room> allRooms = asList(
                ROOM_ONE,
                ROOM_TWO
        );
        given(roomRepository.getRooms()).willReturn(allRooms);

        // When
        List<Room> rooms = hotel.findAvailableRooms(1, null, null);

        // Then
        assertThat(rooms).isEqualTo(asList(ROOM_ONE, ROOM_TWO));
    }

    @Test
    void findAvailableRooms_should_return_rooms_for_4_or_more() {
        given(roomRepository.getRooms()).willReturn(ALL_ROOMS);

        // When
        List<Room> rooms = hotel.findAvailableRooms(4, null, null);

        // Then
        assertThat(rooms).isEqualTo(asList(ROOM_THREE, ROOM_FOUR));
    }

    @Test
    void findAvailableRooms_should_get_the_bookings_from_repository() {
        // When
        hotel.findAvailableRooms(1, null, null);

        // Then
        verify(bookingRepository).findAll();
    }

    @Test
    void findAvailableRooms_should_return_all_rooms_when_no_bookings() {
        // Given
        given(roomRepository.getRooms()).willReturn(ALL_ROOMS);
        given(bookingRepository.findAll()).willReturn(emptyList());

        // When
        List<Room> availableRooms = hotel.findAvailableRooms(1, JANUARY_1, JANUARY_2);

        // Then
        assertThat(availableRooms).isEqualTo(ALL_ROOMS);
    }

    @Test
    void findAvailableRooms_should_not_return_a_room_when_booking_is_inside_overlap_date() {
        // Given
        given(roomRepository.getRooms()).willReturn(ALL_ROOMS);
        Booking bookingOne = new Booking(ROOM_ONE, JANUARY_1, JANUARY_5);
        Booking bookingTwo = new Booking(ROOM_TWO, JANUARY_6, JANUARY_7);
        given(bookingRepository.findAll()).willReturn(asList(bookingOne, bookingTwo));

        // When
        List<Room> availableRooms = hotel.findAvailableRooms(1, JANUARY_2, JANUARY_3);

        // Then
        assertThat(availableRooms).isEqualTo(asList(ROOM_TWO, ROOM_THREE, ROOM_FOUR));
    }

    @Test
    void findAvailableRooms_should_not_return_a_room_when_booking_is_outside_overlap_date() {
        // Given
        given(roomRepository.getRooms()).willReturn(ALL_ROOMS);
        Booking bookingOne = new Booking(ROOM_ONE, JANUARY_3, JANUARY_4);
        Booking bookingTwo = new Booking(ROOM_TWO, JANUARY_6, JANUARY_7);
        given(bookingRepository.findAll()).willReturn(asList(bookingOne, bookingTwo));

        // When
        List<Room> availableRooms = hotel.findAvailableRooms(1, JANUARY_1, JANUARY_5);

        // Then
        assertThat(availableRooms).isEqualTo(asList(ROOM_TWO, ROOM_THREE, ROOM_FOUR));
    }

    @Test
    void findAvailableRooms_should_not_return_a_room_when_booking_is_partial_overlap_date() {
        // Given
        given(roomRepository.getRooms()).willReturn(ALL_ROOMS);
        Booking bookingOne = new Booking(ROOM_ONE, JANUARY_2, JANUARY_4);
        Booking bookingTwo = new Booking(ROOM_TWO, JANUARY_6, JANUARY_7);
        given(bookingRepository.findAll()).willReturn(asList(bookingOne, bookingTwo));

        // When
        List<Room> availableRooms = hotel.findAvailableRooms(1, JANUARY_1, JANUARY_3);

        // Then
        assertThat(availableRooms).isEqualTo(asList(ROOM_TWO, ROOM_THREE, ROOM_FOUR));
    }

    @Test
    void makeReservation_should_register_a_reservation_with_provided_information() {
        // Given
        given(roomRepository.getRoomByNumber("101")).willReturn(ROOM_ONE);

        // When
        hotel.makeReservation("101", JANUARY_1, JANUARY_2, 2);

        // Then
        verify(bookingRepository).add(ROOM_ONE, JANUARY_1, JANUARY_2, 2);
    }
}