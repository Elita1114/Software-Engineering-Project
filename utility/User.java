package utility;



enum PayingMethod{
	pay_per_order,
	annual_subscription,
	monthly_subscription
}

public class User {
	
	String username;
	String password;
	String id;
	String credit_card_number;
	PayingMethod pay_method;

	public User(String username, String password, String id, String credit_card_number, PayingMethod pay_method) {
		super();
		this.username = username;
		this.password = password;
		this.id = id;
		this.credit_card_number = credit_card_number;
		this.pay_method = pay_method;
	}

}
