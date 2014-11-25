import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class Main 
{
	public static void main(String args[]) throws Exception
	{
		Config config = new Config("config.properties");
		HeartBeat heartBeat = new HeartBeat(config);
		heartBeat.start();
	}
}
