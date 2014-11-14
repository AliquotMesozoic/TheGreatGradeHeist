package trittimo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import trittimo.entities.AbstractEntity;
import trittimo.entities.Laser;

public class GameListener implements KeyListener {

	private static final int MAX_LEVEL = 3;
	private static final int MIN_LEVEL = 1;

	private static int currentLevel = 1;
	private World world;
	AbstractEntity player;
	private boolean isPressed = false;

	public GameListener(World world, AbstractEntity player) {
		this.world = world;
		this.player = player;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (this.world.isPaused() && e.getKeyCode() != KeyEvent.VK_P) {
			return;
		}
		switch (e.getKeyCode()) {
		case KeyEvent.VK_D:
			if (!this.isPressed) {
				this.player.move(Direction.RIGHT);
				this.isPressed = true;
			}
			break;
		case KeyEvent.VK_W:
			if (!this.isPressed) {
				this.player.move(Direction.UP);
				this.isPressed = true;
			}
			break;
		case KeyEvent.VK_A:
			if (!this.isPressed) {
				this.player.move(Direction.LEFT);
				this.isPressed = true;
			}

			break;
		case KeyEvent.VK_S:
			if (!this.isPressed) {
				this.player.move(Direction.DOWN);
				this.isPressed = true;
			}
			break;
		case KeyEvent.VK_SPACE:
			new Laser(this.world, this.player.getDirection());
			break;
		case KeyEvent.VK_F:
			if (currentLevel < MAX_LEVEL) {
				currentLevel++;
				this.world.reloadMap("resources/level_0" + currentLevel
						+ ".txt");
			}

			break;
		case KeyEvent.VK_B:
			if (currentLevel > MIN_LEVEL) {
				currentLevel--;
				this.world.reloadMap("resources/level_0" + currentLevel
						+ ".txt");
			}
			break;
		case KeyEvent.VK_P:
			this.world.setPaused(!this.world.isPaused());
		default:
			this.isPressed = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.isPressed = false;
	}

}
