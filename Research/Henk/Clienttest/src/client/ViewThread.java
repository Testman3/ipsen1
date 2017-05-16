package client;

public class ViewThread implements Runnable{

	long tStart = System.currentTimeMillis();

	public static void main(String[] args) {
		new Thread((new ViewThread())).start();

	}

	public void run() {
		
		GameClient.updatePlayerList();
		System.out.println("Thread Started");
		
		try {
			while(true){
			Thread.sleep(500);
			GameClient.updatePlayerList();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//while (true)
		//{
		//	System.out.println("Thread Running");
		//	GameClient.updatePlayerList();
		//	}
		GameClient.updatePlayerList();
		System.out.println("Playerlist updated, thread running");
		}
	public static void kill() 
	{
		Thread.interrupted();
		System.out.print("Thread killed");
	}
	
	}
