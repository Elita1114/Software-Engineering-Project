package common;

import java.io.Serializable;

public class Complaint implements Serializable {
	public String title;
	public String description;
	public boolean status;
	public double refund;
	public int userID;
	public String reply;
	public int complaintID;
	public int timer; // in minutes
	
	
	public Complaint(String title_,String description_,boolean status_,double refund_,int userID_) {
		title=title_;
		description=description_;
		status=status_;
		refund=refund_;
		userID=userID_;
		reply="";
		complaintID=0;
		timer=24*60;
	}
	
	
	public Complaint(String title_,String description_,boolean status_,double refund_,int userID_,String reply_,int complaintID_,int timer_) {
		title=title_;
		description=description_;
		status=status_;
		refund=refund_;
		userID=userID_;
		reply=reply_;
		complaintID=complaintID_;
		timer=timer_;
	}

}