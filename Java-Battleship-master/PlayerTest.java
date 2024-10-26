import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlayerTest{
    private static Player player;
    private static Ship mockShip1, mockShip2 ,mockShip3, mockShip4, mockShip5;
    private static Grid mockPlayerGrid, mockOppGrid;

    @BeforeAll
    static void setUp() throws Exception {
        try{
            // Mock the Ship and Grid classes for testing
            mockShip1 = mock(Ship.class);
            mockShip2 = mock(Ship.class);
            mockShip3 = mock(Ship.class);
            mockShip4 = mock(Ship.class);
            mockShip5 = mock(Ship.class);

            mockPlayerGrid = mock(Grid.class);
            mockOppGrid = mock(Grid.class);

            // Initialize the player object
            player = new Player();

            // Inject mocked Grids for testing purposes
            player.playerGrid = mockPlayerGrid;
            player.oppGrid = mockOppGrid;

            // Inject mocked Ships (as if they were initialized with different lengths)
            player.ships = new Ship[]{mockShip1, mockShip2, mockShip3, mockShip4, mockShip5};
            setAllShipsLocationAndDirection();



        } catch (Exception e) {
            // If there's an issue, throw an exception that will prevent the tests from running
            throw new Exception("Setup failed: " + e.getMessage(), e);
        }
    }

    // Helper method to set all ships with location and direction
    private static void setAllShipsLocationAndDirection() {
        when(mockShip1.isLocationSet()).thenReturn(true);
        when(mockShip1.isDirectionSet()).thenReturn(true);
        when(mockShip2.isLocationSet()).thenReturn(true);
        when(mockShip2.isDirectionSet()).thenReturn(true);
        when(mockShip3.isLocationSet()).thenReturn(true);
        when(mockShip3.isDirectionSet()).thenReturn(true);
        when(mockShip4.isLocationSet()).thenReturn(true);
        when(mockShip4.isDirectionSet()).thenReturn(true);
        when(mockShip5.isLocationSet()).thenReturn(true);
        when(mockShip5.isDirectionSet()).thenReturn(true);
    }

    private static void testNumOfShipsLeft_NoShipsSet() {
        // Simulate no ships having location or direction set
        when(mockShip1.isLocationSet()).thenReturn(false);
        when(mockShip1.isDirectionSet()).thenReturn(false);
        when(mockShip2.isLocationSet()).thenReturn(false);
        when(mockShip2.isDirectionSet()).thenReturn(false);
        when(mockShip3.isLocationSet()).thenReturn(false);
        when(mockShip3.isDirectionSet()).thenReturn(false);
        when(mockShip4.isLocationSet()).thenReturn(false);
        when(mockShip4.isDirectionSet()).thenReturn(false);
        when(mockShip5.isLocationSet()).thenReturn(false);
        when(mockShip5.isDirectionSet()).thenReturn(false);
    }


    @AfterAll
    static void tearDown() throws Exception {
        try{
            // Nullify all objects to release memory and clean up resources
            player = new Player();;
            mockPlayerGrid = new Grid();
            mockOppGrid = new Grid();
            testNumOfShipsLeft_NoShipsSet();
        } catch (Exception e) {
            // If there's an issue, throw an exception that will prevent the tests from running
            throw new Exception("TearDown failed: " + e.getMessage(), e);
        }
    }

    @Test
    public void testAddShips(){
        player.addShips();
        for(int i = 0; i < player.ships.length; i++){
            mockPlayerGrid.addShip(player.ships[i]);
        }
    }

    // Test to check if all ship has been set
    @Test
    public void testNumOfShipsLeft_AllShipsSet() {
        setAllShipsLocationAndDirection();
        // Since all ships are set, no ships should be left to place
        assertEquals(0, player.numOfShipsLeft(), "All ships are set, so 0 ships should be left.");
    }

    // Test to check if all ship has not been set
    @Test
    public void testNoShipsSet() throws Exception {
        tearDown();
        assertEquals(5,player.numOfShipsLeft(),"No ships are set, so return all ships." );
    }

    @Test
    public void testNumOfShipsLeft_SomeShipsSet() throws Exception {
        // reset logic for testing purposes
        tearDown();
        // set up once again
        setUp();

        // Set the behavior for mockShip1
        mockShip1.setLocation(1, 1);  // Simulate setting the location
        mockShip1.setDirection(0);    // Simulate setting the direction
        // First, stub the behavior of isLocationSet and isDirectionSet
        when(mockShip1.isLocationSet()).thenReturn(true);
        when(mockShip1.isDirectionSet()).thenReturn(true);

        // Set the behavior for mockShip2
        mockShip2.setLocation(5, 8);
        mockShip2.setDirection(1);

        // Set the behavior for mockShip3 (location and direction not set)
        when(mockShip3.isLocationSet()).thenReturn(false);
        when(mockShip3.isDirectionSet()).thenReturn(false);

        // Set the behavior for mockShip4
        mockShip4.setLocation(3, 3);
        mockShip4.setDirection(2);

        // Set the behavior for mockShip5 (location and direction not set)
        when(mockShip5.isLocationSet()).thenReturn(false);
        when(mockShip5.isDirectionSet()).thenReturn(false);

        // Verify that the setters were called
        verify(mockShip1).setLocation(1, 1);
        verify(mockShip1).setDirection(0);
        verify(mockShip2).setLocation(5, 8);
        verify(mockShip2).setDirection(1);
        verify(mockShip4).setLocation(3, 3);
        verify(mockShip4).setDirection(2);

        // Call numOfShipsLeft()
        assertEquals(2, player.numOfShipsLeft(), "3 ships are set, so 2 ships should be left.");
    }

    // this test is intentionally to catch error
    @Test
    public void testNumOfShipsLeft_ThrowsErrorInSetup() throws Exception {
        player = null;
        // We expect this test to fail because setUp() will not initialize the player object
        assertThrows(Exception.class, () -> {
            player.numOfShipsLeft();  // This will throw a NullPointerException due to player being null
        });
        // reset everything
        tearDown();
        setUp();
    }
}
