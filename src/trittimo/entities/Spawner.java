package trittimo.entities;

import java.awt.Point;

import trittimo.World;

public class Spawner extends Sprite {
	private int timer;
	private int monsterCount;
	// cap on # of monsters on screen
	private int monsterCap;
	// limit on total # of monsters
	private int monsterLimit;
	private int rate;
	private final int DEFRATE = 4;
	private final int DEFCAP = 4;
	private final int DEFMONSLIMIT = 20;
	private int hobRate;
	private int hobCounter;
	private final int DEFHOBRATE = 4;

	public Spawner(World world) {
		super(world);
		this.monsterCap = this.DEFCAP;
		this.monsterLimit = this.DEFMONSLIMIT;
		this.rate = this.DEFRATE;
		this.timer = 1;
		this.monsterCount = 0;
		this.hobRate = this.DEFHOBRATE;
		this.hobCounter = 0;
		// TODO Auto-generated constructor stub
	}

	public void setCap(int cap) {
		this.monsterCap = cap;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public void setLimit(int limit) {
		this.monsterLimit = limit;
	}

	public void setHobRate(int hobRate) {
		this.hobRate = hobRate;
	}

	@Override
	public void update() {
		// get number of monsters
		AbstractEntity[][] map = this.world.getEntities();
		for (int i = 0; i < map.length; i++) {
			for (int p = 0; p < map[i].length; p++) {
				if (map[i][p] != null) {
					if (map[i][p].toString().equals("hobbin")
							|| map[i][p].toString().equals("nobbin")) {
						this.monsterCount++;
					}
				}
			}
		}
		// increment timer
		this.timer++;
		// check conditions for spawning
		Point above = new Point(getPosition().x, getPosition().y - 1);
		if (this.timer % this.rate == 0 && this.monsterCount < this.monsterCap
				&& this.monsterLimit > 0
				&& this.world.getEntityAt(above) == null) {
			this.hobCounter++;
			if (this.hobCounter == this.hobRate) {
				this.hobCounter = 0;
				Hobbin spawn = new Hobbin(this.world);
				this.world.addEntity(spawn, above);
				this.monsterLimit--;
			} else {
				Nobbin spawn = new Nobbin(this.world);

				this.world.addEntity(spawn, above);
				this.monsterLimit--;
			}
		}
		// reset count to 0;
		this.monsterCount = 0;
	}

	@Override
	public boolean canMove(AbstractEntity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "spawner";
	}

	@Override
	public String getImagePath() {
		// TODO Auto-generated method stub
		return "resources/spawner.png";
	}

}
