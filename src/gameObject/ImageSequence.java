package gameObject;

import java.awt.image.BufferedImage;

import system.GameSystem;

/**
* <b>Description:</b>
* <br>
* Displays images in sequences at specified dimensions and order
* @author Team 6
* @version 1.0
* @since 2014-03-31
*/
public class ImageSequence {
	private int size = GameSystem.GRID_SIZE;
	private final double scale = GameSystem.GRID_SIZE*1.5;
	private final int yShift = GameSystem.GRID_SIZE/10;
	
	private BufferedImage[] images;
	private int x,y,width,height,frames;
	private double animationSpeed;
	/**
	 * defines image sequence
	 * @panam path, frames
	 */
	public ImageSequence(String path,int frames){
		this.frames=frames;
		images=new BufferedImage[frames];
		for(int i=0;i<frames;i++){
			images[i]=GameSystem.loader.loadImage(path.concat(" (").concat(Integer.toString(i+1)).concat(")").concat(".png"));
		}
		setDefaultValues();
	}
	
	public BufferedImage getImage(int index){
		return images[index];
	}
	
	/**
	 * Sets default dimensions of image as well as the speed in which the frames of the image are animated
	 */
	public void setDefaultValues(){
		height=(int)scale;
		width=images[0].getWidth()*height/images[0].getHeight();
		x= (size-width)/2;
		y= size-height+yShift;
		animationSpeed=0.2;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	

	public int getHeight() {
		return height;
	}
	public int setWidth(int width){
		this.width=width;
		return width;
	}
	public int setHeight(int height){
		this.height=height;
		return height;
	}

	
	public int getFrames(){
		return frames;
	}

	public double getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(double animationSpeed) {
		this.animationSpeed = animationSpeed;
	}
	/**
	 * scales the dimensions
	 * @panam ratio
	 */
	public void scale(double ratio){
		double tempX = (width-width*ratio)/2;
		double tempY = (height-height*ratio)/2;
		
		width*=ratio;
		height*=ratio;
		this.x+=tempX;
		this.y+=tempY;
	}
}
