package client;

import client.Controllers.CatalogController;

public class MyThread extends Thread {
	
	private ClientConsole chat;
	private CatalogController catalogController;
	private String msg;
	public MyThread(ClientConsole chat_,String msg_,CatalogController catalogController_) {
		chat=chat_;
		msg=msg_;
		catalogController=catalogController_;
	}
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		FibThreads[] threads=new FibThreads[10];
		for(int i=0;i<10;i++)
			threads[i]=new FibThreads();
		for(int i=0;i<10;i++)
			threads[i].start();
		for(Thread thread:threads) {
			try {
				thread.join();
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long difference = System.nanoTime() - startTime;
		System.out.println("Total execution time: "+difference+" nanosecconds");

	}
	*/
	
	public void run() {
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