package Backend;

public class Card {
	
private	boolean isHidden;
private eValue value;
private eColour color;

public Card(eColour color,eValue value){ //set Cards Value and Color when constructing a card
	this.setColor(color);
	this.setValue(value);
}

public boolean isHidden() {
	return isHidden;
}

public void setHidden(boolean isHidden) {
	this.isHidden = isHidden;
}

public eColour getColor() {
	return color;
}

private void setColor(eColour color) {
	if(color != null){
	this.color = color;
}
else{
	throw new NullPointerException("Please set a proper color!");
}
}

public eValue getValue() {
	return value;
}

private void setValue(eValue value) {
	if(value != null){
		this.value = value;
	}
	else{
		throw new NullPointerException("Please set a proper value!");
	}
}

}
