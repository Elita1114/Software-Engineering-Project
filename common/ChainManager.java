package common;

class ChainManager extends StoreManager{
	
	private static ChainManager single_instance = null; 

	private ChainManager(String username, String password) {
		super(username, password);
	}

	public static ChainManager getInstance(String username, String password) { 
		if (single_instance == null) 
			single_instance = new ChainManager(username, password); 

		return single_instance; 
	}

}