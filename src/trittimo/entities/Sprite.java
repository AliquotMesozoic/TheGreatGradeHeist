package trittimo.entities;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import trittimo.World;

public abstract class Sprite extends AbstractEntity {
	public abstract String getImagePath();
	protected Image sprite;
	
	public Sprite(World world) {
		super(world);
		loadSprite(getImagePath());
	}
	
	public void loadSprite(String path) {
		ImageIcon image = new ImageIcon(path);
		this.sprite = image.getImage();
	}

	@Override
	public void drawOn(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Point position = this.getAbsolutePosition();
		Dimension size = this.getSize();
		g2.drawImage(sprite, position.x, position.y, size.width, size.height, null);
	}

}
