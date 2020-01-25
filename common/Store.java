package common;

import java.io.Serializable;

public class Store implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 828961710358073954L;
	private int id;
	private String name;

	
	public Store(int id_, String name_){
		setId(id_);
		name=name_;

	}
	

	public String getName() {
		return name;
	}
	public void setName(String name_) {
		name=name_;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
