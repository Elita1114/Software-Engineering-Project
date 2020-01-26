package common;

import java.io.Serializable;

public class CartItem  implements Serializable {

	private static final long serialVersionUID = 3929474044454561386L;
	private int qty;
	private Item item;
	
	public CartItem(Item item_, int qty_) {
		setItem(item_);
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
