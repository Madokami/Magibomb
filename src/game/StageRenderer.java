package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import system.BufferedImageLoader;
import system.GameSystem;

public class StageRenderer {
	private int size = GameSystem.GRID_SIZE;
	private BufferedImageLoader loader = new BufferedImageLoader();
	private BufferedImage corn = loader.loadImage("/image/stage/stage1/corn.png");
	private BufferedImage bara = loader.loadImage("/image/stage/stage1/bara1.png");
	private BufferedImage bar1 = loader.loadImage("/image/stage/stage1/bar1.png");
	private BufferedImage bar2 = loader.loadImage("/image/stage/stage1/bar2.png");
	private BufferedImage hyoushiki = loader.loadImage("/image/stage/stage1/hyoushiki.png");

	public void render1(Graphics g){
		g.drawImage(corn, 14*size, 0*size,size,size, null);
		g.drawImage(corn, 10*size, 2*size,size,size, null);	
		g.drawImage(corn, 11*size, 2*size,size,size, null);	
		g.drawImage(corn, 12*size, 2*size,size,size, null);
		g.drawImage(corn, 14*size, 7*size,size,size, null);
		g.drawImage(corn, 15*size, 7*size,size,size, null);
		g.drawImage(hyoushiki, 7*size, 5*size,size,size, null);
		g.drawImage(hyoushiki, 8*size, 5*size,size,size, null);
		g.drawImage(bar1, 2*size, 2*size,3*size,size, null);
		g.drawImage(bar2, 9*size, 7*size,size,3*size, null);
	}
	public void render2(Graphics g){
		g.drawImage(corn, 2*size, 3*size,size,size, null);
		g.drawImage(corn, 13*size, 3*size,size,size, null);
		g.drawImage(corn, 2*size, 6*size,size,size, null);
		g.drawImage(corn, 13*size, 6*size,size,size, null);
		g.drawImage(hyoushiki, 7*size, 0*size,size,size, null);
		g.drawImage(hyoushiki, 8*size, 0*size,size,size, null);
		g.drawImage(hyoushiki, 7*size, 9*size,size,size, null);
		g.drawImage(hyoushiki, 8*size, 9*size,size,size, null);
		g.drawImage(bara, 7*size, 4*size,size,size, null);
		g.drawImage(bara, 8*size, 4*size,size,size, null);
		g.drawImage(bara, 7*size, 5*size,size,size, null);
		g.drawImage(bara, 8*size, 5*size,size,size, null);
	}
	public void render3(Graphics g){
		
	}
	public void render4(Graphics g){
		
	}
	public void render5(Graphics g){
		
	}
	public void render6(Graphics g){
		
	}
	public void render7(Graphics g){
			
		}
	public void render8(Graphics g){
		
	}
}
