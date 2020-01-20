package common;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 276134203467033903L;
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
	
	public String toString()
	{
		return 	"User: "+ username + 
				"\nPassword: " + password +
				"\nID: "+ id+ 
				"\nCredit card number "+ credit_card_number;
	}
}
