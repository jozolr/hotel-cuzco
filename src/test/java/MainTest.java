import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MainTest {

    @Test
    public void test_plop(){
        Main main = new Main();

        assertThat(main.plop()).isTrue();
    }

}