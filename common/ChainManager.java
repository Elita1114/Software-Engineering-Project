package common;

public class ChainManager extends StoreManager{
	
	private static ChainManager single_instance = null; 

	private ChainManager(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status, String email) {
		super( user_id_,  username,  password,  id,  credit_card_number,  pay_method, phone_number,  store, status,  email);
	}

	public static ChainManager getInstance(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status, String email) { 
		if (single_instance == null) 
			single_instance = new ChainManager( user_id_,  username,  password,  id,  credit_card_number,  pay_method, phone_number,  store, status,  email);

		return single_instance; 
	}

}