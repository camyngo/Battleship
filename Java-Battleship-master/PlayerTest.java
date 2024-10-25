import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerTest{
    private static Player player;
    private static Ship mockShip1, mockShip2 ,mockShip3, mockShip4, mockShip5;
    private static Grid mockPlayerGrid, mockOppGrid;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
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

        // Call the helper method to set all ships as "set"
        setAllShipsLocationAndDirection() ;

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

    @AfterAll
    static void tearDown() throws Exception {

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
        // Since all ships are set, no ships should be left to place
        assertEquals(0, player.numOfShipsLeft(), "All ships are set, so 0 ships should be left.");
    }

    @Test
    public void testNumOfShipsLeft_NoShipsSet(){

    }


}
