package system;

public class StartClient
{
	private static String myIp="192.168.0.101";
	private static String self="127.0.0.1";
	public static void init()
	{
		
		Thread client=new Thread(new Client(myIp));
		//a.setIp("142.157.154.86");
		
	
		//Thread client2 = new Thread(new Client("142.157.107.77"));
		//Thread client1 = new Thread(new Client1("142.157.106.1"));
		client.start();
		//client1.start();
	}
}