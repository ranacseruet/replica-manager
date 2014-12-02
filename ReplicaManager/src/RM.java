import UDP.UDPClient;


public class RM 
{
	private String host;
	private int port;
	
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public RM(String host, int port)
	{
		this.host = host;
		this.port = port;
		System.out.println("Replica Manager running at "+host+":"+port);
	}
	
	public boolean verifyServer(Server server)
	{
		UDPClient client = new UDPClient(host, port);
		String response = client.send("req:replica:isactive:"+server.getHostName()+":"+server.getPort());
		if(response == null) {
			return false;
		}
		return Boolean.parseBoolean(response)||response.equals("yes");
	}
	
	public String toString()
	{
		return host+":"+port;
	}
	
}
