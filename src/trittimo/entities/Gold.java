package trittimo.entities;

import trittimo.World;

public class Gold extends Sprite{

	public Gold(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImagePath() {
		return "resources/gold.png";
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canMove(AbstractEntity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "gold";
	}

}
