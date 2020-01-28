package common;

import java.io.Serializable;

public class CustomItem  extends Item implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1218774016502592391L;
	// private Range<Integer> price_range
	
	public CustomItem(int type, String description, String color) {
		super(-1, "", description, color,  type);
	}

	public CustomItem(int id_, String name_, String description_, int type_, float price_){
		super(id_,name_,description_,type_,price_);
	}
	
	public boolean equals(Object obj)
	{
		  if(this == obj) 
	            return true; 
	      if(obj == null || obj.getClass()!= this.getClass()) 
	           return false; 
	      // type casting of the argument.  
	      CustomItem other_item = (CustomItem) obj; 	          
	      // comparing the state of argument with  
	      // the state of 'this' Object. 
	      return ( super.equals(obj)); 
	}
	
	
}
