package trittimo;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

import trittimo.entities.AbstractEntity;

@SuppressWarnings("serial")
public class WorldComponent extends JComponent {

	private World world;
	private GraphicsTimer timer;
	private GameListener listener;
	private JFrame frame;

	public WorldComponent(String levelName, JFrame frame) {
		this.frame = frame;
		loadLevel(levelName);
		this.timer = new GraphicsTimer(this);
		new Thread(this.timer).start();
	}

	public void loadLevel(String levelName) {
		if (this.listener != null) {
			this.frame.removeKeyListener(this.listener);
		}
		if (this.world != null) {
			this.world.stop();
		}
		this.world = new World(levelName, this);

		this.listener = new GameListener(this.world,
				this.world.getEntity("player"));
		this.frame.addKeyListener(this.listener);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, super.getWidth(), super.getHeight());
		AbstractEntity[][] entities = this.world.getEntities();
		for (int x = 0; x < entities.length; x++) {
			for (int y = 0; y < entities[x].length; y++) {
				if (entities[x][y] != null) {
					entities[x][y].drawOn(g);
				}
			}
		}

	}
}
