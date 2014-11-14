package trittimo.entities;

import java.awt.Point;

import trittimo.Direction;
import trittimo.World;

public class Nobbin extends Sprite {
	private int hobCounter;
	private int hobThresh;
	private final int DEFHOBTHRESH = 10;
	private double hobRate;
	protected double rand = Math.random();
	protected double randCheck = Math.random();
	public Nobbin(World world) {
		super(world);
		this.hobCounter = 0;
		this.hobThresh = this.DEFHOBTHRESH;
		this.hobRate = Math.random() * 10;
		// TODO Auto-generated constructor stub
	}

	public void setHobThresh(int thresh) {
		this.hobThresh = thresh;
	}

	public void setHobRate(double hobRate) {
		this.hobRate = hobRate;
	}

	@Override
	public String getImagePath() {
		return "resources/nobbin.png";
	}

	@Override
	public void update() {

		// get player's position
		AbstractEntity ent = this.world.getEntity("player");
		if (ent == null) {
			return;
		}
		Point playerPos = ent.getPosition();
		// transform
		double check = Math.random() * 10;
		if (check < this.hobRate) {
			this.hobCounter++;
		}
		if (this.hobCounter == this.hobThresh) {
			Hobbin up = new Hobbin(this.world, true);
			this.world.addEntity(up, this.getPosition());
			this.die();
			return;
		}
		// tries to match positions with Player
		// direction to player
		int xDist = playerPos.x - this.getPosition().x;
		int yDist = playerPos.y - this.getPosition().y;

		// neighbours
		boolean right = this.canMove(this.world.getEntityAt(new Point(this
				.getPosition().x + 1, this.getPosition().y)));
		boolean left = this.canMove(this.world.getEntityAt(new Point(this
				.getPosition().x - 1, this.getPosition().y)));
		boolean up = this.canMove(this.world.getEntityAt(new Point(this
				.getPosition().x, this.getPosition().y - 1)));
		boolean down = this.canMove(this.world.getEntityAt(new Point(this
				.getPosition().x, this.getPosition().y + 1)));

	
		if (rand > randCheck) {
			if (xDist > 0 && right) {
				this.move(Direction.RIGHT);
			} else if (xDist < 0 && left) {
				this.move(Direction.LEFT);
			} else {
				if (yDist > 0 && down) {
					this.move(Direction.DOWN);
				} else if (yDist < 0 && up) {
					this.move(Direction.UP);
				}
			}
		} else {
			if (yDist > 0 && down) {
				this.move(Direction.DOWN);
			} else if (yDist < 0 && up) {
				this.move(Direction.UP);
			} else {
				if (xDist > 0 && right) {
					this.move(Direction.RIGHT);
				} else if (xDist < 0 && left) {
					this.move(Direction.LEFT);
				}
			}
		}

	}

	@Override
	public boolean canMove(AbstractEntity entity) {
		if (entity instanceof Player) {
			return true;
		} else if (entity == null) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		return "nobbin";
	}

}
