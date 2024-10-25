import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Mockito.*;

public class PlayerTest{
    private Player player;
    private static Ship mockShip1;
    private static Ship mockShip2;
    private static Ship mockShip3;
    private static Ship mockShip4;
    private static Ship mockShip5;
    private static Grid mockPlayerGrid;
    private static Grid mockOppGrid;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Mock the Ship and Grid classes
        mockShip1 = mock(Ship.class);
        mockShip2 = mock(Ship.class);
        mockShip3 = mock(Ship.class);
        mockShip4 = mock(Ship.class);
        mockShip5 = mock(Ship.class);

        mockPlayerGrid = mock(Grid.class);
        mockOppGrid = mock(Grid.class);


    }
    @AfterAll
    static void tearDown() throws Exception {

    }


}
