package hotel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RoomRepositoryTest {

    @InjectMocks
    private RoomRepository roomRepository;

    @Test
    void getRoom_should_return_12_rooms() {
        // When
        List<Room> rooms = roomRepository.getRooms();
        // Then
        assertThat(rooms).hasSize(12);
    }

    @Test
    void getRoom_should_return_a_room_with_a_number_and_a_capacity() {
        // When
        List<Room> rooms = roomRepository.getRooms();

        // Then
        Room firstRoom = rooms.get(0);
        assertThat(firstRoom.getNumber()).isEqualTo("101");
        assertThat(firstRoom.getCapacity()).isEqualTo(2);
    }

    @Test
    void getRoomByNumber_should_return_the_room_with_the_good_number() {
        // Given

        // When
        Room room = roomRepository.getRoomByNumber("101");
        // Then
        assertThat(room.getNumber()).isEqualTo("101");
    }
}