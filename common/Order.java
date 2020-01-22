package common;

import java.sql.Date;
import java.util.ArrayList;

public class Order {
	private ArrayList<Item> order_items;
	private Date requested_delivery_date;
	private String letter;
	private boolean want_shipping;
	private String shipping_address;
	private String shipping_reciever;
	private String reciever_phone_number;
	
	static final double shipping_price = 20; 
	
	public Order(ArrayList<Item> order_items, Date requested_delivery_date, String letter, boolean want_shipping,
			String shipping_address, String shipping_reciever, String reciever_phone_number) {
		super();
		this.order_items = order_items;
		this.letter = letter;
		this.requested_delivery_date = requested_delivery_date;
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
	public Date get_requested_delivery_date() {
		return requested_delivery_date;
	}
	public void set_requested_delivery_date(Date requested_delivery_date) {
		this.requested_delivery_date = requested_delivery_date;
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
	public String toString()
	{
		return "#gotOrder";
	}
}
