package trittimo.entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import trittimo.Direction;
import trittimo.World;

public abstract class AbstractEntity {

	public abstract void drawOn(Graphics g);

	public abstract boolean canMove(AbstractEntity entity);

	@Override
	public abstract String toString();

	protected World world;
	protected Direction direction = Direction.UP;

	protected int shiftX = 0;
	protected int shiftY = 0;

	protected Point initialPosition;

	public AbstractEntity(World world) {
		this.world = world;
	}

	public void setInitialPosition(Point position) {
		if (this.initialPosition == null) {
			this.initialPosition = position;
		}
	}

	public Dimension getSize() {
		// Dimension frameSize = Main.FRAME_SIZE;
		// Dimension worldSize = this.world.getSize();

		// return new Dimension (frameSize.width / worldSize.width,
		// frameSize.height / worldSize.height);

		return new Dimension(37, 37);

	}

	public Point getAbsolutePosition() { // If we want to do tweening we can do
											// it here at some point
		Point position = this.getPosition();
		Dimension size = this.getSize();
		return new Point(position.x * size.width + this.shiftX, position.y
				* size.height + this.shiftY);
	}

	public void setShiftAmount(double x, double y) {
		this.shiftX = (int) x;
		this.shiftY = (int) y;
	}

	/**
	 * Removes the entity from the world
	 */
	public void die() {
		this.world.removeEntity(this);
	}

	public void move(Direction direction) {
		Point newPosition = this.getPointInDirection(direction);

		if (!this.world.inMap(newPosition)) {
			this.onFailedMove(new Dirt(this.world));
			return;
		}

		AbstractEntity entity = this.world.getEntityAt(newPosition);

		Direction oldDirection = this.direction;
		this.setDirection(direction);

		if (this.canMove(entity)) {
			this.onMove(entity);
			if (entity != null && this.shouldKill(entity)) {
				entity.die();
			}
			this.world.removeEntity(this);
			this.world.addEntity(this, newPosition);
			return;
		}

		this.onFailedMove(entity);
		this.setDirection(oldDirection);
	}

	public AbstractEntity getEntityInDirection(Direction direction) {
		return this.world.getEntityAt(this.getPointInDirection(direction));
	}

	public Point getPointInDirection(Direction direction) {
		Point position = this.getPosition();
		switch (direction) {
		case UP:
			position.y--;
			break;
		case RIGHT:
			position.x++;
			break;
		case LEFT:
			position.x--;
			break;
		case DOWN:
			position.y++;
			break;
		}
		return position;
	}

	public Point getPosition() {
		return this.world.getPosition(this);
	}

	public Direction getDirection() {
		return this.direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void update() {
		// Nothing to do here
	}

	public void onMove(AbstractEntity entity) {
		// Nothing to do here
	}

	public void onFailedMove(AbstractEntity entity) {
		// Nothing to do here
	}

	public boolean shouldKill(AbstractEntity entity) {
		// Override if we need to have a case like the bag
		return true;
	}

}
