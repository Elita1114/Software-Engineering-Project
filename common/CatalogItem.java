package common;

import java.io.Serializable;

public class CatalogItem extends Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3897068652305367050L;
	private float price;
	private String imagePath;
	private int type;
	private float sale;

	

	
	public CatalogItem(String name_, String description_, String color_,float price_,int id_,String imagePath_, int type_, float sale_) {
		super(name_, description_, color_,id_);
		// TODO Auto-generated constructor stub
		price=price_;
		imagePath=imagePath_;
		setType(type_);
		setSale(sale_);
	}
	
	public float getPrice() {
		return price;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setPrice(float price_) {
		price=price_;
	}

	public void setImagePath(String imagePath_) {
		imagePath=imagePath_;
	}
	
	// possible errors
	public boolean equals(Object obj)
	{
		  if(this == obj) 
	            return true; 
	      if(obj == null || obj.getClass()!= this.getClass()) 
	           return false; 
	      // type casting of the argument.  
	      CatalogItem other_item = (CatalogItem) obj; 	          
	      // comparing the state of argument with  
	      // the state of 'this' Object. 
	      return (other_item.price == this.price  && other_item.imagePath == this.imagePath
	    		  && super.equals(obj)); 
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getSale() {
		return sale;
	}

	public void setSale(float sale) {
		this.sale = sale;
	}

}
