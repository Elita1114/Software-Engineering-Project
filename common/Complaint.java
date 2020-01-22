package common;

import java.io.Serializable;

public class Complaint implements Serializable {
	public String title;
	public String description;
	public boolean status;
	public double refund;
	public int userID;
	
	public Complaint(String title_,String description_,boolean status_,double refund_,int userID_) {
		title=title_;
		description=description_;
		status=status_;
		refund=refund_;
		userID=userID_;
	}

}
