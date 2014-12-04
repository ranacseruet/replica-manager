import UDP.UDPClient;


public final class HeartBeat extends Thread 
{
	private Config config;
	
	private String myHost;
	private int myPort;
	public HeartBeat(Config config)
	{
		this.config = config;
		myHost = config.getPropertyValue("self.host");
		myPort = Integer.parseInt(config.getPropertyValue("self.port"));
	}
	
	public void run()
	{
		while(true)
		{
			 try {
				for(Server server:config.getServers())
				{
					if(!server.isAlive())
					{
						System.out.println("server "+server+" is not OK");
						if(server.getFailed()) {							
							boolean dead = true;
							for(RM rm:config.getReplicaManagers()) {
								if(rm.getHost().equals(myHost) && rm.getPort() == myPort){
									//don't recheck with itself
									continue;
								}
								System.out.println("verifying status for "+server+" from RM "+rm);
								//TODO for now will verify from its own RM as well
								if(!rm.verifyServer(server)) {
									System.out.println("Remote server also says dead");
								}
								else {
									dead = false;
									System.out.println("Remote server says its alive");
								}
							}
							
							//send restart signal
							if(dead){
								System.out.println("Sending restart signal");
								UDPClient client = new UDPClient(server.getRM().getHost(), server.getRM().getPort());
								client.sendOnly("req:reset:hostname");
							}
							
						}
						else {
							server.setFailed();
						}
					}
					else {
						System.out.println("server "+server+" is OK");
					}
				}
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
