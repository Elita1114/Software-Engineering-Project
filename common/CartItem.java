package common;

import java.io.Serializable;

public class CartItem  implements Serializable {

	private static final long serialVersionUID = 3929474044454561386L;
	private int qty;
	private Item item;
	
	public CartItem(int id_, String name_, String description_, String color_, int qty_) {
		item = new Item(id_, name_, description_, color_);
		qty = qty_;
	}
}
