package common;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 828961710358073954L;
	private int qty;
	private String name;
	private String description;
	private String color;
	
	public Item(String name_, String description_, String color_, int qty_){
		setQty(qty_);
		name=name_;
		description=description_;
		color=color_;
	}
	
	public Item(String name_, String description_, String color_){
		setQty(1);
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
	
	@Override 
	public boolean equals(Object obj)
	{
		  if(this == obj) 
	            return true; 
	      if(obj == null || obj.getClass()!= this.getClass()) 
	           return false; 
	      // type casting of the argument.  
	      Item other_item = (Item) obj; 	          
	      // comparing the state of argument with  
	      // the state of 'this' Object. 
	      return (other_item.name == this.name && other_item.description == this.description && other_item.color == this.color); 
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
