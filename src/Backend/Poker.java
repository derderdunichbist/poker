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
	private ArrayList<Seat> seatedPlayers;
	
	public Poker(){
		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(2*smallBlindValue);
		seatedPlayers = new ArrayList<Seat>(6);
		
		setCarddeck(new ArrayList<Card>(52));
		init();
		
		
		//For testing-purposes, we will create the players here for now:
		this.addPlayer(new Seat("Benni"));
		this.addPlayer(new Seat("DeGagBenni"));
		this.addPlayer(new Seat("leGrandBrunBrun"));
		this.addPlayer(new Seat("BenniDiGaga"));
		this.addPlayer(new Seat("Berndsaftstinker"));
		
		//Syso-Test Are players added properly? 
		System.out.println(seatedPlayers.toString());
	}
		
	//Following listed methods are not yet fully determined
	//neither in name,implementation nor order! ------{
	public void init(){
		resetDeck();
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
	
	
	public void resetRound(){
		resetDeck();
	}
	
	public void dealHands(){
		
	}
	
	public void burnCards(){
		
	}
	
	public void bettingRound(){
		
	}
	
	public void beginRound(){
		
	}
	//}-------
	
	public void addPlayer(Seat s){
		if(s!=null && this.seatedPlayers.size()<5){
			seatedPlayers.add(s);	
		}
		else{
			throw new NullPointerException("Player cannot be seated! Seat is either null or no more space!");
		}
	}
	
	public void removePlayer(Seat s){
		seatedPlayers.remove(s);
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

}
