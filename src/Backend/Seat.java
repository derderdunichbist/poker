package Backend;

import java.util.ArrayList;

public class Seat {

	private String playerName;
	private int seatNumber;
	private static int amountOfPlayers = 0;
	private Blind blind;
	private int chips;
	private int bettedAmount = 0; // amount of bets in $ (in current round!); is 0 on roundstart
	private ArrayList<Card> cardsOnHand; // Array List used instead of Array for easy emptying

	public Seat(String playerName) {
		
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

	public void call() {

	}

	public void bet() {

	}

	public void fold() {

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

}
