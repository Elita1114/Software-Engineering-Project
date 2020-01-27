package common;

public class Employee extends User {

	public Employee(String username, String password, int user_id_, int store) {
		super(username, password, user_id_, store);
	}
	
	@Override
	public String getDetails() {
		return "User: " + username + "\nPassword: " + password + "\nUser ID: "+ user_id + "\nStore: "+ store;
	}
}
