package common;

import java.io.Serializable;

public class CustomIteam  extends Item implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1218774016502592391L;
	private String type;
	// private Range<Integer> price_range
	
	public CustomIteam(String type, String description, String color) {
		super(-1,"", description, color);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public boolean equals(Object obj)
	{
		  if(this == obj) 
	            return true; 
	      if(obj == null || obj.getClass()!= this.getClass()) 
	           return false; 
	      // type casting of the argument.  
	      CustomIteam other_item = (CustomIteam) obj; 	          
	      // comparing the state of argument with  
	      // the state of 'this' Object. 
	      return (other_item.type == this.type && super.equals(obj)); 
	}
	
	
}
