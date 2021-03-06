package common;

import java.io.Serializable;

public class CatalogItem extends Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3897068652305367050L;
	private String imagePath;
	private float sale;
	private int store;

	
	
	public CatalogItem(String name_, String description_, String color_,float price_,int id_,String imagePath_, int type_, int store_, float sale_) {
		super( id_,  name_,  description_,  color_,  type_, price_);
		// TODO Auto-generated constructor stub
		imagePath=imagePath_;
		setStore(store_);
		setSale(sale_);
	}
	
	public CatalogItem(int id_, String name_, String description_, int type_, float price_, float sale_){
		super(id_,name_,description_,type_,price_);
		sale = sale_;
	}
	
	public CatalogItem(int id_, String name_, String description_, int type_, float price_, float sale_, String color_){
		super(id_,name_,description_,color_,type_,price_);
		sale = sale_;
	}
	
	public CatalogItem(int id_, String name_, String description_, int type_, float price_){
		super(id_,name_,description_,type_,price_);
	}

	

	public String getImagePath() {
		return imagePath;
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
	      return (other_item.getPrice() == this.getPrice() && other_item.imagePath == this.imagePath && other_item.getSale() == this.getSale() && other_item.getStore() == this.getStore() 
	    		  && super.equals(obj)); 
	}



	public int getStore() {
		return store;
	}

	public void setStore(int store) {
		this.store = store;
	}

	public float getSale() {
		return sale;
	}

	public void setSale(float sale) {
		this.sale = sale;
	}

}