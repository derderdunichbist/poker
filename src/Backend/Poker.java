package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Interfaces.iController;

public class Poker implements iController {

	/**
	 * The current pot value
	 */
	private int pot;
	
	/**
	 * The current amount every player has to call (syn.: highest bet yet)
	 */
	private int toCall;
	
	/**
	 * The small blind value
	 */
	private int smallBlindValue = 50;
	
	/**
	 * The game's small blind
	 */
	private Blind smallBlind;
	
	/**
	 * The game's big blind
	 */
	private Blind bigBlind;
	
	/**
	 * The game's community cards
	 */
	private CommunityCards comCards;
	
	/**
	 * The maximum amount of players that can play
	 */
	public static final int MAX_AMOUNT_OF_PLAYERS = 6;
	
	/**
	 * The amount of cards adapted by the Texas Hold'em rules
	 */
	public static final int AMOUNT_OF_CARDS = 52;

	/**
	 * The shuffled carddeck
	 */
	private ArrayList<Card> carddeck;
	
	/**
	 * all players that are currently seated on the table
	 */
	private ArrayList<Seat> seatedPlayers;

	/**
	 * the players that are still participating in the ongoing round (not
	 * folded)
	 */
	private ArrayList<Seat> activePlayers;

	/**
	 * The current round state
	 */
	private eRound currentRound;

	public Poker() {
		this.seatedPlayers = new ArrayList<Seat>(MAX_AMOUNT_OF_PLAYERS);
		this.activePlayers = new ArrayList<Seat>(MAX_AMOUNT_OF_PLAYERS);
		this.setCarddeck(new ArrayList<Card>(AMOUNT_OF_CARDS));
		this.currentRound = eRound.INITIALIZE;
		this.comCards = new CommunityCards();
		this.init();
	}

	public int getPot() {
		return pot;
	}

	public int getToCall() {
		return toCall;
	}

	public Blind getBigBlind() {
		return bigBlind;
	}

	public Blind getSmallBlind() {
		return smallBlind;
	}

	public ArrayList<Seat> getActivePlayers() {
		return activePlayers;
	}

	public ArrayList<Card> getCarddeck() {
		return carddeck;
	}

	public eRound getCurrentRound() {
		return currentRound;
	}
	
	public CommunityCards getComCards() {
		return comCards;
	}

