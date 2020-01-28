package common;

public class customerService extends User {

	public customerService(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status, String email) {
		super( user_id_,  username,  password,  id,  credit_card_number,  pay_method, phone_number,  store, status, email);
	}
}
