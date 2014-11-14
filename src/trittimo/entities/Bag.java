package trittimo.entities;

import java.awt.Graphics;

import trittimo.Direction;
import trittimo.World;

public class Bag extends Sprite {

	// We use this to indicate the current amount the bag has fallen
	private int fallDistance = 0;

	// At some point this might come in handy...
	private Direction fallDirection = Direction.DOWN;

	private boolean shouldFall = false;
	private boolean shouldWiggle = false;
	private double shiftAmount = 0;
	private int maxShift = 4;

	public Bag(World world) {
		super(world);
	}

	@Override
	public void update() {
		if (this.canMove(this.getEntityInDirection(this.fallDirection))) {
			if (this.shouldFall) {
				this.shouldWiggle = false;
				this.move(this.fallDirection);
			} else {
				this.shouldWiggle = true;
				this.shouldFall = true;
			}
		} else if (this.shouldFall) {
			this.shouldWiggle = false;
			this.move(this.fallDirection);
		}

	}

	@Override
	public boolean canMove(AbstractEntity entity) {
		if (entity == null) {
			return true;
		}
		switch (entity.toString()) {
		case "hobbin":
		case "nobbin":
		case "player":
		case "emerald":
		case "monster":
			return true;
		default:
			return false;
		}
	}

	@Override
	public void onMove(AbstractEntity entity) {
		if (this.direction == Direction.DOWN) {
			this.fallDistance++;
		}

	}

	@Override
	public String getImagePath() {
		return "resources/bag.png";
	}

	@Override
	public void die() {
		Gold gold = new Gold(this.world);
		this.world.addEntity(gold, this.getPosition());
		super.die();
	}

	@Override
	public String toString() {
		return "bag";
	}

	@Override
	public void onFailedMove(AbstractEntity entity) {
		switch (entity.toString()) {
		case "dirt":
		case "bag":
			if (this.fallDistance > 1) {
				this.die();
			} else {
				this.fallDistance = 0;
				this.shouldFall = false;
			}
		}
	}

	@Override
	public void drawOn(Graphics g) {
		if (this.shouldWiggle) {
			this.shiftAmount += Math.PI / 15;
			this.setShiftAmount(Math.sin(this.shiftAmount) * this.maxShift,
					Math.cos(this.shiftAmount) * this.maxShift);
		} else {
			this.setShiftAmount(0, 0);
		}
		super.drawOn(g);
	}
}
