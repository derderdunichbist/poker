package Backend;

import java.util.ArrayList;

public class CommunityCards {

	private ArrayList<Card> communityCards;

	public CommunityCards() {
		communityCards = new ArrayList<Card>(5);
	}

	public void addCard(Card c) {
		if (communityCards.size() < 5 && c != null) {
			communityCards.add(c);
		} else {
			throw new RuntimeException("Cannot set Card. Card is either null or already 5 ComCards!");
		}
	}
	
	public void reset(){
		this.communityCards.clear();
	}

	// create me an Array of all flop-cards
	public Card[] getFlop() {
		Card[] cards = (Card[]) this.communityCards.toArray();
		Card[] flop = new Card[3];

		if (cards != null && cards.length > 2) {

			for (int i = 0; i < flop.length - 1; i++) {
				flop[i] = (Card) cards[i];
			}
			return flop;
		} else {
			throw new RuntimeException("List does not contain flopcards!");
		}
	}

	// create me an Array of all cards
	public ArrayList<Card> getAll() {
		return this.communityCards;
	}

	public Card getTurn() {
		if (communityCards.size() > 3) {
			Card c = this.communityCards.get(3);
			return c;
		} else {
			throw new RuntimeException("List does not contain Turncard!");
		}
	}

	public Card getRiver() {
		if (communityCards.size() > 4) {
			Card c = this.communityCards.get(4);
			return c;
		} else {
			throw new RuntimeException("List does not contain Rivercard!");
		}
	}
}
