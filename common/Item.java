package common;

import java.io.Serializable;

public class Item implements Serializable{
	private String name;
	private String description;
	private String color;
	
	public Item(String name_, String description_, String color_){
		name=name_;
		description=description_;
		color=color_;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public String getColor() {
		return color;
	}
	
	public void setName(String name_) {
		name=name_;
	}
	public void setDescription(String description_) {
		description=description_;
	}
	public void setColor(String color_) {
		color=color_;
	}
	
}
