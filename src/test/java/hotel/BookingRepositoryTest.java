package hotel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BookingRepositoryTest {

    @InjectMocks
    private BookingRepository bookingRepository;

    @Test
    void findAll_should_return_all_bookings() {
        // Given
        Room room = new Room("101", 5);
        bookingRepository.add(room, null, null, 5);

        // When
        List<Booking> bookings = bookingRepository.findAll();

        // Then
        assertThat(bookings.get(0).getRoom()).isEqualTo(room);
    }
}