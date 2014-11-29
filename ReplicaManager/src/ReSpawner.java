import UDP.UDPServer;


public class ReSpawner extends Thread 
{
	public ReSpawner()
	{
		//TODO save own server info?!?
	}
	
	public void run()
	{
		try{
			UDPServer server = new UDPServer("", 5004);
			while(true){
				String request = server.recieveRequest();
				String[] reqParts = request.split(":");
				if(reqParts[1].equals("reset")){
					System.out.println("Got restart signal. Restarting");
					//Restart done
					server.sendResponse("restarted");
				}
			}
		}
		catch(Exception exp) {
			exp.printStackTrace();
		}
		
	}
}
