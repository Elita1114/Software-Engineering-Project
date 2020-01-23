package common;

import java.io.Serializable;

public class ReturnStatus implements Serializable {

	public String message;
	public boolean status;
	
	public ReturnStatus(String message_, boolean status_) {
		message=message_;
		status=status_;
	}
	
	public String toString() {
		return message;
	}
}
