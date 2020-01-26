package common;

import java.io.Serializable;
import java.util.ArrayList;

public class MonthlyReportList implements Serializable {

	public ArrayList<MonthlyReport> itemList;
	public String msg;
	
	public MonthlyReportList(ArrayList<MonthlyReport> itemList_) {
		itemList=itemList_;

		msg="#gotReports";
	}
	
	public MonthlyReportList(ArrayList<MonthlyReport> itemList_,String msg_) {
		itemList=itemList_;
		msg=msg_;
	}
	
	public String toString() {
		return msg;
	}
}
