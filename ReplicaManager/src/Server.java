import UDP.UDPClient;


public class Server 
{
	private String host;
	private int    port;
	
	public Server(String host, int port)
	{
		this.host = host;
		this.port = port;
	}
	
	
	public boolean isAlive()
	{
		UDPClient client = new UDPClient(host, port);
		String response = client.send("req:replica:isactive");
		if(response == null) {
			return false;
		}
		return Boolean.parseBoolean(response);
	}
	
	public String toString()
	{
		return host+":"+port;
	}
}
