import java.io.IOException;

import UDP.UDPServer;


public class ReSpawner extends Thread 
{
	Process process;
	Config config;
	private String command;
	
	public ReSpawner(Config config) throws Exception
	{
		this.config = config;
		//TODO save own server info?!?
		command = "java -jar "+this.config.getPropertyValue("replica");
		process = Runtime.getRuntime().exec(command);
	}
	
	public void run()
	{
		try{
			UDPServer server = new UDPServer("", 5004);
			while(true){
				String request = server.recieveRequest();
				String[] reqParts = request.split(":");
				if(reqParts[1].equals("reset")) {
					System.out.println("Got restart signal. Restarting");
					if(process != null) {
						process.destroy();
						process.waitFor();
					}
					Thread.sleep(3000);
					process = Runtime.getRuntime().exec(command);
					//Restart done
					server.sendResponse("restarted");
				}
				else if(reqParts[2].equals("isactive")) {
					Server host = config.getServerByNameAndPort(reqParts[2], Integer.parseInt(reqParts[3]));
					if(host.isAlive()) {
						server.sendResponse("true");
					}
					else {
						server.sendResponse("false");
					}
				}
			}
		}
		catch(Exception exp) {
			exp.printStackTrace();
		}
		finally {
			try{
				process.destroy();
				process.waitFor();
			}
			catch(Exception exp){
				exp.printStackTrace();
			}
		}
	}
}
