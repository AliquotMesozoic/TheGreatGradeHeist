package trittimo.entities;

import java.awt.Point;

import trittimo.Direction;
import trittimo.World;
import trittimo.util.Sound;

/**
 * TODO Put here a description of what this class does.
 *
 * @author suchyb. Created Nov 13, 2014.
 */
public class Monster extends Nobbin {
	boolean insight = false;
	boolean alreadyPlayed = false;

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param world
	 */
	public Monster(World world) {
		super(world);
		this.world.setUpdateRate(500);
	}

	public void playCookies() {
		if (this.insight) {
			new Sound("resources/sounds/cookies.mp3").start();
			this.alreadyPlayed = true;
		}

	}

	@Override
	public void update() {

		if (this.getPosition().x == this.world.getEntity("player")
				.getPosition().x
				|| this.getPosition().y == this.world.getEntity("player")
						.getPosition().y) {
			this.insight = true;
			if (this.insight && !this.alreadyPlayed) {
				this.playCookies();
			}
			this.world.setUpdateRate(50);
		} else {
			this.insight = false;
			this.alreadyPlayed = false;
			this.world.setUpdateRate(500);
		}
		// get player's position
		AbstractEntity ent = this.world.getEntity("player");
		if (ent == null) {
			return;
		}
		Point playerPos = ent.getPosition();
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

		if (this.rand > this.randCheck) {
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
	public void onMove(AbstractEntity entity) {
		if (entity instanceof Player) {
			this.world.setUpdateRate(500);
		}
	}

	@Override
	public String getImagePath() {
		return "resources/monster.png";
	}

	@Override
	public String toString() {
		return "monster";
	}

}
