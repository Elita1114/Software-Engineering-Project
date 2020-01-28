package common;

import java.io.Serializable;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 828961710358073954L;
	private int id;
	private String name;
	private String description;
	private String color;
	private float price;
	private int type;
	
	public Item(int id_, String name_, String description_, String color_, int type_){
		name=name_;
		description=description_;
		color=color_;
		type =type_;
		id=id_;
	}

	
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public Item(int id_, String name_, String description_, int type_, float price_){
		setId(id_);
		name=name_;
		description=description_;
		System.out.println(price_);
		price = price_;
		color="";
		type =type_;
	}
	
	public Item(int id_, String name_, String description_, String color_, int type_, float price_){
		setId(id_);
		name=name_;
		description=description_;
		System.out.println(price_);
		price = price_;
		color=color_;
		type =type_;
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
	      return (other_item.type == this.type && other_item.name == this.name && other_item.description == this.description && other_item.color == this.color && other_item.id == this.id); 
	}
	

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", color=" + color + "]";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}
}
