package engine;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import engine.DrawManager.SpriteType;

/**
 * Manages files used in the application.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public final class FileManager {

	/** Singleton instance of the class. */
	private static FileManager instance;
	/** Application logger. */
	private static Logger logger;
	/** Max number of high scores. */
	private static final int MAX_SCORES = 7;

	/**
	 * private constructor.
	 */
	private FileManager() {
		logger = Core.getLogger();
	}

	/**
	 * Returns shared instance of FileManager.
	 * 
	 * @return Shared instance of FileManager.
	 */
	protected static FileManager getInstance() {
		if (instance == null)
			instance = new FileManager();
		return instance;
	}

	/**
	 * Loads sprites from disk.
	 * 
	 * @param spriteMap
	 *            Mapping of sprite type and empty boolean matrix that will
	 *            contain the image.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public void loadSprite(final Map<SpriteType, boolean[][]> spriteMap)
			throws IOException {
		InputStream inputStream = null;

		try {
			inputStream = DrawManager.class.getClassLoader()
					.getResourceAsStream("graphics");
			char c;

			// Sprite loading.
			for (Map.Entry<SpriteType, boolean[][]> sprite : spriteMap
					.entrySet()) {
				for (int i = 0; i < sprite.getValue().length; i++)
					for (int j = 0; j < sprite.getValue()[i].length; j++) {
						do
							c = (char) inputStream.read();
						while (c != '0' && c != '1');

						if (c == '1')
							sprite.getValue()[i][j] = true;
						else
							sprite.getValue()[i][j] = false;
					}
				logger.fine("Sprite " + sprite.getKey() + " loaded.");
			}
			if (inputStream != null)
				inputStream.close();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	/**
	 * Loads a font of a given size.
	 * 
	 * @param size
	 *            Point size of the font.
	 * @return New font.
	 * @throws IOException
	 *             In case of loading problems.
	 * @throws FontFormatException
	 *             In case of incorrect font format.
	 */
	public Font loadFont(final float size) throws IOException,
			FontFormatException {
		InputStream inputStream = null;
		Font font;

		try {
			// Font loading.
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("font.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(
					size);
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return font;
	}

	/**
	 * Returns the application default scores if there is no user high scores
	 * file.
	 * 
	 * @return Default high scores.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	private List<Score> loadDefaultHighScores() throws IOException {
		List<Score> highScores = new ArrayList<Score>();
		InputStream inputStream = null;
		BufferedReader reader = null;

		try {
			inputStream = FileManager.class.getClassLoader()
					.getResourceAsStream("scores");
			reader = new BufferedReader(new InputStreamReader(inputStream));

			Score highScore = null;
			String name = reader.readLine();
			String score = reader.readLine();

			while ((name != null) && (score != null)) {
				highScore = new Score(name, Integer.parseInt(score));
				highScores.add(highScore);
				name = reader.readLine();
				score = reader.readLine();
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return highScores;
	}

	/**
	 * Loads high scores from file, and returns a sorted list of pairs score -
	 * value.
	 * 
	 * @return Sorted list of scores - players.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public List<Score> loadHighScores() throws IOException {

		List<Score> highScores = new ArrayList<Score>();
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);
			inputStream = new FileInputStream(scoresFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading user high scores.");

			Score highScore = null;
			String name = bufferedReader.readLine();
			String score = bufferedReader.readLine();

			while ((name != null) && (score != null)) {
				highScore = new Score(name, Integer.parseInt(score));
				highScores.add(highScore);
				name = bufferedReader.readLine();
				score = bufferedReader.readLine();
			}

		} catch (FileNotFoundException e) {
			// loads default if there's no user scores.
			logger.info("Loading default high scores.");
			highScores = loadDefaultHighScores();
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		Collections.sort(highScores);
		return highScores;
	}

	/**
	 * Saves user high scores to disk.
	 * 
	 * @param highScores
	 *            High scores to save.
	 * @throws IOException
	 *             In case of loading problems.
	 */
	public void saveHighScores(final List<Score> highScores) 
			throws IOException {
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String scoresPath = new File(jarPath).getParent();
			scoresPath += File.separator;
			scoresPath += "scores";

			File scoresFile = new File(scoresPath);

			if (!scoresFile.exists())
				scoresFile.createNewFile();

			outputStream = new FileOutputStream(scoresFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, Charset.forName("UTF-8")));

			logger.info("Saving user high scores.");

			// Saves 7 or less scores.
			int savedCount = 0;
			for (Score score : highScores) {
				if (savedCount >= MAX_SCORES)
					break;
				bufferedWriter.write(score.getName());
				bufferedWriter.newLine();
				bufferedWriter.write(Integer.toString(score.getScore()));
				bufferedWriter.newLine();
				savedCount++;
			}

		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}
	public Player loadPlayer(char[] name) throws IOException {

		Player player = null;
		InputStream inputStream = null;
		BufferedReader bufferedReader = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, "UTF-8");

			String playerPath = new File(jarPath).getParent();
			playerPath += File.separator;
			playerPath += "accounts";

			File playerFile = new File(playerPath);
			inputStream = new FileInputStream(playerFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, Charset.forName("UTF-8")));

			logger.info("Loading Account.");

			String loadedName = bufferedReader.readLine();
			String currency = bufferedReader.readLine();

			while ((loadedName != null) && (currency != null)) {
				if (loadedName.equals(String.valueOf(name))) {
					player = new Player(loadedName, Integer.parseInt(currency));
					logger.info(String.valueOf(player.getCurrency()));

					break;
				}else {
					loadedName = bufferedReader.readLine();
					currency = bufferedReader.readLine();
				}
			}

		} catch (FileNotFoundException e) {
			// create new player if player not found.
			logger.info("Account list not found.");
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		return player;
	}
	public void saveNewPlayer(final char[] name)
			throws IOException {
		OutputStream outputStream = null;
		BufferedWriter bufferedWriter = null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8);

			String playerPath = new File(jarPath).getParent();
			playerPath += File.separator;
			playerPath += "accounts";

			File playerFile = new File(playerPath);

			playerFile.createNewFile();

			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(playerFile, true), "UTF-8"));

			logger.info("Creating new user");

				bufferedWriter.write(String.valueOf(name));
				bufferedWriter.newLine();
				bufferedWriter.write("0");
				bufferedWriter.newLine();
		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
		}
	}
	public void updateCurrency(final char[] name, int difference)
			throws IOException {
		BufferedWriter bufferedWriter = null;
		BufferedReader bufferedReader =null;

		try {
			String jarPath = FileManager.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();
			jarPath = URLDecoder.decode(jarPath, StandardCharsets.UTF_8);

			String playerPath = new File(jarPath).getParent();
			playerPath += File.separator;
			playerPath += "accounts.txt";

			File playerFile = new File(playerPath);

			InputStream inputStream = new FileInputStream(playerFile);
			bufferedReader = new BufferedReader(new InputStreamReader(
					inputStream, StandardCharsets.UTF_8));

			StringBuilder inputBuffer = new StringBuilder();

			String line;
			while ((line = bufferedReader.readLine()) != null) {
				inputBuffer.append(line);
				inputBuffer.append('\n');
			}
			String inputStr = inputBuffer.toString();
			System.out.println(inputStr); // display the original file for debugging
			int newBalance = loadPlayer(name).getCurrency()+difference;
			inputStr = inputStr.replace(
					String.valueOf(name)+"\n"+ loadPlayer(name).getCurrency()+"\n",
					String.valueOf(name)+"\n" + newBalance+"\n");
			System.out.println("----------------------------------\n" + "current Balance: " +inputStr);

			OutputStream outputStream = new FileOutputStream(playerFile);
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					outputStream, StandardCharsets.UTF_8));

			outputStream.write(inputStr.getBytes());

			logger.info("Successfully changed amount of player");

		} finally {
			if (bufferedWriter != null)
				bufferedWriter.close();
			if (bufferedReader != null)
				bufferedReader.close();
		}
	}
}
