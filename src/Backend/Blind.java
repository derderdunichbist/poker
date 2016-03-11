package Backend;

public class Blind {

	private int value;
	private String type;
	private static int amountOfBlinds = 0;

	public Blind(int blindValue) {
		amountOfBlinds++;

		if (amountOfBlinds == 1) {
			this.setType("smallBlind");
			this.setValue(blindValue);
		} else if (amountOfBlinds == 2) {
			this.setType("bigBlind");
			this.setValue(2 * blindValue);
		} else {
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
	
	public static void setAmountOfBlinds(int amountOfBlinds){
		Blind.amountOfBlinds = amountOfBlinds;
	}

	// leave this public for now, in case of dynamically changing value later on
	// TODO Big Blind implementation
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return this.type + " with value " + this.value;
	}

}
