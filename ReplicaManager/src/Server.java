import UDP.UDPClient;


public class Server 
{
	private String host;
	private int    port;
	private RM	   rm;
	
	private int failed = 0;
	
	public String getHostName()
	{
		return host;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public RM getRM()
	{
		return rm;
	}
	
	public void setFailed() {
		failed++;
	}
	
	public void resetFailed() {
		failed = 0;
	}
	
	public boolean getFailed() {
		return failed >= 3;
	}
	
	public Server(String host, int port, RM rm)
	{
		this.host = host;
		this.port = port;
		this.rm = rm;
	}
	
	
	public boolean isAlive()
	{
		UDPClient client = new UDPClient(host, port);
		String response = client.send("req:replica:isactive");
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
