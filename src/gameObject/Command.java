package gameObject;

public class Command {
	private String command;
	private int key;
	
	public Command(String command,int key){
		this.command = command;
		this.key = key;
	}
	
	public String getCommand(){
		return this.command;
	}
	
	public int getKey(){
		return this.key;
	}
}
