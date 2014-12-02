import UDP.UDPClient;


public final class HeartBeat extends Thread 
{
	private Config config;
	
	public HeartBeat(Config config)
	{
		this.config = config;
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
							int dead = 0;
							for(RM rm:config.getReplicaManagers()) {
								System.out.println("verifying status for "+server+" from RM "+rm);
								//TODO for now will verify from its own RM as well
								if(!rm.verifyServer(server)) {
									System.out.println("Remote server also says dead");
									dead++;
								}
								else {
									System.out.println("Remote server says its alive");
								}
							}
							
							//send restart signal
							if(dead >= config.getReplicaManagers().size()){
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
