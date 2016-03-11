package Backend;

public class Card {

	private boolean isHidden;
	private eColor color;
	private eValue value;
	private String name; // Contains both Color- and Value of card

	public Card(eValue value, eColor color) { // set Cards Value and Color when
												// constructing a card
		if (value != null && color != null) {
			this.setValue(value);
			this.setColor(color);
			this.setName(value, color);
		} else {
			throw new NullPointerException("Color and Value must not be null!");
		}
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public eValue getValue() {
		return value;
	}

	public void setValue(eValue value) {
		if (value != null) {
			this.value = value;
		} else {
			throw new NullPointerException("Please set a proper color!");
		}
	}

	public eColor getColor() {
		return color;
	}

	public void setColor(eColor color) {
		if (color != null) {
			this.color = color;
		} else {
			throw new NullPointerException("Please set a proper value!");
		}
	}

	public String getName() {
		return name;
	}

	private void setName(eValue color, eColor value) {
		// Example: "ACE DIAMONDS"
		this.name = color.toString() + " " + value.toString();
	}

	@Override
	public String toString() {
		return value.toString() + " " + color.toString();
	}

}
