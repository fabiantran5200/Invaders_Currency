package screen;

import engine.Cooldown;
import engine.Core;
import engine.Player;
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

	private static final int SELECTION_TIME = 100;

	public ItemShopScreen(final int width, final int height, final int fps) {
		super(width, height, fps);
		//defaults to center
		this.returnCode = 1;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
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
	private Cooldown selectionCooldown;
	protected final void update() {
		super.update();

		draw();
		if (this.selectionCooldown.checkFinished() && this.inputDelay.checkFinished()) {
			if (inputManager.isKeyDown(KeyEvent.VK_LEFT) || inputManager.isKeyDown(KeyEvent.VK_A)) {
				previousMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_RIGHT) || inputManager.isKeyDown(KeyEvent.VK_D)) {
				nextMenuItem();
				this.selectionCooldown.reset();
			}
			if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
				// Determine the selected item
				int selectedItem = this.returnCode;
				int itemPrice = 0;

				// Update currency and item based on the selected item
				if (selectedItem == 0) {
					itemPrice = 10;
					logger.info("Player bought Speed item successfully");
				} else if (selectedItem == 1) {
					itemPrice = 15;
					logger.info("Player bought Additional Health item successfully");
				} else if (selectedItem == 2) {
					itemPrice = 20;
					logger.info("Player bought Shooting Speed item successfully");
				}

			}
			if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
				this.isRunning = false;
			}
		}
	}


	/**
	 * Shifts the focus to the next menu item (horizontally).
	 */
	private void nextMenuItem() {
		if (this.returnCode == 2)
			this.returnCode = 0;
		else if (this.returnCode == 0)
			this.returnCode = 1;
		else if (this.returnCode == 1)
			this.returnCode = 2;
	}

	/**
	 * Shifts the focus to the previous menu item (horizontally).
	 */
	private void previousMenuItem() {
		if (this.returnCode == 0)
			this.returnCode = 2;
		else if (this.returnCode == 2)
			this.returnCode = 1;
		else if (this.returnCode == 1)
			this.returnCode = 0;
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawItemShopMenu(this, this.returnCode);

		drawManager.completeDrawing(this);
	}
}