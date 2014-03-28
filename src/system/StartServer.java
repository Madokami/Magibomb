package system;

import game.Game;

public class StartServer
{
	public static void init(Game game)
	{
		Thread server=new Thread(new Server(game));
		server.start();
	}
}