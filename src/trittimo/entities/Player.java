package trittimo.entities;

import trittimo.Main;
import trittimo.World;
import trittimo.util.LevelLoader;
import trittimo.util.Sound;

public class Player extends Sprite {
	private int score;
	private static final int EMERALD_VALUE = 10;
	private static final int GOLD_VALUE = 20;

	public Player(World world) {
		super(world);

		this.score = 0;

	}

	public void addScore(int addition) {
		this.score += addition;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return this.score;
	}

	public boolean canDoBagMove(AbstractEntity bag) {
		if (bag.canMove(bag.getEntityInDirection(this.direction))) {
			return true;
		}
		return false;
	}

	@Override
	public void update() {
		Main.setTitle("The Great Grade Heist - Score: " + this.score);
	}

	public void doBagMove(AbstractEntity bag) {
		bag.move(this.direction);
	}

	public void playDieSound() {
		this.world.setPaused(true);
		new Sound("resources/sounds/myleg.wav").start();
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.world.setPaused(false);
	}

	@Override
	public String getImagePath() {
		if (this.world.isFinalLevel()) {
			return "resources/cookie.png";
		}
		return "resources/player.png";
	}

	@Override
	public boolean canMove(AbstractEntity entity) {

		if (entity == null) {
			return true;
		}

		switch (entity.toString()) {
		case "hobbin":
		case "nobbin":
			return false;
		case "emerald":
			this.score += EMERALD_VALUE;
			return true;
		case "gold":
			this.score += GOLD_VALUE;
			return true;
		case "laser":
			this.die();
			return false;
		case "spawner":
			return false;
		case "bag":
			return this.canDoBagMove(entity);
		default:
			return true;
		}

	}

	@Override
	public void onMove(AbstractEntity entity) {
		if (entity == null) {
			return;
		}

		switch (entity.toString()) {
		case "emerald":
			this.score += EMERALD_VALUE;
		case "gold":
			this.score += GOLD_VALUE;
		case "bag":
			// TODO replace this with an actual doBagMove
			// keep in mind that we only need to add a new bag in the new loc
			// We don't need to worry about killing off the old bag
			this.doBagMove(entity);
		}
	}

	@Override
	public void onFailedMove(AbstractEntity entity) {
		switch (entity.toString()) {
		case "hobbin":
		case "nobbin":
		case "laser":
			this.die();
		}
	}

	@Override
	public boolean shouldKill(AbstractEntity entity) {
		return !(entity instanceof Bag);
	}

	@Override
	public void die() {
		this.playDieSound();

		super.die();

		AbstractEntity[][] map = this.world.getEntities();
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map[x].length; y++) {
				AbstractEntity entity = map[x][y];
				if (entity != null) {
					switch (entity.toString()) {
					case "hobbin":
					case "nobbin":
					case "monster":
						entity.die();
						this.world.addEntity(entity, entity.initialPosition);
					}
				}
			}
		}
		this.world.addEntity(this, this.initialPosition);

		LevelLoader.saveLevel("resources/level_current.txt",
				this.world.getEntities());

		this.world.reloadMap("resources/level_current.txt");
	}

	@Override
	public String toString() {
		return "player";
	}

}
