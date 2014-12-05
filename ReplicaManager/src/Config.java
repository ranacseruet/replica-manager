import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;


public class Config 
{
	Properties prop;
	private ArrayList<Server> servers = new ArrayList<Server>();
	private ArrayList<RM> rms = new ArrayList<RM>();
	
	public String getPropertyValue(String propertyName)
	{
		return prop.getProperty(propertyName);
	}
	
	public ArrayList<Server> getServers()
	{
		return servers;
	}
	
	public ArrayList<RM> getReplicaManagers()
	{
		return rms;
	}
	
	public Server getServerByNameAndPort(String name, int port)
	{
		for(Server s:servers){
			if(s.equals(name) && port == s.getPort()){
				return s;
			}
		}
		return null;
	}
	
	
	public Config(String filePath) throws Exception
	{
		prop = new Properties();
		prop.load(new FileInputStream(filePath));
		int i = 1;
		String hostProperty = "server"+i+".host";
		String portProperty = "server"+i+".ports";
		String rmProperty = "server"+i+++".rm";
		while(prop.getProperty(hostProperty) != null && prop.getProperty(portProperty) != null)
		{
			String[] ports = prop.getProperty(portProperty).split(",");
			RM rm = new RM(prop.getProperty(hostProperty), Integer.parseInt(prop.getProperty(rmProperty)));
			rms.add(rm);
			for(String port:ports) {
				//System.out.print("port: "+port);
				Server server = new Server(prop.getProperty(hostProperty), Integer.parseInt(port), rm);
				servers.add(server);
			}
			
			hostProperty = "server"+i+".host";
			portProperty = "server"+i+".ports";
			rmProperty   = "server"+i+++".rm";
			//System.out.println("Now will try for "+hostProperty);
		}
		System.out.println("Total "+servers.size()+" servers added");
	}
}
