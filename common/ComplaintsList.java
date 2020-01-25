package common;

import java.io.Serializable;
import java.util.ArrayList;

public class ComplaintsList implements Serializable {

	public ArrayList<Complaint> itemList;
	public String msg;
	
	public ComplaintsList(ArrayList<Complaint> itemList_) {
		itemList=itemList_;

		msg="#gotComplaints";
	}
	
	public ComplaintsList(ArrayList<Complaint> itemList_,String msg_) {
		itemList=itemList_;
		msg=msg_;
	}
	
	public String toString() {
		return msg;
	}
}
