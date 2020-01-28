package common;

import java.io.Serializable;
import java.util.ArrayList;

public class OrdersList implements Serializable {

	public ArrayList<Order> itemList;
	public String msg;
	
	public OrdersList(ArrayList<Order> itemList_) {
		itemList=itemList_;

		msg="#gotOrders";
	}
	
	public OrdersList(ArrayList<Order> itemList_,String msg_) {
		itemList=itemList_;
		msg=msg_;
	}
	
	public String toString() {
		return msg;
	}

	public ArrayList<Order> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Order> itemList) {
		this.itemList = itemList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
