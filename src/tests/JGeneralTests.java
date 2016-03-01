package tests;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Backend.Poker;
import Backend.Seat;


/**
 * @author Kosta
 *
 */
public class JGeneralTests {
	private Poker poker;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		poker = new Poker();
		
		poker.addPlayer("Benni");
		poker.addPlayer("DeGagBenni");
		poker.addPlayer("leGrandBrunBrun");
		poker.addPlayer("BenniDiGaga");
		poker.addPlayer("Berndsaftstinker");
		poker.addPlayer("Berndsaftgagrotzfotz");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		poker = null;
	}

	/**
	 * Test method for {@link Backend.Poker#removePlayer(int)}.
	 */
	@Test
	public void testRemoveMiddlePlayer() {
		ArrayList<Seat> seatedPlayers = poker.getSeatedPlayers();
		Seat seatToTest = seatedPlayers.get(3);
		poker.removePlayer(4);
		assertTrue(seatedPlayers.size() == 5);
		for(Seat seat : poker.getSeatedPlayers()){
			assertNotSame(seat, seatToTest);
		}
	}
	
//	/**
//	 * Test method for {@link Backend.Poker#removePlayer(int)}.
//	 */
//	@Test
//	public void testRemoveFirstPlayer() {
//		fail("Not yet implemented");
//	}
//	
//	/**
//	 * Test method for {@link Backend.Poker#removePlayer(int)}.
//	 */
//	@Test
//	public void testRemoveLastPlayer() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for {@link Backend.Poker#addPlayer(java.lang.String)}.
//	 */
//	@Test
//	public void testAddPlayer() {
//		poker.addPlayer("BerndDeGagaGigi");
//	}
//	
//	/**
//	 * Test method for {@link Backend.Poker#addPlayer(java.lang.String)}.
//	 */
//	@Test
//	public void testMaxCapacityAddPlayer() {
//		fail("Not yet implemented");
//	}

}
