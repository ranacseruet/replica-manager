import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;


public class Config 
{
	private ArrayList<Server> servers = new ArrayList<Server>();
	
	public ArrayList<Server> getServers()
	{
		return servers;
	}
	
	public Config(String filePath) throws Exception
	{
		Properties prop = new Properties();
		prop.load(new FileInputStream("config.properties"));
		int i = 1, port;
		String hostProperty = "server"+i+".host";
		String portProperty = "server"+i+++".port";
		while(prop.getProperty(hostProperty) != null && prop.getProperty(portProperty) != null)
		{
			Server server = new Server(prop.getProperty(hostProperty), Integer.parseInt(prop.getProperty(portProperty)));
			servers.add(server);
			hostProperty = "server"+i+".host";
			portProperty = "server"+i+++".port";
		}
		System.out.println("Total "+servers.size()+" servers added");
	}
}
