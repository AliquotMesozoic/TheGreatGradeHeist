package trittimo.entities;

import java.awt.Point;

import trittimo.Direction;
import trittimo.World;

public class Hobbin extends Sprite {
	private boolean wasNob;
	private int nobCounter;
	private int nobThresh;
	private static int DEFNOBTHRESH = 10;
	private double rand = Math.random();
	private double randCheck = Math.random();
	public Hobbin(World world) {
		super(world);
		this.wasNob = false;
		this.nobCounter = 0;
		this.nobThresh = DEFNOBTHRESH;
	}

	public Hobbin(World world, boolean wasNob) {
		super(world);
		this.wasNob = wasNob;
		this.nobCounter = 0;
		this.nobThresh = DEFNOBTHRESH;
	}

	@Override
	public String getImagePath() {
		return "resources/hobbin.png";
	}

	public void setNobThresh(int thresh) {
		this.nobThresh = thresh;
	}

	@Override
	public void update() {
		AbstractEntity player = this.world.getEntity("player");
		if (player == null) {
			return;
		}
		// transform back
		if (this.wasNob) {
			this.nobCounter++;
		}
		if (this.wasNob && this.nobCounter == this.nobThresh) {
			Nobbin revert = new Nobbin(this.world);
			this.world.addEntity(revert, this.getPosition());
			this.die();
			return;
		}

		Point playerPos = player.getPosition();
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
		
		if(rand > randCheck){
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
		}else{
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
		if (entity == null) {
			return true;
		}

		switch (entity.toString()) {
		case "player":
			return true;
		case "dirt":
			return true;
		default:
			return false;
		}
	}

	@Override
	public String toString() {
		return "hobbin";
	}

}
