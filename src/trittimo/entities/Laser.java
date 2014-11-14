package trittimo.entities;

import java.awt.Point;

import trittimo.Direction;
import trittimo.World;
import trittimo.util.Sound;

/**
 * TODO Kills things except for dirt and score items
 *
 * @author suchyb. Created Nov 10, 2014.
 */
public class Laser extends Sprite {
	private static Direction dir = Direction.UP;
	private final int NOBVAL = 10;
	private final int HOBVAL = 20;
	int updateCount = 0;

	/**
	 * TODO Put here a description of what this constructor does.
	 *
	 * @param world
	 */
	public Laser(World world, Direction direction) {
		super(world);

		try {
			this.world.setPaused(true);
			new Sound("resources/sounds/laser.mp3").start();
			Thread.sleep(2155);
			this.world.setPaused(false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Laser.dir = direction;
		this.fire();
	}

	public Laser(World world) {
		super(world);
	}

	@Override
	public String getImagePath() {
		switch (dir) {
		case UP:
		case DOWN:
			return "resources/laser-vertical.png";
		default:
			return "resources/laser-horizontal.png";
		}

	}

	@Override
	public void update() {
		if (this.updateCount > 3) {
			this.kill();
		} else {
			this.updateCount++;
		}
	}

	public void fire() {
		if (this.world.getEntity("laser") != null) {
			return;
		}

		Point p = this.world.getEntity("player").getPosition();
		for (int i = 0; i < this.world.getEntities().length; i++) {
			p = this.getNextPosition(p, dir);
			AbstractEntity ent = this.world.getEntityAt(p);
			if (ent instanceof Dirt || ent instanceof Spawner
					|| ent instanceof Emerald || ent instanceof Bag) {
				return;
			} else if (ent instanceof Nobbin) {
				ent.die();
				((Player) this.world.getEntity("player")).addScore(this.NOBVAL);
				this.world.addEntity(new Laser(this.world), p);
			} else if (ent instanceof Hobbin) {
				ent.die();
				((Player) this.world.getEntity("player")).addScore(this.HOBVAL);
				this.world.addEntity(new Laser(this.world), p);
			} else if (ent != null) {
				ent.die();
				this.world.addEntity(new Laser(this.world), p);
			} else {
				this.world.addEntity(new Laser(this.world), p);
			}
		}
	}

	public void kill() {
		while (true) {
			AbstractEntity laser = this.world.getEntity("laser");

			if (laser == null) {
				return;
			} else {

				laser.die();
			}
		}
	}

	public Point getNextPosition(Point position, Direction direction) {
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

	@Override
	public boolean canMove(AbstractEntity entity) {
		return true;
	}

	@Override
	public String toString() {
		return "laser";
	}

}
