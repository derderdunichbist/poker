package Backend;

public class Blind {
	
	private int value;
	private String type;
	private static int amountOfBlinds = 0;

	public Blind(int blindValue) {
		amountOfBlinds++;
		
		if(amountOfBlinds==1){
			this.setType("smallBlind");
			this.setValue(blindValue);
		}
		else if(amountOfBlinds==2){
			this.setType("bigBlind");
			this.setValue(blindValue);
		}
		else{
			throw new RuntimeException("You can only create 2 Blinds!");
		}
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}
//leave this public for now, in case of dynamically changing value later on		
	public void setValue(int value) {
		this.value = value;
	}

}
