import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GridTest {

	/*
	 * Ensures the functionality of the constructor for the Grid class. Checks that the grid has been 
	initialized and that no ships have been added yet
	*/
	@Test
	void testGrid() {
		Grid tstGrid = new Grid();
		for (int i = 0; i < tstGrid.numRows(); i++) {
			for(int j = 0; j < tstGrid.numCols(); j++) {
				assertEquals(tstGrid.hasShip(i,j), false);
			}
		}
	}
	
	/*
	 * Ensures that any position in the grid is not marked with a hit (status change) until the markHit 
	 * method is called. Subsequently the status of that location changes to hit
	 */
	@Test 
	void testMarkHit(){
    	Grid testGrid = new Grid();
    	for(int i = 0; i < 10; i++) {
    		for(int j = 0; j < 10; j++) {
    			assertEquals(testGrid.getStatus(i, j), 0);
    			testGrid.markHit(i, j);
    			assertEquals(testGrid.getStatus(i, j), 1);
    		}
    	}
    }
	
	/*
	 * Ensures that at any location in the grid, the status of that position is not marked missed (status change)
	 * until the markMiss method is called. Subsequently the status changes from unguessed to missed
	 */
	@Test
	void testMarkMiss() {
		Grid testGrid = new Grid();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				assertEquals(testGrid.getStatus(i, j), 0);
				testGrid.markMiss(i, j);
				assertEquals(testGrid.getStatus(i, j), 2);
			}
		}
	}
	
	/*
	 * This test checks that when this method is called, the status of any given location is changed from
	 * unguessed to either hit or missed
	 */
	@Test 
	void testSetStatus() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.getStatus(0, 0), 0);
		testGrid.setStatus(0, 0, 2);
		assertEquals(testGrid.getStatus(0, 0), 2);
		testGrid.setStatus(0, 0, 1);
		assertEquals(testGrid.getStatus(0, 0), 1);
	}
	
	/*
	 * Ensures that the status is changed for only the location specified and matches what the method
	 * changed the status to
	 */
	@Test
	void testGetStatus() {
		Grid testGrid = new Grid();
		testGrid.setStatus(0, 0, 0);
		testGrid.setStatus(4, 9, 1);
		testGrid.setStatus(8, 4, 2);
		for (int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				if (i == 0 & j == 0) {
					assertEquals(testGrid.getStatus(i, j), 0);
				}
				else if (i == 4 & j == 9) {
					assertEquals(testGrid.getStatus(i, j), 1);
				}
				else if  (i == 8 & j == 4) {
					assertEquals(testGrid.getStatus(i, j), 2);
				}
				else {
					assertEquals(testGrid.getStatus(i, j), 0);
				}
			}
		}
	}
	
	/*
	 * Tests that the boolean within the AlreadyGuessed method appropriately changes when the status changes
	 * initially should be false, and once it is guessed (marked hit or missed) the boolean should change to true
	 */
	@Test
	void testAlreadyGuessed() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.alreadyGuessed(0, 0), false);
		testGrid.setStatus(0,  0,  1);
		assertEquals(testGrid.alreadyGuessed(0, 0), true);
		testGrid.setStatus(0, 0, 2);
		assertEquals(testGrid.alreadyGuessed(0, 0), true);
	}
	
	/*
	 * Ensures that a ship is set appropriately in the grid. Once the ship is set the position should be marked true
	 * for having a ship. Tracks that the boolean appropriately changes
	 */
	@Test
	void testSetShip() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.hasShip(0, 0), false);
		testGrid.setShip(0, 0, true);
		assertEquals(testGrid.hasShip(0, 0), true);
		testGrid.setShip(0, 0, false);
		assertEquals(testGrid.hasShip(0, 0), false);
	}
	
	/*
	 * This is a slightly redundant test since the testSetShip method also ensures that hasShip is working 
	 * appropriately. This test is repeated to ensure that when a ship is set that the hasShip method appropriately
	 * reports back that the location has a ship
	 */
	@Test
	void testHasShip() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.hasShip(0, 0), false);
		testGrid.setShip(0, 0, true);
		assertEquals(testGrid.hasShip(0, 0), true);
		testGrid.setShip(0, 0, false);
		assertEquals(testGrid.hasShip(0, 0), false);
	}
	
	/*
	 * This test ensures that the get function returns as expected. It gets the location in the grid that was created.
	 * In our test it should return the initial parameters of the newly constructed object. That is, that the status 
	 * is unguessed (0), the location has no ship, the length should be -1 and the direction should be -1 until set by
	 * the user
	 */
	@Test
	void testGet() {
		Grid testGrid = new Grid();
		Location testLoc = testGrid.get(0, 0);
		assertEquals(testLoc.getStatus(), 0);
		assertEquals(testLoc.hasShip(), false);
		assertEquals(testLoc.getLengthOfShip(), -1);
		assertEquals(testLoc.getDirectionOfShip(), -1);
	}

	/*
	 * The grid class has a default size of 10 rows. This test ensures that the number of rows in the grid 
	 * is 10 after the grid is initialized
	 */
	@Test
	void testNumRows() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.numRows(), 10);
	}
	
	/*
	 * The grid class has a default of 10 columns. This test ensures that the number of columns in the grid
	 * is 10 after the grid is initialized
	 */
	@Test
	void testNumCol() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.numCols(), 10);
	}
	
	/*
	 * This tests adds 20 Hits to the grid. Any number of hits over 17 will result in the HasLsot method being
	 * marked true. First we test for the number of hits of 20 then we check a new grid for the number of hits of 16 
	 * and ensure the first is marked lost and the second is not marked lost
	 */
	@Test
	void testHasLost() {
		Grid testGrid = new Grid();
		assertEquals(testGrid.hasLost(), false);
		for (int i = 0; i < 10; i++) {
			for(int j = 0; j < 2; j++) {
				testGrid.markHit(i, j);
			}
		}
		assertEquals(testGrid.hasLost(), true);
		Grid testGridTwo = new Grid();
		assertEquals(testGridTwo.hasLost(), false);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				testGrid.markHit(i, j);
			}
		}
		assertEquals(testGridTwo.hasLost(), false);
	}
	
	/*
	 * This test ensures that a ship is added appropriately. it checks the location, direction, is the location is
	 * marked as having a ship, and that no other location is inaccurately marked as having a ship
	 */
	@Test
	void testAddShip() {
		//-1 = unset, 0 = horizontal, 1 = vertical
		Grid testGrid = new Grid();
		Ship testShip = new Ship(4);
		assertEquals(testShip.isLocationSet(), false);
		testShip.setLocation(5, 5);
		assertEquals(testShip.isLocationSet(), true);
		assertEquals(testShip.isDirectionSet(), false);
		testShip.setDirection(0);
		assertEquals(testShip.isDirectionSet(), true);
		assertEquals(testShip.getDirection(), 0);
		
		assertEquals(testGrid.hasShip(5, 5), false);
		testGrid.addShip(testShip);
		assertEquals(testGrid.hasShip(5, 5), true);
		
		for (int i = 0; i < testShip.getLength(); i++) {
			assertEquals(testGrid.hasShip(5, 5+i), true);
		}
		testShip.setDirection(1);
		testGrid.addShip(testShip);
		for (int i = 0; i < testShip.getLength(); i++) {
			assertEquals(testGrid.hasShip(5+i, 5), true);
		}
	}
	
	/*
	 * This test checks the Switch Counter method. It ensures that the counter is accurately switched 
	 * to the appropriate integer for the array.
	 */
	@Test
	void testswitchCounterToIntegerForArray() {
		Grid testGrid = new Grid();
		int x = 65;
		for (int y = 0; y < 26; y++){
			assertEquals(testGrid.switchCounterToIntegerForArray(x), y);
			x++;
		}		
	}
}
