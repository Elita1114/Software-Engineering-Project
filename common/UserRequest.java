package common;

import java.util.*;
public class UserRequest {
	
	String request_str;
	ArrayList<Object> request_args = new ArrayList<Object>();
	
	public UserRequest(String request_str_, ArrayList<Object> request_args_){
		request_str = request_str_;
        ArrayList<Object> request_args = (ArrayList<Object>) request_args_.clone();
	}
	
	
	public String get_request_str(){
		return request_str;
	}
	
	public ArrayList<Object> get_request_args() {
		return request_args;
	}
	
	public String toString()
	{
		return request_str;
	}
	
}
