package trittimo;

public class GameTimer implements Runnable {
	private static final int UPDATE_RATE = 1000 / 33;
	
	
	private World world;
	public GameTimer(World world) {
		this.world = world;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(UPDATE_RATE);
				world.update();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

}
