package common;

import java.io.Serializable;

public class MonthlyReport implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8080534450830284830L;
	int store;
	String Date;
	float income_content;
	String order_content;
	int handledcomplaint;
	int unhandledcomplaint;
	public String date;
	
	public MonthlyReport(int store, String date, float income_content, String order_content,
			int handledcomplaint_, int unhandledcomplaint_) {
		super();
		this.store = store;
		Date = date;
		this.income_content = income_content;
		this.order_content = order_content;
		this.handledcomplaint = handledcomplaint_;
		this.unhandledcomplaint = unhandledcomplaint_;
	}
	
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public void setDate(String date) {
		Date = date;
	}
	public void set_income_content(float income_content) {
		this.income_content = income_content;
	}
	public String get_order_content() {
		return order_content;
	}
	public void set_order_content(String order_content) {
		this.order_content = order_content;
	}
	public int getHandledcomplaint() {
		return handledcomplaint;
	}
  
	public void setHandledcomplaint(int handledcomplaint) {
		this.handledcomplaint = handledcomplaint;
	}

	public int getUnhandledcomplaint() {
		return unhandledcomplaint;
	}

	public void setUnhandledcomplaint(int unhandledcomplaint) {
		this.unhandledcomplaint = unhandledcomplaint;
	}
	
}
