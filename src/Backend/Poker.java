package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Interfaces.iController;

public class Poker implements iController {

	private int pot;
	private int toCall;
	private Blind smallBlind;
	private Blind bigBlind;
	private int smallBlindValue = 50;
	private ArrayList<Card> carddeck;
	private CommunityCards comCards;

	/**
	 * all players that are currently seated on the table
	 */
	private ArrayList<Seat> seatedPlayers;

	/**
	 * the players that are still participating in the ongoing round (not
	 * folded)
	 */
	private ArrayList<Seat> activePlayers;

	private eRound currentRound;

	public Poker() {

		this.seatedPlayers = new ArrayList<Seat>(6);
		this.activePlayers = new ArrayList<Seat>(6);
		this.setCarddeck(new ArrayList<Card>(52));
		this.currentRound = eRound.INITIALIZE;
		this.init();
		this.comCards = new CommunityCards();
		

	}

	// Following listed methods are not yet fully determined neither in
	// name,implementation nor order! ------{
	public void init() {

		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(smallBlindValue);

		// For testing-purposes only: players created here for now!
		this.addPlayer(new Seat("Benni",this));
		this.addPlayer(new Seat("DeGagBenni",this));
		this.addPlayer(new Seat("leGrandBrunBrun",this));
		this.addPlayer(new Seat("BenniDiGaga",this));
		this.addPlayer(new Seat("Berndsaftstinker",this));
		this.addPlayer(new Seat("Berndsaftgagrotzfotz",this));

		// Give a random player small- and bigblind
		Random rand = new Random();
		int index = rand.nextInt((seatedPlayers.size() - 1) - 0 + 0) + 0;
		Seat sBPlayer = seatedPlayers.get(index);
		sBPlayer.setBlind(this.smallBlind);

		if (index < (seatedPlayers.size() - 1)) {
			index++;
		} else {
			index = 0;
		}
		Seat bBPlayer = seatedPlayers.get(index);
		bBPlayer.setBlind(this.bigBlind);

		// Syso-Test Are players added properly?
		System.out.println("currently Seated players are: " + seatedPlayers.toString());

		newRound();
	}

	public ArrayList<Seat> getActivePlayers() {
		return activePlayers;
	}

	public void setActivePlayers(ArrayList<Seat> activePlayers) {
		this.activePlayers = activePlayers;
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
		int newSmallBlind = 0; // Saves index of the seat to receive smallBlind
								// next
		
	
		resetDeck();
		activePlayers.clear();
		
		if(this.currentRound!=eRound.INITIALIZE){
			
		this.comCards.reset();
		for (Seat s : this.seatedPlayers) { // identify old smallBlindPlayer and
											// delete current Blindpositions
			if (s.getBlind() != null && s.getBlind().getType().equals("smallBlind")) {
				newSmallBlind = (seatedPlayers.indexOf(s)) + 1;
			}
			s.setBlind(null);
		}
		
		
		// move the Small and Bigblind to next position:
		this.seatedPlayers.get(newSmallBlind).setBlind(this.smallBlind);
		this.seatedPlayers.get(newSmallBlind + 1).setBlind(this.bigBlind);
		}
		
		setCurrentRound(eRound.PREFLOP);
		// Identify which player has Small-Blind
		Seat sBPlayer = null;
		for (Seat seat : seatedPlayers) {
			if (seat.getBlind() != null && seat.getBlind().getType().equals("smallBlind")) {
				sBPlayer = seat;
				break;
			}
		}
		int index = seatedPlayers.indexOf(sBPlayer);
		// Set all participating players for the next round. Starting in order
		// with Small and Bigblind
		for (int i = 0; i < seatedPlayers.size(); i++) {
			this.activePlayers.add(seatedPlayers.get(index));
			if (index == seatedPlayers.size() - 1) {
				index = 0;
			} else {
				index++;
			}
		}
		dealHands();
	}

