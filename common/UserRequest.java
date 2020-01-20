package common;

import java.io.Serializable;
import java.util.*;
public class UserRequest implements Serializable {
	private static final long serialVersionUID = 3271081956355193071L;
	String request_str;
	private ArrayList<Object> request_args;
	
	public UserRequest(String request_str_, ArrayList<Object> request_args_){
		request_str = request_str_;
		request_args = request_args_;
	}
	
	public String get_request_str() {
		return request_str;
	}
	
	public ArrayList<Object> get_request_args() {
		return request_args;
	}
	
	public String toString() {
		return request_str;
	}
	
}
