package system;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.*;

public class ClientSelf implements Runnable
{
	private static String lastCommand="ffff";
	private String s=null;
	private OutputStreamWriter writer;
	private long count;
	java.util.Scanner reader;
	private String host;
	
	public void run()
	{
		init();
	}
	public ClientSelf(String ip){
		host=ip;
	}
	public void init()
	{
		try
		{
			boolean end=false;
			//String host="127.0.0.1";
			int port=8899;
			Socket client=new Socket(host,port);
			writer=new OutputStreamWriter(client.getOutputStream());
			reader=new java.util.Scanner(System.in);
			while (!end)
			{
				s=GameSystem.sendCommandSelf;
				//s="!helloworld;";
				//System.out.println(s);
				//System.out.println(GameSystem.sendCommand);
				sendOneCommand();
				
				
				
				
				/*
				if(s==null){
					s="";
				}
				try {
					writer.write(s);
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
			}
			System.out.println("client Closed");
			writer.close();
			client.close();
			
		} catch(Exception e)
		{

		}
	}
	public void sendOneCommand(){
		//count++;
		//System.out.println("sendOneCommand entered");
		//System.out.println(GameSystem.sendCommand);
		//System.out.println(s);
		//System.out.println(count);
		String oneCommand="";
		if(s!=null){
			if(s.indexOf("!")!=-1&&s.indexOf(";")!=-1){
				//System.out.println("sendOneCommand Precondition met");
				oneCommand=s.substring(s.indexOf("!"),s.indexOf(";")+1);
				GameSystem.sendCommandSelf=GameSystem.sendCommandSelf.substring(GameSystem.sendCommandSelf.indexOf(";")+1,GameSystem.sendCommandSelf.length());
				/*
				if(oneCommand.equals(lastCommand)){
					oneCommand="";
				}
				else{
					lastCommand=oneCommand;
				}
				*/
			}
		}
		try {
			//System.out.println(oneCommand);
			//System.out.println(lastCommand);
			writer.write(oneCommand);
			writer.flush();
			
			//System.out.println(oneCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		}
	public void setIp(String ip){
		host=ip;
	}
	
}