	@Override
	public void dealHands() {
		System.out.println(activePlayers);
		switch(this.currentRound){
		case ROUNDEND:
			identifyHands();
			break;
		
		case PREFLOP: 
			int seatCounter = 0;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < activePlayers.size(); j++) {
					activePlayers.get(seatCounter).addCard(carddeck.remove(0));
					// Ring-construct
					if (seatCounter == seatedPlayers.size() - 1) {
						seatCounter = 0;
					} else {
						seatCounter++;
					}
				}
			}
			break;
		
		case FLOP:
			removeCard(); //burn 1 card 
			this.comCards.addCard(this.carddeck.remove(0));
			this.comCards.addCard(this.carddeck.remove(0));
			this.comCards.addCard(this.carddeck.remove(0));
			
			break;
			
		case TURN:
			removeCard(); //burn 1 card 
			this.comCards.addCard(this.carddeck.remove(0));
			break;
		
		case RIVER:
			removeCard(); //burn 1 card 
			this.comCards.addCard(this.carddeck.remove(0));
			break;
				
		}
		bettingRound();
	}
	
	private void identifyHands() {
		// TODO Auto-generated method stub
		
	}

	//removeDealtCard and burnCard with same implementation became removeCard
	private void removeCard() { 
		carddeck.remove(0);
	}

	public void bettingRound() {
		// TODO remove this, this is only here for testing-purposes:
		for (Seat s : activePlayers) {
			ArrayList<Card> holeCards = s.getCards();
			System.out.println("Spieler: " + s.getName());
			for (Card holeCard : holeCards)
				System.out.println("hält: " + holeCard.getName());
			if(s.getBlind()!=null){
				System.out.println("Spieler: "+ s.getName() +" hält den "+ s.getBlind().getType());
			}
		}
		//get me small- and bigBlindPlayer; actingPlayer represents player to currently call/raise/fold
		Seat sBPlayer = this.activePlayers.get(0);
		Seat bBPlayer = this.activePlayers.get(1);
		Seat actingPlayer;
		
		if(this.currentRound == eRound.PREFLOP){
			sBPlayer.bet(this.smallBlind.getValue());
			bBPlayer.bet(this.bigBlind.getValue());
			actingPlayer = this.activePlayers.get(2);
		}
		else{
			actingPlayer = sBPlayer; //in case we are not in Preflop, the first player to act as the round begins is always the smallBlindPlayer
		}
		//TODO temporarily, raise/check/calls are realized by console-input
		//calls a method act() in which the actingPlayer either folds/calls or raises
		int nextPlayerIndex;
		Seat priorPlayer=null; //last acting player : needed to check the move & position of the last to act player
		
		while (priorPlayer == null || priorPlayer.isLastPlayer() == false || priorPlayer.getLastMove() != "call") {
			actingPlayer.act();
			int currentPlayerIndex = (this.activePlayers.indexOf(actingPlayer));
			if (currentPlayerIndex == 5) {
				nextPlayerIndex = 0;
			} else {
				nextPlayerIndex = currentPlayerIndex+1;
			}
			//this was the last player to act
			priorPlayer = this.activePlayers.get(currentPlayerIndex);
			//set acting player to next player to act
			actingPlayer = this.activePlayers.get(nextPlayerIndex);
			
			System.out.println(priorPlayer + priorPlayer.getLastMove() + " "+priorPlayer.isLastPlayer());
			System.out.println(actingPlayer + actingPlayer.getLastMove() + " "+actingPlayer.isLastPlayer());
		}
		
		
		
		//TODO: determine next acting Player (again, get the index of currently acting player and implement ring-type selection
		//TODO: after implementation of determining next player, create a loop for the betting round (right now, its only 1 player to act once)

			//test-Syso
			System.out.println("nextRound!");
			//newRound();
			// TODO reset bettedAmount for every round (probably better to do in newRound)
		
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

	@Override
	public void beginRound() { //INTERFACE METHOD!

		
	}

	public CommunityCards getComCards() {
		return comCards;
	}

	public void setComCards(CommunityCards comCards) {
		this.comCards = comCards;
	}

}
