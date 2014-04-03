package gameObject;

/**
* <b>Description:</b>
* <br>
* Defines point on grid map
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class Point {
	private int x;
	private int y;
	
	public Point(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}
