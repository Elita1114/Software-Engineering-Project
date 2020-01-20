package common;

public class User {
	
	String username;
	String password;
	String id;
	String credit_card_number;
	PayingMethod pay_method;
	int user_id;

	public User(String username, String password, String id, String credit_card_number, PayingMethod pay_method) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.credit_card_number = credit_card_number;
		this.pay_method = pay_method;
		user_id = -1;
	}
}
