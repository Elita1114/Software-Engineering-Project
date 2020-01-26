package common;

public class MonthlyReport {
	int store;
	String Date;
	String income_content;
	String order_content;
	String complaint_content;
	
	public MonthlyReport(int store, String date, String income_content, String order_content,
			String complaint_content) {
		super();
		this.store = store;
		Date = date;
		this.income_content = income_content;
		this.order_content = order_content;
		this.complaint_content = complaint_content;
	}
	
	public int getStore() {
		return store;
	}
	public void setStore(int store) {
		this.store = store;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String gte_income_content() {
		return income_content;
	}
	public void set_income_content(String income_content) {
		this.income_content = income_content;
	}
	public String get_order_content() {
		return order_content;
	}
	public void set_order_content(String order_content) {
		this.order_content = order_content;
	}
	public String get_complaint_content() {
		return complaint_content;
	}
	public void set_complaint_content(String complaint_content) {
		this.complaint_content = complaint_content;
	}
}
