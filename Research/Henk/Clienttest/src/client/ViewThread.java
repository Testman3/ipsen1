package client;

public class ViewThread implements Runnable
{

	boolean enableThread = true;

	public static void main(String[] args) 
	{
		new Thread((new ViewThread())).start();
	}

	public void run() {
		
		GameClient.updatePlayerList();
		System.out.println("Thread Started");
		
		try {
			while(enableThread == true)
				{
				Thread.sleep(500);
				GameClient.updatePlayerList();
				}
			} catch (InterruptedException e) {e.printStackTrace();}

		}
	public static void kill() 
	{
		Thread.interrupted();
		System.out.print("Thread killed");
	}
	
}
