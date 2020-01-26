package common;

public class Customer extends User {

	public Customer(String username, String password, String id, String credit_card_number, int pay_method,
			String phone_number, int store) {
		super(username, password, id, credit_card_number, pay_method,
				phone_number, store);
	}

	public String getDetails() {
		return "User: " + username + "\nPassword: " + password + "\nID: " + id + "\nCredit card number "
				+ credit_card_number + "\nPay method: " + pay_method + "\nPhone number: " + phone_number + "\nStore: "
				+ store;
	}
}
