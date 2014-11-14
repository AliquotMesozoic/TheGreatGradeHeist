package trittimo.util;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

import trittimo.World;
import trittimo.entities.AbstractEntity;
import trittimo.entities.Bag;
import trittimo.entities.Dirt;
import trittimo.entities.Emerald;
import trittimo.entities.Hobbin;
import trittimo.entities.Monster;
import trittimo.entities.Nobbin;
import trittimo.entities.Player;
import trittimo.entities.Spawner;

public class LevelLoader {
	public static final String delimeter = "\n";

	private World world;

	public LevelLoader(World world) {
		this.world = world;
	}

	public AbstractEntity[][] load(String levelFilename)
			throws FileNotFoundException {

		Scanner scanner = new Scanner(new File(levelFilename));
		scanner.useDelimiter(delimeter); // Prevents it from detecting spaces

		// Note that the level must begin with two integers indicating
		// the width and height respectively
		char[][] level = new char[scanner.nextInt()][scanner.nextInt()];

		for (int x = 0; x < level.length; x++) {
			String line = scanner.next();
			for (int y = 0; y < level[0].length; y++) {
				level[y][x] = line.charAt(y);
			}
		}

		scanner.close();

		return this.parse(level);
	}

	public AbstractEntity[][] parse(char[][] level) {
		AbstractEntity[][] entities = new AbstractEntity[level.length][level[0].length];
		for (int x = 0; x < entities.length; x++) {
			for (int y = 0; y < entities.length; y++) {
				switch (level[x][y]) {
				case '#':
					entities[x][y] = new Dirt(this.world);
					break;
				case '*':
					entities[x][y] = new Emerald(this.world);
					break;
				case 'N':
					entities[x][y] = new Nobbin(this.world);
					break;
				case 'H':
					entities[x][y] = new Hobbin(this.world);
					break;
				case 'P':
					entities[x][y] = new Player(this.world);
					break;
				case 'B':
					entities[x][y] = new Bag(this.world);
					break;
				case 'S':
					entities[x][y] = new Spawner(this.world);
					break;
				case 'M':
					entities[x][y] = new Monster(this.world);
					break;
				}
				if (entities[x][y] != null) {
					entities[x][y].setInitialPosition(new Point(x, y));
				}
			}
		}

		return entities;
	}

	public static void saveLevel(String fileName, AbstractEntity[][] map) {
		String fileContent = map[0].length + "\n" + map.length + "\n";
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				if (map[x][y] == null) {
					fileContent += " ";
				} else {
					switch (map[x][y].toString()) {
					case "player":
						fileContent += "P";
						break;
					case "hobbin":
						fileContent += "H";
						break;
					case "nobbin":
						fileContent += "N";
						break;
					case "emerald":
						fileContent += "*";
						break;
					case "dirt":
						fileContent += "#";
						break;
					case "bag":
						fileContent += "B";
						break;
					case "monster":
						fileContent += "M";
						break;
					case "spawner":
						fileContent += "S";
						break;
					default:
						fileContent += " ";
					}
				}

			}
			if (y < map[y].length - 1) {
				fileContent += "\n";
			}
		}

		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName)));
			writer.write(fileContent);
		} catch (IOException e) {
			throw new RuntimeException("Cannot save level! Emergency exit!");
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				throw new RuntimeException("Cannot close save! Emergency exit!");
			}
		}
	}
}
