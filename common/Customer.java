package common;

public class Customer extends User {

	
	public Customer(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status, String email){
		super( user_id_,  username,  password,  id,  credit_card_number,  pay_method, phone_number,  store, status,  email);
	}

	public String getDetails() {
		return "User: " + username + "\nPassword: " + password + "\nID: " + id + "\nCredit card number "
				+ credit_card_number + "\nPay method: " + pay_method + "\nPhone number: " + phone_number + "\nStore: "
				+ store;
	}
}
