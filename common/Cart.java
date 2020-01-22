package common;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
	private static final long serialVersionUID = 5756472290677023353L;
	ArrayList<Item> items;
	public Cart(ArrayList<Item> items_)
	{
		items = items_;
	}
	
	public void addItem(Item it)
	{
		items.add(it);
	}
	
	public void addItems(ArrayList<Item> other_items)
	{
		items.addAll(other_items);
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public void remove_item_from_cart(Item item_to_remove)
	{
		ArrayList<Item> toRemove = new ArrayList<Item>();
		for (Item item : items) {
			if(item.equals(item_to_remove)) {
		        toRemove.add(item);
			}
		}
		items.removeAll(toRemove);
	}

	 
}
