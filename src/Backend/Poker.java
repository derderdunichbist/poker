package Backend;

import java.util.ArrayList;
import java.util.Collections;

public class Poker implements iController {

	private int pot;
	private int toCall;
	private Blind smallBlind;
	private Blind bigBlind;
	private int smallBlindValue = 50;
	private ArrayList<Card> carddeck;
	
	/**
	 * all players that are currently seated on the table
	 */
	private ArrayList<Seat> seatedPlayers;
	
	/**
	 * the players that are still participating in the ongoing round (not folded)
	 */
	private ArrayList<Seat> activePlayers;
	
	private eRound currentRound;

	public Poker() {

		this.seatedPlayers = new ArrayList<Seat>(6);
		this.activePlayers = new ArrayList<Seat>(6);
		this.setCarddeck(new ArrayList<Card>(52));

		this.init();

	}

	// Following listed methods are not yet fully determined neither in name,implementation nor order! ------{
	public void init() {

		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(smallBlindValue);

		// For testing-purposes only: players created here for now!
		this.addPlayer(new Seat("Benni"));
		this.addPlayer(new Seat("DeGagBenni"));
		this.addPlayer(new Seat("leGrandBrunBrun"));
		this.addPlayer(new Seat("BenniDiGaga"));
		this.addPlayer(new Seat("Berndsaftstinker"));
		this.addPlayer(new Seat("Berndsaftgarotzfotz"));

		// Syso-Test Are players added properly?
		System.out.println("currently Seated players are: " + seatedPlayers.toString());

		newRound();
	}

	public void resetDeck() {

		if (this.carddeck.size() > 0) {
			this.carddeck.clear();
		}

		for (eValue value : eValue.values()) {
			for (eColour color : eColour.values()) {
				Card c = new Card(color, value);
				this.carddeck.add(c);
			}
		}
		Collections.shuffle(this.carddeck);

		// test-syso: does deck work properly?
		System.out.println(this.carddeck);
	}

	// A new round begins after a complete hand is finished
	public void newRound() {

		resetDeck();
		setCurrentRound(eRound.PREFLOP);

		// remove blinds from players
		for (Seat s : this.seatedPlayers) {
			s.setBlind(null);
		}

		// someone gets the blinds..............................................[!!!!!]
		// impl: random number 1-6 gets SB, next index gets BB

		// set all players currently on the table to participate in the upcoming round
		activePlayers.clear();

		// {----------------------------------FIRST, LOOK FOR SMALLBLIND, SEAT HIM FIRST!---------------}
		for (Seat s : this.seatedPlayers) {
			if (s.getBlind() != null) {

			}
			// activePlayers.add(s);
		}

		// test-syso
		System.out.println("Still participating in the current round are: " + activePlayers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dealHands() {

	}

	private void burnCard() {
		carddeck.remove(0);
	}

	public void bettingRound() {

	}

	public void addPlayer(Seat s) {
		if (s != null && this.seatedPlayers.size() < 6) {
			this.seatedPlayers.add(s);
		} else {
			throw new NullPointerException("Player cannot be seated! Seat is either null or no more space!");
		}
	}

	public void removePlayer(Seat s) {
		this.seatedPlayers.remove(s);

		int amountOfPlayers = Seat.getAmountOfPlayers() - 1;
		Seat.setAmountOfPlayers(amountOfPlayers);
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public int getToCall() {
		return toCall;
	}

	// used when a player raises
	public void setToCall(int toCall) {
		this.toCall = toCall;
	}

	public Blind getSmallBlind() {
		return smallBlind;
	}

	// Leave this here for now, in case we want to dynamically change blinds
	// later on
	public void setSmallBlind(Blind smallBlind) {
		this.smallBlind = smallBlind;
	}

	public Blind getBigBlind() {
		return bigBlind;
	}

	public void setBigBlind(Blind bigBlind) {
		this.bigBlind = bigBlind;
	}

	public ArrayList<Card> getCarddeck() {
		return carddeck;
	}

	public void setCarddeck(ArrayList<Card> carddeck) {
		this.carddeck = carddeck;
	}

	public eRound getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(eRound currentRound) {
		this.currentRound = currentRound;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beginRound() {
		Seat smallBlindSeat = null;
		for(Seat seat : activePlayers) {
			if(seat.getBlind().getType().equals("smallBlind")) {
				smallBlindSeat = seat;
				break;
			}
		}
		int index = seatedPlayers.indexOf(smallBlindSeat);
		dealClockwise(index); //TODO Clockwise or Counter-Clockwise?
	}

	//TODO Clockwise or Counter-Clockwise?
	/**
	 * Deals 1 card clockwise to each player twice
	 * @param index Number of player in list to begin with
	 */
	private void dealClockwise(int index) {
		int seatCounter = index;
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < activePlayers.size() - 1; j++) {
				activePlayers.get(seatCounter).addCard(carddeck.get(0));
				this.removeDealtCard();
				
				//Ring-construct
				if(seatCounter == seatedPlayers.size() - 1 ) {
					seatCounter = 0;
				} else {
					seatCounter ++;
				}
			}
		}
	}

	private void removeDealtCard() {
		carddeck.remove(0);
	}

}
