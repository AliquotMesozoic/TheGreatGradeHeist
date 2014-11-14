package trittimo.util;

import java.io.File;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

	public static JFXPanel panel = new JFXPanel();

	private MediaPlayer player;
	private Media media;

	public Sound(String fileName) {
		File file = new File(fileName);
		this.media = new Media(file.toURI().toString());
		this.player = new MediaPlayer(this.media);
	}

	public void start() {
		this.player.play();
	}

	public void start(double length) {
		this.player.play();
		new Thread(new SoundTimer(this.player, length)).start();
	}

	public void stop() {
		this.player.stop();
	}
}

class SoundTimer implements Runnable {

	private int time;
	private MediaPlayer player;

	public SoundTimer(MediaPlayer player, double time) {
		this.time = (int) (time * 1000);
		this.player = player;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(this.time);
			this.player.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
