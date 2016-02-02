package Backend;

public class Card {
	
private	boolean isHidden;
private eValue value; 
private eColour color; 
private String name; //Contains both Color- and Value of card

public Card(eColour color,eValue value){ //set Cards Value and Color when constructing a card
	if(color != null && value != null){
		this.setColor(color);
		this.setValue(value);
		this.setName(color,value);
	}
	else{
		throw new NullPointerException("Color and Value must not be null!");
	}
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

public String getName() {
	return name;
}

private void setName(eColour color, eValue value) {
	//Example: "ACE DIAMONDS"
	this.name = color.toString()+" "+value.toString();
}

@Override
public String toString(){
	return color.toString()+" "+value.toString();
}

}
