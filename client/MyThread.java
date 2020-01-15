package client;

public class MyThread extends Thread {
	

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

		try {
			wait(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}