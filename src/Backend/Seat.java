package Backend;

import java.util.ArrayList;
import java.util.Scanner;

public class Seat {

	private String playerName;
	private int seatNumber;
	private static int amountOfPlayers = 0;
	private Blind blind;
	private int chips = 2500;
	/**
	 * isAllin is true if a player is allin and thus can not bet any further. He is skipped for any further bets but maintains
	 * in the betting round until the end.
	 */
	private boolean isAllin = false;
	/**
	 * entireBettedAmount is the entirety of a players betted amount and thus the sum of all bettedAmounts over all rounds. 
	 * entireBettedAmount lets us determine the maximum winning-amount for the player in determineWinner() later on.
	 */
	private int entireBettedAmount = 0;

	/**
	 * amount of bets in $ (in current round!); is 0 on roundstart
	 */
	private int bettedAmount = 0; 
	private ArrayList<Card> cardsOnHand; // Array List used instead of Array for easy emptying
	private Poker poker;
	private boolean isLastPlayer = false;
	private String lastMove=""; //to determine whether or not his last move was a call,raise or fold: Important in ending the round
	
	public boolean isLastPlayer() {
		return isLastPlayer;
	}

	public Seat(Poker poker) {
		Seat.amountOfPlayers++;
		this.cardsOnHand = new ArrayList<Card>(2);
		this.setSeatNumber(amountOfPlayers);
		this.poker = poker;
	}

	public static int getAmountOfPlayers() {
		return Seat.amountOfPlayers;
	}
/**
 * Set the amount that the respective Player has betted in only the current round! (rounds are for example: Flop,Turn,..)
 * @param amountOfPlayers
 */
	public static void setAmountOfPlayers(int amountOfPlayers) {
		Seat.amountOfPlayers = amountOfPlayers;
	}
	/**
	 * act() determines whether a player raised,called(checked) or folded
	 */
	public void act(){
		System.out.println("Current pot: " + poker.getPot());
		System.out.println("Player "+this.getName()+" it is your turn! "+(poker.getToCall()-(this.bettedAmount))+" to call!");
		System.out.println("Current chips of " + this.playerName + " : " + this.chips);
		
		//TODO: This is only temporary. As well as the checking/folding with 0!
		System.out.println("enter your bet or '0' to check/fold :");
		
		Scanner reader = new Scanner(System.in); 
		int n = reader.nextInt();
		
		if(n > poker.getToCall()-this.bettedAmount || this.chips-n==0){//Player is either raising (pays more than needed to call) or allinning
			bet(n);
		}
		else if(n == poker.getToCall()-this.bettedAmount){
			call(n);
		}
		else if(n < poker.getToCall()-this.bettedAmount && n > 0)
		{
			throw new RuntimeException("Not a valid bet! Bet at least enough to call or fold!");
		}
		else{
			fold();
		}
	}
	public int getBettedAmount() {
		return bettedAmount;
	}

	public void setBettedAmount(int bettedAmount) {
		this.bettedAmount = bettedAmount;
	}

	/**
	 * call(amount) is called when a player calls a bet
	 * @param amount: the amount of the bet
	 */
	public void call(int amount) {
		if (amount > chips) {
			throw new RuntimeException("Too high amount to call");
		}
			this.chips -= amount;
			this.bettedAmount += amount;
			this.entireBettedAmount += amount; //The only difference to bettedAmount is that this is not being reset to 0 with every roundstart
			this.setLastMove("call");
			if(poker.getToCall()==0){
				System.out.println("Player "+this.getName()+" checked his Hand!");
			}
			else{
			System.out.println("Player "+this.getName()+" called!");
			}
			System.out.println(" ");
			
			
			
			System.out.println("Current chips " + this.playerName + ": " + this.chips);
			
			poker.addToPot(amount);
			
			
	}
	/**
	 * Called when a player is betting. Also properly determines who the lastPlayerToAct is, in relation to the last raiser
	 * @param amount: the amount of the bet (not the raise!)
	 */
	public void bet(int amount) {
			if (amount > chips) {
				throw new RuntimeException("Too high amount to bet");
			}
			this.bettedAmount += amount;
			this.entireBettedAmount += amount; //The only difference to bettedAmount is that this is not being reset to 0 with every roundstart
			poker.setToCall(bettedAmount);
			this.setLastMove("bet");
			
			if(this.chips-amount==0){//The player went allin
				System.out.println("Player "+this.getName()+" went allin with "+amount);
				this.isAllin=true;
			}
			else{ //The player raised
				System.out.println("Player "+this.getName()+" raised to "+amount);
			}
			
			System.out.println(" ");
			
			//Get activePlayersList, so we can determine the new lastPlayerToAct 
			ArrayList<Seat> activePlayers = poker.getActivePlayers();
			
			//reset all isLastPlayer attributes
			for(Seat player: activePlayers){
					player.isLastPlayer=false;
			}
			int lastToBetPlayerPosition = activePlayers.indexOf(this); //the index of the last player to bet
			int lastPlayerPosition; 
			
			//player left to lastToBet is then the last Player:
			if(lastToBetPlayerPosition == 0){
				lastPlayerPosition = activePlayers.size()-1;
			}
			else{
				lastPlayerPosition = lastToBetPlayerPosition-1;
			}
			
			activePlayers.get(lastPlayerPosition).isLastPlayer=true; //set the player seated left to the last raiser as lastPlayer
	
			this.chips -= amount;
			poker.addToPot(amount);
			
			System.out.println("Current chips of "+this.playerName+ "after his bet: " + this.chips);
			System.out.println();
			
	}

	private void fold() {
		this.setLastMove("fold"); //Its important that in "Poker" we know what the last move of every player was
		//BettingRound()-Method will fold the player if the last move was fold!
		System.out.println("Player "+this.getName()+" folded his hand");
		System.out.println(" ");
		
		

	}

	public void setLastPlayer(boolean isLastPlayer) {
		this.isLastPlayer = isLastPlayer;
	}
	public String getName() {
		return playerName;
	}

	public void setName(String playerName) {
		if (playerName != null) {
			this.playerName = playerName;
		}
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	// In case of moving players from seat to seat, we keep this method public for now
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public int getChips() {
		return chips;
	}

	public void addChips(int amount) {
		this.chips += amount;
	}

	@Override
	public String toString() {
		return playerName + " on seat " + seatNumber;
	}

	public Blind getBlind() {
		return blind;
	}

	public void setBlind(Blind blind) {
		this.blind = blind;
	}

	public ArrayList<Card> getCards() {
		return cardsOnHand;
	}

	private void setCards(ArrayList<Card> cards) {
		this.cardsOnHand = cards;
	}
	
	public void addCard(Card card){
		if (cardsOnHand != null && cardsOnHand.size() < 2) {
			cardsOnHand.add(card);
		} else {
			throw new RuntimeException ("Cards list is either null or already full (2 cards)!");
		}
	}

	public String getLastMove() {
		return lastMove;
	}

	public void setLastMove(String lastMove) {
		if(lastMove != null){
			this.lastMove = lastMove;
		}
	}
	
	public void removeCards(){
		this.cardsOnHand.clear();
	}

	public int getEntireBettedAmount() {
		return entireBettedAmount;
	}

	public void setEntireBettedAmount(int entireBettedAmount) {
		this.entireBettedAmount = entireBettedAmount;
	}

	public boolean isAllin() {
		return isAllin;
	}

	public void setAllin(boolean isAllin) {
		this.isAllin = isAllin;
	}

}
