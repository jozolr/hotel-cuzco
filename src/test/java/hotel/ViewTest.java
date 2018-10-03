package hotel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ViewTest {

    @InjectMocks
    private View view;

    @Mock
    private Console console;

    @Test
    void displayRooms_should_print_rooms() {
        // Given
        List<Room> rooms = asList(new Room("101", 2));

        // When
        view.displayRooms(rooms);

        // Then
        verify(console).print("LISTE DES CHAMBRES");
        verify(console).print("Chambre 101");
    }
}