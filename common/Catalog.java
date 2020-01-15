package common;

import java.io.Serializable;
import java.util.ArrayList;



public class Catalog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4717455994964419464L;
	private ArrayList<CatalogItem> itemList;
	private boolean flag;
	
	public Catalog(ArrayList<CatalogItem> itemList_,boolean flag_) {
		itemList=itemList_;
		flag=flag_;
	}
	
	public ArrayList<CatalogItem> getList(){
		return itemList;
	}
	public boolean getFlag() {
		return flag;
	}
	public String toString() {
		return "#gotCatalog";
	}
}
