package common;

import java.io.Serializable;

public class CartItem  implements Serializable {

	private static final long serialVersionUID = 3929474044454561386L;
	private int qty;
	private Item item;
	
	public CartItem(int id_, String name_, String description_, String color_, int qty_) {
		setItem(new Item(id_, name_, description_, color_));
		setQty(qty_);
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}
}
