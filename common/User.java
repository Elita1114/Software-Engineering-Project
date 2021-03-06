package common;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 276134203467033903L;
	public String username;
	public String password;
	public String id;
	public String credit_card_number;
	public int pay_method;
	public String phone_number;
	public int store;
	public int user_id;
	public Status status;
	public String email;


	public User(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status) {
		super();
		this.user_id = user_id_;
		this.username = username;
		this.password = password;
		this.id = id;
		this.credit_card_number = credit_card_number;
		this.pay_method = pay_method;
		this.phone_number = phone_number;
		this.store = store;
		this.status = status;
	}
	
	public User(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status, String email) {
		super();
		this.user_id = user_id_;
		this.username = username;
		this.password = password;
		this.id = id;
		this.credit_card_number = credit_card_number;
		this.pay_method = pay_method;
		this.phone_number = phone_number;
		this.store = store;
		this.status = status;
		this.email = email;
	}
	
	public User(String username, String password)
	{
		super();
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String password, int user_id_, int store) {
		super();
		this.username = username;
		this.password = password;
		this.store = store;
		this.user_id = user_id_;
	}

	public String toString()
	{
		return 	"#gotUser";
	}
	
	public String getDetails()
	{
		return 	"User: "+ username + 
				"\nPassword: " + password +
				"\nID: "+ id+ 
				"\nCredit card number "+ credit_card_number;
	}
}