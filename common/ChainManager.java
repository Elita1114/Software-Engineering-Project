package common;

class ChainManager extends StoreManager{
	
	private static ChainManager single_instance = null; 

	private ChainManager(String username, String password, int user_id_, int store) {
		super(username, password, user_id_, store);
	}

	public static ChainManager getInstance(String username, String password, int user_id_, int store) { 
		if (single_instance == null) 
			single_instance = new ChainManager(username, password, user_id_, store); 

		return single_instance; 
	}

}