package common;

public class ChainManager extends StoreManager{
	
	private static ChainManager single_instance = null; 

	private ChainManager(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status) {
		super( user_id_,  username,  password,  id,  credit_card_number,  pay_method, phone_number,  store, status);
	}

	public static ChainManager getInstance(int user_id_, String username, String password, String id, String credit_card_number, int pay_method,String phone_number, int store,Status status) { 
		if (single_instance == null) 
			single_instance = new ChainManager( user_id_,  username,  password,  id,  credit_card_number,  pay_method, phone_number,  store, status);

		return single_instance; 
	}

}