package trittimo;

public class GraphicsTimer implements Runnable {
	private static final int UPDATE_RATE = 1000 / 30;
	
	
	private WorldComponent component;
	public GraphicsTimer(WorldComponent component) {
		this.component = component;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(UPDATE_RATE);
				component.repaint();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
