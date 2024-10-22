import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	/*
	 * Tests that the constructor properly initializes within the Ship class
	 */
	@Test
	void testShip() {
		Ship testShip = new Ship(2);
		assertEquals(testShip.getDirection(), -1);
		assertEquals(testShip.getLength(), 2);
		assertEquals(testShip.getRow(), -1);
		assertEquals(testShip.getCol(), -1);
	}

	/*
	 * This test ensures that when a location is set with a ship that it is properly recorded by the isLocationSet
	 * method (boolean)
	 */
	@Test
	void testIsLocationSet() {
		Ship testShip = new Ship(2);
		assertEquals(testShip.isLocationSet(), false);
		testShip.setLocation(0, 0);
		assertEquals(testShip.isLocationSet(), true);
	}
	

	/*
	 * This test ensures that when a direction is set (either vertical, horizontal, or unset) that the IsDirectionSet
	 * method appropriately records the change
	 */
	@Test
	void testIsDirectionSet() {
		Ship testShip = new Ship(2);
		assertEquals(testShip.isDirectionSet(), false);
		testShip.setDirection(1);
		assertEquals(testShip.isDirectionSet(), true);
		assertThrows(IllegalArgumentException.class, () -> {
	        testShip.setDirection(2);
	    });
		assertThrows(IllegalArgumentException.class, () -> {
	        testShip.setDirection(-2);
	    });
	}

	/*
	 * This test ensures that when a location is set with a ship, that it is set in the specified location
	 * and that the status is changed appropriately for that row and column
	 */
	@Test
	void testSetLocation() {
		Ship testShip = new Ship(2);
		testShip.setLocation(0, 0);
		assertEquals(testShip.getRow(), 0);
		assertEquals(testShip.getCol(), 0);
	}

	/*
	 * This test ensures that the direction is properly recorded for the ship when it is first set
	 */
	@Test
	void testSetDirection() {
		Ship testShip = new Ship(2);
		testShip.setDirection(0);
		assertEquals(testShip.getDirection(), 0);
		testShip.setDirection(1);
		assertEquals(testShip.getDirection(), 1);
		testShip.setDirection(-1);
		assertEquals(testShip.getDirection(), -1);
	}

	/*
	 * This test ensures that the row is properly set for the ship in the grid and not misappropriated.
	 */
	@Test
	void testGetRow() {
		Ship testShip = new Ship(3);
		testShip.setLocation(4, 2);
		assertEquals(testShip.getRow(), 4);
	}

	/*
	 * This test ensures that the column is properly set for the ship in the grid and not misappropriated.
	 */
	@Test
	void testGetCol() {
		Ship testShip = new Ship(4);
		testShip.setLocation(1, 3);
		assertEquals(testShip.getCol(), 3);
	}

	/*
	 * This test ensures that the length is properly set based on the input from the constructor of the ship
	 */
	@Test
	void testGetLength() {
		Ship testShip = new Ship(3);
		assertEquals(testShip.getLength(), 3);
	}

	/*
	 * This test ensures that the direction is properly set. -1 for unset, 0 for horizontal, and 1 for vertical
	 */
	@Test
	void testGetDirection() {
		Ship testShip = new Ship(2);
		assertEquals(testShip.getDirection(), -1);
		testShip.setDirection(1);
		assertEquals(testShip.getDirection(), 1);
		testShip.setDirection(0);
		assertEquals(testShip.getDirection(), 0);
	}
	
	/*
	 * this test ensures that the toString method properly called the private toString method and prints out
	 * the proper message based on which public toString method is being called, null, 0, or 1
	 */
	@Test
	void testToString() {
		Ship testShip = new Ship(4);
		testShip.setLocation(5, 7);
		assertEquals(testShip.toString(), "Ship: 5, 7 with length 4 and direction UNSET");
		testShip.setDirection(0);
		assertEquals(testShip.toString(), "Ship: 5, 7 with length 4 and direction HORIZONTAL");
		testShip.setDirection(1);
		assertEquals(testShip.toString(), "Ship: 5, 7 with length 4 and direction VERTICAL");
	}

}
