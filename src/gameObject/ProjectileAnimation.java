package gameObject;

import gameObject.GameObject.ORIENTATION;
import gameObject.MovableObject.ANIMATION;
import gameObject.MovableObject.FACING;

public class ProjectileAnimation {
	private double counter;
	private int frames;
	private ImageSequence currentSequence;
	private Projectile owner;

	
	public ProjectileAnimation(Projectile o){
		this.owner=o;
	}
	public void animate(){
		if(owner.orientation==ORIENTATION.RIGHT||owner.orientation==ORIENTATION.DOWN){
			owner.image=currentSequence.getImage((int)counter);
			owner.imageWidth=currentSequence.getWidth();
			owner.imageHeight=currentSequence.getHeight();
			owner.renderXShift=currentSequence.getX();
			owner.renderYShift=currentSequence.getY();
		}
		else if(owner.orientation==ORIENTATION.LEFT){
			owner.image=currentSequence.getImage((int)counter);
			owner.imageWidth=-currentSequence.getWidth();
			owner.imageHeight=currentSequence.getHeight();
			owner.renderXShift=currentSequence.getX()+currentSequence.getWidth();
			owner.renderYShift=currentSequence.getY();
		}
		else if(owner.orientation==ORIENTATION.UP){
			owner.image=currentSequence.getImage((int)counter);
			owner.imageWidth=currentSequence.getWidth();
			owner.imageHeight=-currentSequence.getHeight();
			owner.renderXShift=currentSequence.getX();
			owner.renderYShift=currentSequence.getY()+currentSequence.getHeight();
		}
	}
	public void tick(){
		
		if(currentSequence!=null){
			counter=counter%frames;
			animate();
			counter+=currentSequence.getAnimationSpeed();
		}
	}
	public void startSequence(ImageSequence sequence){
		currentSequence=sequence;
		counter=0;
		if(currentSequence!=null){
			frames=currentSequence.getFrames();
		}
	}
}
