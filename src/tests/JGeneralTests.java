package tests;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Backend.Blind;
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
		Blind.setAmountOfBlinds(0);
	}

	/**
	 * Test method for {@link Backend.Poker#removePlayer(int)}.
	 */
	@Test
	public void testRemoveMiddlePlayer() {
		final int SEAT_NUMBER_TO_REMOVE = 4;
		ArrayList<Seat> seatedPlayers = poker.getSeatedPlayers();
		Seat seatToTest = seatedPlayers.get(3);
		poker.removePlayer(SEAT_NUMBER_TO_REMOVE);
		assertTrue(seatedPlayers.size() == Poker.MAX_AMOUNT_OF_PLAYERS - 1);
		for(Seat seat : poker.getSeatedPlayers()){
			assertNotSame(seat, seatToTest);
		}
	}
	
	/**
	 * Test method for {@link Backend.Poker#removePlayer(int)}.
	 */
	@Test
	public void testRemoveFirstPlayer() {
		final int SEAT_NUMBER_TO_REMOVE = 1;
		ArrayList<Seat> seatedPlayers = poker.getSeatedPlayers();
		Seat seatToTest = seatedPlayers.get(0);
		poker.removePlayer(SEAT_NUMBER_TO_REMOVE);
		assertTrue(seatedPlayers.size() == Poker.MAX_AMOUNT_OF_PLAYERS - 1);
		for(Seat seat : poker.getSeatedPlayers()){
			assertNotSame(seat, seatToTest);
		}
	}
	
	/**
	 * Test method for {@link Backend.Poker#removePlayer(int)}.
	 */
	@Test
	public void testRemoveLastPlayer() {
		final int SEAT_NUMBER_TO_REMOVE = Poker.MAX_AMOUNT_OF_PLAYERS;
		ArrayList<Seat> seatedPlayers = poker.getSeatedPlayers();
		Seat seatToTest = seatedPlayers.get(Poker.MAX_AMOUNT_OF_PLAYERS - 1);
		poker.removePlayer(SEAT_NUMBER_TO_REMOVE);
		assertTrue(seatedPlayers.size() == Poker.MAX_AMOUNT_OF_PLAYERS - 1);
		for(Seat seat : poker.getSeatedPlayers()){
			assertNotSame(seat, seatToTest);
		}
	}

	/**
	 * Test method for {@link Backend.Poker#addPlayer(java.lang.String)}.
	 */
	@Test(expected=RuntimeException.class)
	public void testMaxCapacityAddPlayer() {
		assertTrue(poker.getSeatedPlayers().size() == Poker.MAX_AMOUNT_OF_PLAYERS);
		poker.addPlayer("BerndDeGagaGigi");
	}
	
//	/**
//	 * Test method for {@link Backend.Poker#addPlayer(java.lang.String)}.
//	 */
//	@Test
//	public void testAddPlayer() {
//		poker.addPlayer("BerndDeGagaGigi");
//	}
//	

}
