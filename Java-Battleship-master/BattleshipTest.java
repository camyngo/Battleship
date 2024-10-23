import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BattleshipTest {

    @Test
    public void testConvertLetterToInt() {
        assertEquals(0, Battleship.convertLetterToInt("A"));
        assertEquals(1, Battleship.convertLetterToInt("B"));
        assertEquals(2, Battleship.convertLetterToInt("C"));
        assertEquals(3, Battleship.convertLetterToInt("D"));
        assertEquals(4, Battleship.convertLetterToInt("E"));
        assertEquals(5, Battleship.convertLetterToInt("F"));
        assertEquals(6, Battleship.convertLetterToInt("G"));
        assertEquals(7, Battleship.convertLetterToInt("H"));
        assertEquals(8, Battleship.convertLetterToInt("I"));
        assertEquals(-1, Battleship.convertLetterToInt("Z"));
        assertEquals(9, Battleship.convertLetterToInt("J"));

    }

    @Test
    public void testConvertIntToLetter() {
        assertEquals("A", Battleship.convertIntToLetter(0));
        assertEquals("B", Battleship.convertIntToLetter(1));
        assertEquals("C", Battleship.convertIntToLetter(2));
        assertEquals("D", Battleship.convertIntToLetter(3));
        assertEquals("E", Battleship.convertIntToLetter(4));
        assertEquals("F", Battleship.convertIntToLetter(5));
        assertEquals("G", Battleship.convertIntToLetter(6));
        assertEquals("H", Battleship.convertIntToLetter(7));
        assertEquals("I", Battleship.convertIntToLetter(8));
        assertEquals("J", Battleship.convertIntToLetter(9));
        assertEquals("Z", Battleship.convertIntToLetter(10)); // Invalid case
    }

    @Test
    public void testConvertUserColToProCol() {
        assertEquals(0, Battleship.convertUserColToProCol(1));
        assertEquals(1, Battleship.convertUserColToProCol(2));
        assertEquals(2, Battleship.convertUserColToProCol(3));
        assertEquals(3, Battleship.convertUserColToProCol(4));
        assertEquals(4, Battleship.convertUserColToProCol(5));
        assertEquals(5, Battleship.convertUserColToProCol(6));
        assertEquals(6, Battleship.convertUserColToProCol(7));
        assertEquals(7, Battleship.convertUserColToProCol(8));
        assertEquals(8, Battleship.convertUserColToProCol(9));
        assertEquals(9, Battleship.convertUserColToProCol(10));
        assertEquals(-1, Battleship.convertUserColToProCol(11)); // Invalid case
    }

    @Test
    public void testConvertCompColToRegular() {
        assertEquals(1, Battleship.convertCompColToRegular(0));
        assertEquals(2, Battleship.convertCompColToRegular(1));
        assertEquals(3, Battleship.convertCompColToRegular(2));
        assertEquals(4, Battleship.convertCompColToRegular(3));
        assertEquals(5, Battleship.convertCompColToRegular(4));
        assertEquals(6, Battleship.convertCompColToRegular(5));
        assertEquals(7, Battleship.convertCompColToRegular(6));
        assertEquals(8, Battleship.convertCompColToRegular(7));
        assertEquals(9, Battleship.convertCompColToRegular(8));
        assertEquals(10, Battleship.convertCompColToRegular(9));
        assertEquals(-1, Battleship.convertCompColToRegular(10));
    }

    @Test
    public void testHasErrorsHorizontal() {
        Player player = new Player();
        Ship ship = new Ship(4);
        player.ships[0] = ship;
        assertFalse(Battleship.hasErrors(0, 0, 0, player, 0));
        assertTrue(Battleship.hasErrors(0, 7, 0, player, 0));
    }
    @Test
    public void testOverlapHorizontal() {
        Player player = new Player();
        Ship ship1 = new Ship(4); // Ship of length 4
        player.ships[0] = ship1;

        // Place the first ship at (0,0) horizontally
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(0); // Horizontal
        player.playerGrid.addShip(player.ships[0]);

        // Place another ship that overlaps at (0, 2)
        Ship ship2 = new Ship(3); // Another ship of length 3
        player.ships[1] = ship2;

        // This should return true due to overlap at (0, 2)
        assertTrue(Battleship.hasErrors(0, 2, 0, player, 1)); // Overlap at (0,2)
    }

    @Test
    public void testHasErrorsVertical() {
        Player player = new Player();
        Ship ship = new Ship(4);
        player.ships[0] = ship;
        assertFalse(Battleship.hasErrors(0, 0, 1, player, 0));
        assertTrue(Battleship.hasErrors(7, 0, 1, player, 0));
    }

    @Test
    public void testOverlapVertical() {
        Player player = new Player();
        Ship ship1 = new Ship(4); // Ship of length 4
        player.ships[0] = ship1;

        // Place the first ship at (0,0) vertically
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(1); // Vertical
        player.playerGrid.addShip(player.ships[0]);

        // Place another ship that overlaps at (2, 0)
        Ship ship2 = new Ship(3); // Another ship of length 3
        player.ships[1] = ship2;

        // This should return true due to overlap at (2, 0)
        assertTrue(Battleship.hasErrors(2, 0, 1, player, 1)); // Overlap at (2,0)
    }

    @Test
    public void testOverlapHorizontalComp() {
        Player computer = new Player();
        Ship ship1 = new Ship(4); // Ship of length 4
        computer.ships[0] = ship1;

        // Place the first ship horizontally at (0, 0)
        computer.ships[0].setLocation(0, 0);
        computer.ships[0].setDirection(0); // Horizontal
        computer.playerGrid.addShip(computer.ships[0]);

        // Now try placing another ship horizontally starting at (0, 2), which should overlap
        Ship ship2 = new Ship(3); // Another ship of length 3
        computer.ships[1] = ship2;

        assertTrue(Battleship.hasErrorsComp(0, 2, 0, computer, 1)); // Overlap, should return true
    }

    @Test
    public void testVerticalOverlap() {
        Player player = new Player();
        Ship ship1 = new Ship(4); // Ship of length 4
        player.ships[0] = ship1;

        // Place the first ship vertically at (0, 0)
        player.ships[0].setLocation(0, 0);
        player.ships[0].setDirection(1); // Vertical direction
        player.playerGrid.addShip(player.ships[0]);

        // Now place a second ship vertically starting at (2, 0), which should overlap
        Ship ship2 = new Ship(3); // Another ship of length 3
        player.ships[1] = ship2;

        // Check if the new ship overlaps with the first one (it should)
        assertTrue(Battleship.hasErrorsComp(2, 0, 1, player, 1)); // Overlap, should return true
    }
    @Test
    public void testVerticalShipOutOfBounds() {
        Player player = new Player();
        Ship ship = new Ship(4); // Ship of length 4
        player.ships[0] = ship;

        // Attempt to place a ship vertically starting at row 8, which should go out of bounds
        // Length + row = 4 + 8 = 12, which exceeds the grid boundary (10).
        assertTrue(Battleship.hasErrorsComp(8, 0, 1, player, 0)); // Ship goes out of bounds
    }

    @Test
    public void testHasErrorsComp() {
        Player player = new Player();
        Ship ship = new Ship(3); // Ship of length 3
        player.ships[0] = ship;

        // Test no errors, valid placement
        assertFalse(Battleship.hasErrorsComp(0, 0, 0, player, 0)); // No errors, ship fits horizontally

        // Test errors, ship out of bounds horizontally
        assertTrue(Battleship.hasErrorsComp(0, 8, 0, player, 0)); // Ship doesn't fit horizontally
    }


}

