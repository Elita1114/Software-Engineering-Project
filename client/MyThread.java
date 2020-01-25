package client;

import client.Controllers.MainController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MyThread extends Thread{

	public static boolean flagCustomerService=false;
	public static boolean loggedIn=false;
	public static boolean flagAnswer=false;
	public static boolean flagUrgent=false;
	
	MyThread(){
	}
	
	
	@Override
	public void run() {

		while(!loggedIn) {
			System.out.println("not logged in");
	    	try {
				Thread.sleep(30000);
	    		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(flagCustomerService) {
			while(true) {
				flagAnswer=false;
				flagUrgent=false;
				
				MainController.client.client.handleMessageFromClientUI("#isUrgent");
				
				while(!flagAnswer) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(flagUrgent)
					MainController.notifyCustomerService();
    	    	try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	    	
			}
		}

	}
	
}
