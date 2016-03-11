package Backend;

import java.util.ArrayList;

public class HandIdentifier {
	private Poker poker;

	public HandIdentifier(Poker poker) {
		this.poker = poker;
		
		for(Seat seat : poker.getSeatedPlayers()) {//TODO change to activePlayers();  test purposes only
			ArrayList<Card> allCards = new ArrayList<Card>();
			allCards.addAll(poker.getComCards().getAll());
			allCards.addAll(seat.getCards());
			seat.setHandCategory(this.getWinner(allCards));
		}
	}

	public eHandCategory getWinner(ArrayList<Card> allCards) {
		return this.checkStraightFlush(allCards);
	}

	private eHandCategory checkStraightFlush(ArrayList<Card> allCards) { //also checks RoyalFlush
		ArrayList<Card> clubs = new ArrayList<Card>();
		ArrayList<Card> diamonds = new ArrayList<Card>();
		ArrayList<Card> hearts = new ArrayList<Card>();
		ArrayList<Card> spades = new ArrayList<Card>();
		
		for(Card card : allCards) {
			switch(card.getColor()) {
			case CLUBS:
				clubs.add(card);
				break;
			case DIAMONDS:
				diamonds.add(card);
				break;
			case HEARTS: 
				hearts.add(card);
				break;
			case SPADES: 
				spades.add(card);
				break;
			}
		}
		
		if(clubs.size() >= 5) {
			determineFlush(clubs);
		} else if(diamonds.size() >=  5) {
			determineFlush(diamonds);
		} else if(hearts.size() >=  5) {
			determineFlush(hearts);
		} else if(spades.size() >=  5) {
			determineFlush(spades);
		}
			
		return this.checkFourOfAKind(allCards);
	}

	private void determineFlush(ArrayList<Card> cards) {
		cards = sortFlushCards(cards);
		int lowestIndex = cards.get(0).getValue().ordinal();
		for(int i = 1; i < cards.size(); i++) {
			if(cards.get(i).getValue().ordinal() == (lowestIndex + 1)) {
				lowestIndex ++;
			} else {
				System.out.println("No Flush");
			}
		}
	}

	/**
	 * Algorithm to sort an list of cards by its value.<br>
	 * This is needed to determine wether these cards shape a Flush or not
	 * @param allCards The card-list to be sorted
	 * @return The sorted card list
	 */
	private ArrayList<Card> sortFlushCards(ArrayList<Card> allCards) {
		ArrayList<Card> cards = new ArrayList<Card>();
		
		for(Card card : allCards) {
			boolean inserted = false;
			inserted = false;
			if(cards.isEmpty()) {
				cards.add(card);
			} else {
				for(Card c : cards){
					if(card.getValue().compareTo(c.getValue()) < 0) {
						cards.add(cards.indexOf(c) , card);
						inserted = true;
						break;
					}
				}
				if(inserted == false) {
					cards.add(cards.size() , card);
				}
			}
		}
		return cards;
	}

	private eHandCategory checkFourOfAKind(ArrayList<Card> allCards) {
		return this.checkFullHouse(allCards);
	}

	private eHandCategory checkFullHouse(ArrayList<Card> allCards) {
		return this.checkFlush(allCards);
	}

	private eHandCategory checkFlush(ArrayList<Card> allCards) {
		return this.checkStraight(allCards);
	}

	private eHandCategory checkStraight(ArrayList<Card> allCards) {
		return this.checkThreeOfAKind(allCards);
	}

	private eHandCategory checkThreeOfAKind(ArrayList<Card> allCards) {
		return this.checkTwoPair(allCards);
	}

	private eHandCategory checkTwoPair(ArrayList<Card> allCards) {
		return this.checkOnePair(allCards);
	}

	private eHandCategory checkOnePair(ArrayList<Card> allCards) {
//		for(Seat seat : poker.getSeatedPlayers()) { //TODO changeWithActiveSeats, just for test purposes
//			for(Card card : seat.getCards()) {
//				for(Card comCard : poker.getComCards().getAll()) {
//					if(comCard.getValue().equals(card.getValue())) {
//						System.out.println(seat.toString() + " pair found with cards: " + comCard.toString() + " " + card.toString());
//					}
//				}
//			}
//		}
		
		return this.checkHighCard(allCards);
	}

	private eHandCategory checkHighCard(ArrayList<Card> allCardsallCards) {
		return null;
	}

}
