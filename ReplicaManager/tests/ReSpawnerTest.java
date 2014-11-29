import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import UDP.UDPClient;


public class ReSpawnerTest extends TestCase  {

	UDPClient udpClient;
	
	@Before
	public void setUp() throws Exception {
		udpClient = new UDPClient("localhost", 5004);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String response = udpClient.send("req:reset:hostname");
		assertEquals("restarted", response);
	}

}
