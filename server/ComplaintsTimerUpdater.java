package server;


public class ComplaintsTimerUpdater extends Thread {
	
	private EchoServer echoServer;

	public ComplaintsTimerUpdater(EchoServer echoServer_) {
		echoServer=echoServer_;
	}
	
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			echoServer.handleMessageFromServerUI("#updateTimer");
			System.out.println("waked up");
		}
		/*
		chat.client.handleMessageFromClientUI(msg);
		while(!chat.flagCatalog) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catalogController.setCatalog(chat.catalog.getList());
		catalogController.updateCatalog();
		chat.flagCatalog=false;
		System.out.println("finished");
		*/
	}
	
	
	
	
}