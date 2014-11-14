package trittimo.entities;

import trittimo.World;

public class Emerald extends Sprite{

	public Emerald(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImagePath() {
		return "resources/emerald.png";
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
	public void die() {
		super.die();
		if (world.getEntity("emerald") == null) {
			world.reloadMap("resources/level_02.txt");
		}
	}

	@Override
	public String toString() {
		return "emerald";
	}

}
