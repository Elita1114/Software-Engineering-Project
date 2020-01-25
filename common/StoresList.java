package common;

import java.io.Serializable;
import java.util.ArrayList;

public class StoresList implements Serializable {

	private static final long serialVersionUID = -2424443010736555540L;
	public ArrayList<Store> stores;
	
	public StoresList(ArrayList<Store> stores_) {
		stores=stores_;
		
	}
	
	public String toString() {
		return "#gotStores";
	}
}