	public void setActivePlayers(ArrayList<Seat> activePlayers) {
		this.activePlayers = activePlayers;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public void setToCall(int toCall) {
		this.toCall = toCall;
	}

	public void setSmallBlind(Blind smallBlind) {
		this.smallBlind = smallBlind;
	}

	public void setBigBlind(Blind bigBlind) {
		this.bigBlind = bigBlind;
	}

	public void setCarddeck(ArrayList<Card> carddeck) {
		this.carddeck = carddeck;
	}

	public void setCurrentRound(eRound currentRound) {
		this.currentRound = currentRound;
	}

	public void setComCards(CommunityCards comCards) {
		this.comCards = comCards;
	}

	public void init() {

		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(smallBlindValue);
		this.initSeats();
	}

	private void initSeats() {
		for(int i = 0; i < MAX_AMOUNT_OF_PLAYERS; i++) {
			this.seatedPlayers.add(new Seat(this));
		}
	}

	public void resetDeck() {
		if (this.carddeck != null) {
			this.carddeck.clear();
		}

		for (eValue value : eValue.values()) {
			for (eColour color : eColour.values()) {
				Card c = new Card(color, value);
				this.carddeck.add(c);
			}
		}
		Collections.shuffle(this.carddeck);
	}
	
	@Override
	public void newRound() {
		int newSmallBlind = 0; // Saves index of the seat to receive smallBlind next
		int newBigBlind = 0;	// Saves index of the seat to receive bigBlind next					

		resetDeck();//Fill carddeck with 52 cards again, in random order
		activePlayers.clear();

		if (this.currentRound != eRound.INITIALIZE) { // This is not being done in the first round (initialize-phase), as blinds are randomly assigned then

			this.comCards.reset();
			for (Seat s : this.seatedPlayers) { // identify old smallBlindPlayer and delete current Blindpositions
				if (s.getBlind() != null && s.getBlind().getType().equals("smallBlind")) { // Who has the smallBlind?
					newSmallBlind = (seatedPlayers.indexOf(s));// determine seat of old smallBlind
				}
				s.setBlind(null);
			}
			
			//ring-type identification for next player's index that receives smallBlind next
			newSmallBlind = this.getNextIndex(newSmallBlind, seatedPlayers);

			this.seatedPlayers.get(newSmallBlind).setBlind(this.smallBlind);//move the smallBlind to next position

			//ring-type identification for next player's index that receives bigBlind next
			newBigBlind = newSmallBlind;
			newBigBlind = this.getNextIndex(newBigBlind, seatedPlayers);
			
			this.seatedPlayers.get(newBigBlind).setBlind(this.bigBlind);//move the BigBlind to next position
		}

		setCurrentRound(eRound.PREFLOP); //When a new round begins, round-state is Preflop!
		
		// Identify which player has Small-Blind
		Seat sBPlayer = null;
		for (Seat seat : seatedPlayers) {
			if (seat.getBlind() != null && seat.getBlind().getType().equals("smallBlind")) {
				sBPlayer = seat;
				break;
			}
		}
		int index = seatedPlayers.indexOf(sBPlayer); //get index of smallBlindPlayer
		
		// Set all participating players for the next round. Starting in order
		// with Small and Bigblind
		for (int i = 0; i < seatedPlayers.size(); i++) {
			this.activePlayers.add(seatedPlayers.get(index)); //add Players starting with smallBlind
			index = this.getNextIndex(index, seatedPlayers);
		}
		dealHands(); //Call dealHands to start dealing hands to each player!
	}
	
	private int getNextIndex(int index, ArrayList<Seat> seats) {
		if (index == seats.size() - 1) {
			return 0;
		} else {
			index++;
			return index;
		}
	}

	private void identifyHands() {
		// TODO Auto-generated method stub

	}

	// removeDealtCard and burnCard with same implementation became removeCard
	private void removeCard() {
		carddeck.remove(0);
	}
	
	/**
	 * bettingRound(): manages all betting of players; Also manages who is still active for the current and upcoming rounds 	  
	 */
	public void bettingRound() {

		//This block has no functionality. It is simply there to see who holds which card for the time being, as well as who is assigned with a blind.
		System.out.println();
		for (Seat s : activePlayers) {
			ArrayList<Card> holeCards = s.getCards();
			System.out.print("Spieler " + s.getName() + " hält: ");
			for (Card holeCard : holeCards)
				System.out.print(" " + holeCard.getName() + ", ");
			System.out.println();
			if (s.getBlind() != null) {
				System.out.println("Spieler: " + s.getName() + " hält den " + s.getBlind().getType());
			}
		}
		System.out.println();
		
		//Get the SmallBlind and Bigblind Players
		Seat sBPlayer = this.activePlayers.get(0);
		Seat bBPlayer = this.activePlayers.get(1);
		Seat actingPlayer; //the player that is currently acting: folding,calling(checking),raising

		//If current round is Preflop, Small and Bigblinds have to be paid, the first player to then act sits behind the BigBlind
		if (this.currentRound == eRound.PREFLOP) {
			sBPlayer.bet(this.smallBlind.getValue());
			bBPlayer.bet(this.bigBlind.getValue());
			actingPlayer = this.activePlayers.get(2);
		} else {//in any other case, every round begins with the smallBlind to act first (or, if folded in order)
			actingPlayer = sBPlayer; 
									
		}

		int nextPlayerIndex;
		Seat priorPlayer = null; //placeholder: Mainly used to for example determine whether or not the previous player did call & was last player to act in the current round

		//A loop that continues until the last player to respond to a raise will call or fold
		while (priorPlayer == null || priorPlayer.isLastPlayer() == false || priorPlayer.getLastMove() != "call") {
			if (priorPlayer != null && priorPlayer.getLastMove().equals("fold")) { //in case the previous player's act was a fold
				if (this.activePlayers.get(this.activePlayers.indexOf(priorPlayer)).isLastPlayer() == true) { //if he was the last player to act & folded, exit this loop!
					break;
				}
				this.activePlayers.remove(priorPlayer); //remove the folded player
			}
			actingPlayer.act(); //act-method determines the action of the currently acting Player: fold,call(check),raise
			
			//Now we determine the position of the next player to act within our activePlayerList
			int currentPlayerIndex = (this.activePlayers.indexOf(actingPlayer));
			if (currentPlayerIndex == this.activePlayers.size() - 1) {
				nextPlayerIndex = 0;
			} else {
				nextPlayerIndex = currentPlayerIndex + 1;
			}
			priorPlayer = this.activePlayers.get(currentPlayerIndex); //priorPlayer is the player that last acted (either called,folded,raised,..)

			actingPlayer = this.activePlayers.get(nextPlayerIndex); //actingPlayer is now the new player to act

		}
		// test-Syso
		System.out.println("nextRound!"); // all betting for this round has been completed, it is now on to the next round! 

		//TODO: From here, get into the "nextRound"; Adjust currentRound, deal the proper hands, reset bettingAmount and start a new betting round,...


	}

	public void removePlayer(Seat s) {
		this.seatedPlayers.remove(s);

		int amountOfPlayers = Seat.getAmountOfPlayers() - 1;
		Seat.setAmountOfPlayers(amountOfPlayers);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dealHands() {
		if(this.currentRound == eRound.ROUNDEND) {
			identifyHands();
		} else if (this.currentRound == eRound.PREFLOP) {
			int seatCounter = 0; 
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < activePlayers.size(); j++) {
					activePlayers.get(seatCounter).addCard(carddeck.remove(0));
					seatCounter = getNextIndex(seatCounter, seatedPlayers);
				}
			}
		} else if (this.currentRound == eRound.FLOP) {
			removeCard(); // burn 1 card
			this.comCards.addCard(this.carddeck.remove(0));
			this.comCards.addCard(this.carddeck.remove(0));
			this.comCards.addCard(this.carddeck.remove(0));
			
		} else if (this.currentRound == eRound.TURN) {
			removeCard(); // burn 1 card
			this.comCards.addCard(this.carddeck.remove(0));
			
		} else if (this.currentRound == eRound.RIVER) {
			removeCard(); // burn 1 card
			this.comCards.addCard(this.carddeck.remove(0));
		}
		bettingRound();
	}

