package Testklassen;

import Backend.Poker;
import Interfaces.iController;

public class runPoker {

	public static void main(String[] args) {
		iController poker = new Poker();
		poker.addPlayer("Benni");
		poker.addPlayer("DeGagBenni");
		poker.addPlayer("leGrandBrunBrun");
		poker.addPlayer("BenniDiGaga");
		poker.addPlayer("Berndsaftstinker");
		poker.addPlayer("Berndsaftgagrotzfotz");
		
		poker.newRound();

	}

}
