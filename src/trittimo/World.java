package trittimo;

import java.awt.Dimension;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import trittimo.entities.AbstractEntity;
import trittimo.util.LevelLoader;

public class World {
	private int updateRate = 500; // Increase to make the game harder

	// Note: here we should only methods adding/getting entities, not moving
	// them. Moving should be handled in the AbstractEntity class itself

	private AbstractEntity[][] map;
	private WorldComponent component;
	private boolean running = true;
	private boolean paused = false;

	protected boolean isFinalLevel = false;

	public World(String firstLevel, WorldComponent component) {
		this.component = component;

		Runnable updater = new Runnable() {
			@Override
			public void run() {
				while (World.this.running) {
					if (!World.this.paused) {
						try {
							Thread.sleep(World.this.updateRate);
							if (!World.this.paused) {
								World.this.update();
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		};
		this.loadMap(firstLevel);
		new Thread(updater).start();

	}

	public void setPaused(boolean value) {
		this.paused = value;
	}

	public boolean isPaused() {
		return this.paused;
	}

	public void setUpdateRate(int updateRate) {
		this.updateRate = updateRate;
	}

	/**
	 * Called every {@value World#updateRate} milliseconds
	 */
	public void update() {
		ArrayList<AbstractEntity> update = new ArrayList<AbstractEntity>();

		for (int x = 0; x < this.map.length; x++) {
			for (int y = 0; y < this.map[x].length; y++) {
				if (this.map[x][y] != null && !update.contains(this.map[x][y])) {
					update.add(this.map[x][y]);
					this.map[x][y].update();
				}
			}
		}
	}

	/**
	 * Loads the AbstractEntity[][] map from a file
	 * 
	 * @param mapName
	 */
	public void loadMap(String mapName) {
		if (mapName.equals("resources/level_03.txt")) {
			this.isFinalLevel = true;
		}
		try {
			LevelLoader loader = new LevelLoader(this);

			this.map = loader.load(mapName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public boolean isFinalLevel() {
		return this.isFinalLevel;
	}

	public void reloadMap(String mapName) {
		this.component.loadLevel(mapName);
	}

	/**
	 * @return Returns the AbstractEntity[][] map without any operations
	 */
	public AbstractEntity[][] getEntities() {
		return this.map;
	}

	/**
	 * @return Returns the size of the AbstractEntity[][] map
	 */
	public Dimension getSize() {
		return new Dimension(this.map.length, this.map[0].length);
	}

	/**
	 * @param position
	 * @return A boolean indicating whether the point is in the map bounds
	 */
	public boolean inMap(Point position) {
		if (position == null) {
			return false;
		}
		if (position.x >= 0 && position.x < this.map.length && position.y >= 0
				&& position.y < this.map[0].length) {
			return true;
		}

		return false;
	}

	/**
	 * Adds an entity to the map if there is one
	 * 
	 * @param entity
	 * @param position
	 */
	public void addEntity(AbstractEntity entity, Point position) {
		if (this.inMap(position)) {
			this.map[position.x][position.y] = entity;
		}
	}

	public void removeEntityAt(Point position) {
		this.addEntity(null, position);
	}

	public void removeEntity(AbstractEntity entity) {
		Point position = this.getPosition(entity);
		if (position != null) {
			this.removeEntityAt(position);
		}
	}

	/**
	 * @param position
	 * @return The entity at the given point, or null if there is none
	 */
	public AbstractEntity getEntityAt(Point position) {
		if (this.inMap(position)) {
			return this.map[position.x][position.y];
		}
		return null;
	}

	public AbstractEntity getEntity(String entityName) {
		for (int x = 0; x < this.map.length; x++) {
			for (int y = 0; y < this.map[x].length; y++) {
				if (this.map[x][y] != null
						&& this.map[x][y].toString().equals(entityName)) {
					return this.map[x][y];
				}
			}
		}
		return null;
	}

	/**
	 * Gets position of entity
	 * 
	 * @param entity
	 * @return
	 */
	public Point getPosition(AbstractEntity entity) {
		for (int x = 0; x < this.map.length; x++) {
			for (int y = 0; y < this.map[x].length; y++) {
				if (this.map[x][y] != null && this.map[x][y].equals(entity)) {
					return new Point(x, y);
				}
			}
		}
		return null;
	}

	public void stop() {
		this.running = false;
	}

}