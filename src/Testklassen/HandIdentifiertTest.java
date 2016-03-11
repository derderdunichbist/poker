package Testklassen;

import java.util.ArrayList;

import Backend.Card;
import Backend.CommunityCards;
import Backend.HandIdentifier;
import Backend.Poker;
import Backend.Seat;
import Backend.eColor;
import Backend.eHandCategory;
import Backend.eValue;

public class HandIdentifiertTest {
	public static void main(String[] args) {
		Poker poker = new Poker();
//
//		poker.addPlayer("Benni");
//		poker.addPlayer("DeGagBenni");
//		poker.addPlayer("leGrandBrunBrun");
//		poker.addPlayer("BenniDiGaga");
//		poker.addPlayer("Berndsaftstinker");
//		poker.addPlayer("Berndsaftgagrotzfotz");
//		
//		poker.resetDeck();
//		int seatCounter = 0;
//		for (int i = 0; i < 2; i++) {
//			for (int j = 0; j < poker.getSeatedPlayers().size(); j++) {
//				poker.getSeatedPlayers().get(seatCounter).addCard(poker.getCarddeck().remove(0));
//				seatCounter = poker.getNextIndex(seatCounter, poker.getSeatedPlayers());
//			}
//		}
//		
//		Seat seat = poker.getSeatedPlayers().get(3);
//		CommunityCards comCards = new CommunityCards();
//		
//		for(int i = 0; i < 5; i++) {
//			comCards.addCard(poker.getCarddeck().remove(0));
//		}
//		poker.setComCards(comCards);
		
		HandIdentifier hi = new HandIdentifier(poker);
		
		Card c1 = new Card(eValue.FIVE, eColor.CLUBS);
		Card c2 = new Card(eValue.THREE, eColor.CLUBS);
		Card c3 = new Card(eValue.EIGHT, eColor.CLUBS);
		Card c4 = new Card(eValue.SIX, eColor.CLUBS);
		Card c5 = new Card(eValue.FOUR, eColor.CLUBS);
		Card c6 = new Card(eValue.FIVE, eColor.SPADES);
		Card c7 = new Card(eValue.TWO, eColor.SPADES);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(c1);
		cards.add(c2);
		cards.add(c3);
		cards.add(c4);
		cards.add(c5);
		cards.add(c6);
		cards.add(c7);
		
		hi.getWinner(cards);
	}
}
