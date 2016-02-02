package Backend;

public class Poker {
	
	private int pot;
	private int toCall;
	private Blind smallBlind;
	private Blind bigBlind;
	private int smallBlindValue = 50;
	
	public Poker(){
		smallBlind = new Blind(smallBlindValue);
		bigBlind = new Blind(2*smallBlindValue);
	}
	
	//Following listed methods are not yet fully determined
	//neither in name,implementation nor order! ------{
	public void burnCards(){
		
	}
	
	public void resetRound(){
		
	}
	
	public void dealHands(){
		
	}
	
	public void beginRound(){
		
	}
	//}-------

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

}
