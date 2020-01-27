package common;

import java.io.Serializable;

public class CustomItem  extends Item implements Serializable{
	private int userID;	
	private int Daisy;
	private int Orchid;
	private int Iris;
	private int Rose;
	private int Lily;
	private int hydrangea;
	private int orderID;
	/**
	 * 
	 */
	private static final long serialVersionUID = -1218774016502592391L;
	// private Range<Integer> price_range
	
	public CustomItem(int type, String description, String color,int userID_,int Daisy_,int Orchid_,int Iris_,int Rose_,int Lily_,int hydrangea_,int orderID_) {
		super(-1, "", description, color,  type);
		userID=userID_;	
		Daisy=Daisy_;
		Orchid=Orchid_;
		Iris=Iris_;
		Rose=Rose_;
		Lily=Lily_;
		hydrangea=hydrangea_;
		orderID=orderID_;
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



	public int getUserID() {
		return userID;
	}



	public void setUserID(int userID) {
		this.userID = userID;
	}



	public int getDaisy() {
		return Daisy;
	}



	public void setDaisy(int daisy) {
		Daisy = daisy;
	}



	public int getOrchid() {
		return Orchid;
	}



	public void setOrchid(int orchid) {
		Orchid = orchid;
	}



	public int getIris() {
		return Iris;
	}



	public void setIris(int iris) {
		Iris = iris;
	}



	public int getRose() {
		return Rose;
	}



	public void setRose(int rose) {
		Rose = rose;
	}



	public int getLily() {
		return Lily;
	}



	public void setLily(int lily) {
		Lily = lily;
	}



	public int getHydrangea() {
		return hydrangea;
	}



	public void setHydrangea(int hydrangea) {
		this.hydrangea = hydrangea;
	}



	public int getOrderID() {
		return orderID;
	}



	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
	
}
