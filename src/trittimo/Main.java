package trittimo;

import java.awt.Dimension;

import javax.swing.JFrame;

import trittimo.util.Constants;

public class Main {

	// TODO Replace this with a dynamic size at some point
	public static final Dimension FRAME_SIZE = new Dimension(800, 800);

	private static JFrame frame;

	public static void main(String[] args) {
		frame = new JFrame("The Great Grade Heist");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(FRAME_SIZE);

		WorldComponent world = new WorldComponent(Constants.LEVELS[0], frame);

		frame.add(world);

		frame.setVisible(true);

	}

	public static void setTitle(String title) {
		Main.frame.setTitle(title);
	}
}
