package client;

/////////////////////////////////////
//Geschreven door Henk van Overbeek//
/////////////////////////////////////

public class ViewThread implements Runnable
{

	static boolean enableThread = true;

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
		enableThread = false;
		Thread.interrupted();
		System.out.print("Thread killed");
	}
	
}
