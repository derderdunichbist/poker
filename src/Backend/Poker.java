package Backend;

import java.util.ArrayList;
import java.util.Collections;

public class Poker {
	
	private int pot;
	private int toCall;
	private Blind smallBlind;
	private Blind bigBlind;
	private int smallBlindValue = 50;
	private ArrayList<Card> carddeck;
	private ArrayList<Seat> seatedPlayers; //all players that are currently seated on the table
	private ArrayList<Seat> activePlayers; //the players that are still participating in the ongoing round (not folded)
	private eRound currentRound;
	
	public Poker(){

		this.seatedPlayers = new ArrayList<Seat>(6);
		activePlayers = new ArrayList<Seat>(6);
		setCarddeck(new ArrayList<Card>(52));
		
		init();
		
	}
		
	//Following listed methods are not yet fully determined
	//neither in name,implementation nor order! ------{
	public void init(){
		
		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(smallBlindValue);
		
		//For testing-purposes only: players created here for now!
		this.addPlayer(new Seat("Benni"));
		this.addPlayer(new Seat("DeGagBenni"));
		this.addPlayer(new Seat("leGrandBrunBrun"));
		this.addPlayer(new Seat("BenniDiGaga"));
		this.addPlayer(new Seat("Berndsaftstinker"));
		this.addPlayer(new Seat("Berndsaftgarotzfotz"));
		
		//Syso-Test Are players added properly? 
		System.out.println("currently Seated players are: "+seatedPlayers.toString());
		
		newRound();
	}
	
	public void resetDeck(){
		
		if(carddeck.size()>0){
			carddeck.clear();
		}
		
		for(eValue value: eValue.values()){
			for(eColour color: eColour.values()){
				Card c = new Card(color,value);
				carddeck.add(c);
			}
		}
		Collections.shuffle(carddeck);
		
		//test-syso: does deck work properly?
		System.out.println(carddeck);
	}

	//A new round begins after a complete hand is finished
	public void newRound(){
		
		resetDeck(); 
		setCurrentRound(eRound.PREFLOP);
		
		//remove blinds from players
		for(Seat s: seatedPlayers){
			s.setBlind(null);
		}
		
		//someone gets the blinds..............................................[!!!!!]
		//impl: random number 1-6 gets SB, next index gets BB

		
		//set all players currently on the table to participate in the upcoming round
		activePlayers.clear();
		
		//{----------------------------------FIRST, LOOK FOR SMALLBLIND, SEAT HIM FIRST!---------------}
		for(Seat s: seatedPlayers){
			if(s.getBlind!=null){
				
			}
			//activePlayers.add(s);
		}
		
		//test-syso
		System.out.println("Still participating in the current round are: "+ activePlayers);
	}
	
	public void dealHands(){
		
	}
	
	public void burnCards(){
		
	}
	
	public void bettingRound(){
		
	}

	//}-------
	
	public void addPlayer(Seat s){
		if(s!=null && this.seatedPlayers.size()<6){
			seatedPlayers.add(s);	
		}
		else{
			throw new NullPointerException("Player cannot be seated! Seat is either null or no more space!");
		}
	}
	
	public void removePlayer(Seat s){
		seatedPlayers.remove(s);
		
		int amountOfPlayers = Seat.getAmountOfPlayers()-1;
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
	
//used when a player raises
	public void setToCall(int toCall) {
		this.toCall = toCall;
	}

	public Blind getSmallBlind() {
		return smallBlind;
	}

//Leave this here for now, in case we want to dynamically change blinds later on
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

}
