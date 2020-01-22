package common;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3708969239650189239L;
	private ArrayList<Item> order_items;
	private String requested_delivery_date_time;
	private String letter;
	private boolean want_shipping;
	private String shipping_address;
	private String shipping_reciever;
	private String reciever_phone_number;
	
	static final double shipping_price = 20; 
	
	public Order(ArrayList<Item> order_items, String requested_delivery_date_time, String letter, boolean want_shipping,
			String shipping_address, String shipping_reciever, String reciever_phone_number) {
		super();
		this.order_items = order_items;
		this.letter = letter;
		this.requested_delivery_date_time = requested_delivery_date_time;
		this.want_shipping = want_shipping;
		if(want_shipping) 
		{
			this.shipping_address = shipping_address;
			this.shipping_reciever = shipping_reciever;
			this.reciever_phone_number = reciever_phone_number;
		}
	}
	
	
	public ArrayList<Item> get_order_items() {
		return order_items;
	}
	
	public void set_order_items(ArrayList<Item> order_items) {
		this.order_items = order_items;
	}
	public String get_letter() {
		return letter;
	}
	public void set_letter(String letter) {
		this.letter = letter;
	}
	public String get_requested_delivery_date() {
		return requested_delivery_date_time;
	}
	public void set_requested_delivery_date(String requested_delivery_date_time) {
		this.requested_delivery_date_time = requested_delivery_date_time;
	}
	public boolean want_shipping() {
		return want_shipping;
	}
	public void set_want_shipping(boolean want_shipping) {
		this.want_shipping = want_shipping;
	}
	public String get_shipping_address() {
		return shipping_address;
	}
	public void set_shipping_address(String shipping_address) {
		this.shipping_address = shipping_address;
	}
	public String get_shipping_reciever() {
		return shipping_reciever;
	}
	public void set_shipping_reciever(String shipping_reciever) {
		this.shipping_reciever = shipping_reciever;
	}
	public String get_recievre_phone_number() {
		return reciever_phone_number;
	}
	public void set_reciever_phone_number(String reciever_phone_number) {
		this.reciever_phone_number = reciever_phone_number;
	}


	@Override
	public String toString() {
		return "Order [order_items=" + order_items + ", requested_delivery_date_time=" + requested_delivery_date_time
				+ ", letter=" + letter + ", want_shipping=" + want_shipping + ", shipping_address=" + shipping_address
				+ ", shipping_reciever=" + shipping_reciever + ", reciever_phone_number=" + reciever_phone_number + "]";
	}
	
}
