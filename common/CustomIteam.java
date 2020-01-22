package common;

import java.io.Serializable;

public class CustomIteam  extends Item implements Serializable{
	

	private String type;
	// private Range<Integer> price_range
	
	public CustomIteam(String type, String description, String color) {
		super("", description, color);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
