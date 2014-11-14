package trittimo.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import trittimo.World;

public class Dirt extends Sprite {

	public Dirt(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImagePath() {
		return "resources/dirt.png";
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canMove(AbstractEntity entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		return "dirt";
	}

	@Override
	public void drawOn(Graphics g) {
		Point coords = this.getAbsolutePosition();
		Dimension size = this.getSize();
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(new Color(0f, 0f, 0f, 0.25f));

		g2.fillRect(coords.x, coords.y, size.width, size.height);
		super.drawOn(g);
	}

}
