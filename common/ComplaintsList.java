package common;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplaintsList implements Serializable {

	public ArrayList<Complaint> itemList;
	
	public ComplaintsList(ArrayList<Complaint> itemList_) {
		itemList=itemList_;
		
	}
	
	public String toString() {
		return "#gotComplaints";
	}
}
