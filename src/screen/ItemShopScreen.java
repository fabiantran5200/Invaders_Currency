package screen;

import engine.Core;
import engine.Score;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

/**
 * Implements the high scores screen, it shows player records.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class ItemShopScreen extends Screen {

	public ItemShopScreen(final int width, final int height, final int fps) {
		super(width, height, fps);

		this.returnCode = 1;
	}

	/**
	 * Starts the action.
	 *
	 * @return Next screen code.
	 */
	public final int run() {
		super.run();

		return this.returnCode;
	}

	/**
	 * Updates the elements on screen and checks for events.
	 */
	protected final void update() {
		super.update();

		draw();
		if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
				&& this.inputDelay.checkFinished())
			this.isRunning = false;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);

		drawManager.drawItemShopMenu(this);

		drawManager.completeDrawing(this);
	}
}
