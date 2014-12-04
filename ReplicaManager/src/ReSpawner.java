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
			UDPServer server = new UDPServer("", Integer.parseInt(config.getPropertyValue("self.port")));
			while(true){
				String request = server.recieveRequest();
				System.out.println("Respawner got request: "+request);
				String[] reqParts = request.split(":");
				if(reqParts[1].trim().equals("reset")) {
					System.out.println("Got restart signal. Restarting");
					if(process != null) {
						process.destroy();
						process.waitFor();
					}
					Thread.sleep(1000);
					process = Runtime.getRuntime().exec(command);
					//Restart done
					server.sendResponse("restarted");
				}
				else if(reqParts[2].equals("isactive")) {
					Server host = config.getServerByNameAndPort(reqParts[3].trim(), Integer.parseInt(reqParts[4].trim()));
					if(host != null && host.isAlive()) {
						server.sendResponse("true");
					}
					else {
						if(host == null) {
							System.out.println("Invalid server info: "+reqParts[3].trim()+":"+reqParts[4].trim());
						}
						server.sendResponse("false");
					}
				}
			}
		}
		catch(Exception exp) {
			exp.printStackTrace();
		}
	}
	
	public void shutDownServer()
	{
		try{
			if(process != null) {
				process.destroy();
				process.waitFor();
			}
		}
		catch(Exception exp){
			exp.printStackTrace();
		}
	}
}
