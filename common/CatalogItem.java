package common;

import java.io.Serializable;

public class CatalogItem extends Item implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3897068652305367050L;
	private float price;
	private int id;
	private String imagePath;
	
	
	public CatalogItem(String name_, String description_, String color_,float price_,int id_,String imagePath_) {
		super(name_, description_, color_);
		// TODO Auto-generated constructor stub
		price=price_;
		id=id_;
		imagePath=imagePath_;
	}
	
	public float getPrice() {
		return price;
	}
	public int getId() {
		return id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setPrice(float price_) {
		price=price_;
	}
	public void setId(int id_) {
		id=id_;
	}
	public void setImagePath(String imagePath_) {
		imagePath=imagePath_;
	}

}