	@Override
	public void addPlayer(String name) {
		boolean playerAdded = false;
		if(name != null && name.length() >= 2) {
			for(int i = 0; i < seatedPlayers.size(); i++) {
				String playerName = seatedPlayers.get(i).getName();
				if (playerName == null || playerName.isEmpty()) {
					seatedPlayers.get(i).setName(name);
					System.out.println("Placetaking: " + seatedPlayers.get(i).toString());
					playerAdded = true;
					break;
				}
			}
			if(playerAdded == false) { //Indicator that max amount of players has been reached, because new player could not be added
				throw new RuntimeException("Max amount of players reached");
			}
		}
		else {
			throw new RuntimeException("Name is not allowed to be null or have less than 2 symbols");
		}
	}

	@Override
	public void startGame() {
		// Give a random player small- and bigblind
		Random rand = new Random();
		int index = rand.nextInt((seatedPlayers.size() - 1));
		Seat sBPlayer = seatedPlayers.get(index);
		sBPlayer.setBlind(this.smallBlind);

		index = this.getNextIndex(index, seatedPlayers);

		Seat bBPlayer = seatedPlayers.get(index);
		bBPlayer.setBlind(this.bigBlind);

		// Syso-Test Are players added properly?
		System.out.println("currently Seated players are: " + seatedPlayers.toString());
	}

}
