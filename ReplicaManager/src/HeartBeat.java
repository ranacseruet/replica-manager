
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
			System.out.println("Thread is running");
			 try {
				for(Server server:config.getServers())
				{
					if(!server.isAlive())
					{
						// TODO verify with other managers
					}
				}
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}