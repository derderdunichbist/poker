package Backend;

import java.util.ArrayList;
import java.util.Scanner;

public class Seat {

	private String playerName;
	private int seatNumber;
	private static int amountOfPlayers = 0;
	private Blind blind;
	private int chips;
	private int bettedAmount = 0; // amount of bets in $ (in current round!); is 0 on roundstart
	private ArrayList<Card> cardsOnHand; // Array List used instead of Array for easy emptying
	private Poker p;
	private boolean isLastPlayer = false;
	private String lastMove=""; //to determine whether or not his last move was a call,raise or fold: Important in ending the round
	
	public boolean isLastPlayer() {
		return isLastPlayer;
	}

	public Seat(String playerName,Poker p) {
		
		this.p = p;
		this.cardsOnHand = new ArrayList<Card>(2);
		
		if (amountOfPlayers < 6 && playerName != null) {
			setName(playerName);
			amountOfPlayers++;
			setSeatNumber(amountOfPlayers);
		} else {
			throw new RuntimeException("No more than 6 Players!");
		}
	}

	public static int getAmountOfPlayers() {
		return amountOfPlayers;
	}

	public static void setAmountOfPlayers(int amountOfPlayers) {
		Seat.amountOfPlayers = amountOfPlayers;
	}
	
	public void act(){
		System.out.println("Player "+this.getName()+" it is your turn! "+(p.getToCall()-(this.bettedAmount))+" to call!");
		//TODO: This is only temporary. As well as the checking/folding with 0!
		System.out.println("enter your bet or '0' to check/fold :");
		
		Scanner reader = new Scanner(System.in); 
		int n = reader.nextInt();
		
		if(n > p.getToCall()-this.bettedAmount){
			bet(n);
		}
		else if(n == p.getToCall()-this.bettedAmount){
			call(n);
		}
		else if(n < p.getToCall()-this.bettedAmount && n > 0)
		{
			throw new RuntimeException("Not a valid bet! Bet at least enough to call or fold!");
		}
		else{
			fold();
		}
	}

	public void call(int amount) {
			this.bettedAmount += amount;
			this.setLastMove("call");
			System.out.println("Player "+this.getName()+" called!");
	}

	public void bet(int amount) {
		//TODO: the current bet is not yet connected to the players stack or the 
		//TODO: poker-games pot!
		
			this.bettedAmount += amount;
			p.setToCall(bettedAmount);
			this.setLastMove("bet");
			System.out.println("Player "+this.getName()+" raised to"+amount);
			
			//re-sort list, so that last betting player is always last to act
			ArrayList<Seat> activePlayers = p.getActivePlayers();
			
			//Delete old lastPlayer
			for(Seat player: activePlayers){
					player.isLastPlayer=false;
			}
			// (this) is the last player to make a bet
			int lastToBetPlayerPosition = activePlayers.indexOf(this);
			int lastPlayerPosition;
			
			//player left to lastToBet is then the last Player:
			if(lastToBetPlayerPosition == 0){
				lastPlayerPosition = activePlayers.size()-1;
			}
			else{
				lastPlayerPosition = lastToBetPlayerPosition-1;
			}
			
			activePlayers.get(lastPlayerPosition).isLastPlayer=true;
			
			for(Seat player: activePlayers){
				System.out.println(player.getName()+" is "+ player.isLastPlayer);
		}
			
	}

	private void fold() {
		this.setLastMove("fold");
		System.out.println("Player "+this.getName()+" folded his hand");
		//betting-Round folds the player, as method needs further information from currently active playerobject
	}

	public String getName() {
		return playerName;
	}

	private void setName(String playerName) {
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

	public void setChips(int chips) {
		this.chips = chips;
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

}
