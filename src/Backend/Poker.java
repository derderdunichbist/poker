package Backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Interfaces.iController;

public class Poker implements iController {
	/**
	 * Determines if there is already a winner or not. If there is a winner, the round ends, Pot and stacks are updated, rounds reset
	 */
	private boolean winner=false;

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
		this.seatedPlayers = new ArrayList<Seat>();
		this.activePlayers = new ArrayList<Seat>();
		this.setCarddeck(new ArrayList<Card>(AMOUNT_OF_CARDS));
		this.currentRound = eRound.INITIALIZE;
		this.comCards = new CommunityCards();
		this.init();
	}

	
	public void init() {

		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(smallBlindValue);
		this.initSeats();
	}

	/**
	 * initialize the Seats for the players. Players can either be active or inactive on a seat, however every seat is used at all times or removed.
	 */
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
	
	/**
	 * newRound() resets the Round (such as updating stacks, resetting variables etc) thus preparing a newRound. 
	 * NewRound is called after a Round is finished and a new Round should begin. 
	 */
	public void newRound() {
		int newSmallBlind = 0; // Saves index of the seat to receive smallBlind next
		int newBigBlind = 0;	// Saves index of the seat to receive bigBlind next	
		this.pot = 0;//There have not been bets yet, pot starts with 0.

		winner=false; //Given that a new round is started, reset winner:Boolean to false
		
		for(Seat s: this.seatedPlayers){ //For a new round, nobody should have cards! Remove all cards out of players' hands.
			s.removeCards();
			s.setBettedAmount(0); //For a new Round, nobody has betted yet!
			s.setEntireBettedAmount(0); //For a new Round, the "sum of all betted-Amounts" per round is also 0
			s.setAllin(false);
		}
			
		
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
	/**
	 * identifyHands() is used if the winner has to be determined via showdown (either at roundend, an allin-situation or splitpot)
	 */
	private void identifyHands() {
		System.out.println("IdentifyHands() is not yet implemented. Here its functionality usually kicks in.");
		//TODO: implement identifyHands()
	}

	/**
	 *  removeDealtCard and burnCard with same implementation became removeCard
	 */
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
					this.activePlayers.remove(priorPlayer);
					if(activePlayers.size()==1){//Is there only one player left? -> Then we have to determine a winner
						winner=true;
					}
					break; //exit while loop
				}
					this.activePlayers.remove(priorPlayer);
					if(activePlayers.size()==1){//Is there only one player left? -> Then we have to determine a winner
						winner=true;
						break;
					}	
				}
			
			int counter=0;//How many players are allin? 
			for(Seat s: this.activePlayers){//Is everyone allin, except for one player? If so, he does not continue betting alone!
				if(s.isAllin()==false){
					counter++;
				}
			}
			if(counter==0){//If everyone is allin, skip the while-loop. This is important because we enter this while-loop
				//Every fresh round (Preflop,Flop,Turn and River) and need a way to skip it. 
				break;
			}
			else if(counter==1 && actingPlayer.getBettedAmount()>=this.toCall){//There is only one player left that is NOT allin! 
				actingPlayer.setAllin(true);//To skip all his betting, we set him into "allin-state" 
				//note: this does not commit the rest of his stack, but is simply a way for us to skip his bettings
				break;
			}
				
			if(actingPlayer.isAllin()!=true){
			actingPlayer.act(); //act-method determines the action of the currently acting Player: fold,call(check),raise
			}
			//Now we determine the position of the next player to act within our activePlayerList
			int currentPlayerIndex = (this.activePlayers.indexOf(actingPlayer));
			if (currentPlayerIndex == this.activePlayers.size() - 1) {
				nextPlayerIndex = 0;
			} else {
				nextPlayerIndex = currentPlayerIndex + 1;
			}
			priorPlayer = this.activePlayers.get(currentPlayerIndex); //priorPlayer is the player that last acted (either called,folded,raised,..)

			actingPlayer = this.activePlayers.get(nextPlayerIndex); //actingPlayer is now the new player to act
			
			System.out.println(priorPlayer);

		}

		for(Seat seated: seatedPlayers){
			seated.setBettedAmount(0); //reset betted Amount of every player to 0 for a fresh betting round
		}
		this.toCall=0; //reset Amount to be called to 0 for a fresh betting round!

		if(this.winner==false){//We do not have a winner yet
			
			System.out.println("nextRound!"); // all betting for this round has been completed, it is now on to the next round
			
				this.currentRound = eRound.values()[this.currentRound.ordinal()+1];//Next Round! PreFlop to Flop, Flop to River etc.
				
				for(Seat s: this.activePlayers){//Reset who was last Player
					s.setLastPlayer(false);
				}
				Seat newLast = this.activePlayers.get(this.activePlayers.size()-1);
				newLast.setLastPlayer(true);//as a new Round starts, the last Player needs to be the Dealer. (important if nobody raises!)
			
			//TODO: There is no "ending" yet; River should be the last round.
			
			System.out.println(this.currentRound);
			
			dealHands(); //Deal the hands for the following round
		}
		else if(this.winner==true || this.currentRound == eRound.ROUNDEND){//We already have a winner
			determineWinner();
		}
		else{
			System.out.println("Jetzt gibts SHOWDOWN! identify hands noch nicht implementiert..");
		}
		//TODO: End the round with identifyHands() if there is more than 1 player after the last move has been made.
	
	}

	
	/**
	 * determineWinner() is being called when a round ends, that being said there is either a winner or several "winners" if the game results in a split-pot-situation.
	 * Further, there has to be a case for Allin-Scenarios in which a game can face several winners (if the smallest stack can not compete with the bigger stacks,
	 * yet held the highest hand)
	 */
	public void determineWinner(){
		if(this.activePlayers.size()==1){//There is exactly one player remaining, we have one winner
			
			Seat winner = this.activePlayers.get(0);
			String winnerName = winner.getName();
			System.out.println("The winner is : "+ winnerName);
			System.out.println(winnerName +" has won a Pot of: "+ this.pot);
			
			winner.addChips(this.pot);
			
		}
		else if(this.activePlayers.size()>1){
			identifyHands();
			//TODO: Note that at the moment, the list does not get reset if this is being called. Just as in the IF block above, this has still
			//TODO: to be implemented.
		}
			
		//TODO: All the other cases in which we have several winners, splitpots etc..
		this.winner=false; 
		newRound();
	}

	/**
	 * @deprecated
	 * @param seat The seat to be removed; only 
	 */
	private void removePlayer(Seat seat) {
		this.seatedPlayers.remove(seat);
//		seat.reset();

		int amountOfPlayers = Seat.getAmountOfPlayers() - 1;
		Seat.setAmountOfPlayers(amountOfPlayers);
		
		System.out.println("currently Seated players are: " + seatedPlayers.toString());
	}

	
	/**
	 * @deprecated
	 */
	@Override
	public void removePlayer(int seatNumber) {
		Seat seatToRemove = null;
		
		if(seatNumber < 1 || seatNumber > Poker.MAX_AMOUNT_OF_PLAYERS) {
			throw new RuntimeException("Invalid seat number!");
		}
		
		for(Seat seat : this.seatedPlayers) {
			if(seat.getSeatNumber() == seatNumber) {
				if(seat.getName() != null) {
					seatToRemove = seat;
					break;
				}
			}
		}
		
		if(seatToRemove != null) {
			this.removePlayer(seatToRemove);
		} else {
			System.out.println("No player placed on seat " + seatNumber);
		}
		
	}
	
	/**
	 * @deprecated
	 * see addPlayer for a description of the problem. 
	 */
	@Override
	public void removePlayer(String playerName) {
		Seat seatToRemove = null;
		
		if(playerName == null) {
			throw new RuntimeException ("Illegal Argument NULL for Player Name!");
		}
		
		for(Seat seat : this.seatedPlayers) {
			if(seat.getName().equals(playerName)) {
				seatToRemove = seat;
				break;
			}
		}
		
		if(seatToRemove != null) {
			this.removePlayer(seatToRemove);
		} else {
			System.out.println("No player with name " + playerName + " found");
		}
	}

	/**
	 * dealHands(): Deals hands for each respective Round. E.g. 2 hands per player in Pre-Flop-state, 3 communityCards for the Flop...
	 */
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
		//Print out the current Community-Cards
		System.out.println("The community cards are:");
		for(Card c: comCards.getAll())
		System.out.println(c);
		
		bettingRound();
	}

	/**
	 * @deprecated
	 * Upcoming Changes: As discussed, a players name is never null or an empty string (not allowed), but a player "is"  
	 * a Seat-Object. That being said, a new player is an additional Seat-Object for our seatedPlayers<>, and analogous functions the removal
	 * of a player from the table.  
	 */
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
		
		this.newRound();
		
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

	public void addToPot(int amount) {
		this.pot += amount; // betting or call amount added to pot
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

}
