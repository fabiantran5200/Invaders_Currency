package engine;

import java.util.List;

/**
 * Implements a high currency record.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Player implements Comparable<Player> {

	/** Player's name. */
	private String name;
	/** currency points. */
	private int currency;
	/** item list */
	private List<Boolean> item;

	/**
	 * Constructor.
	 *
	 * @param name
	 *            Player name, three letters.
	 * @param currency
	 *            Player currency.
	 */
	public Player(final String name, final int currency, final List<Boolean> item) {
		this.name = name;
		this.currency = currency;
		this.item = item;
	}

	/**
	 * Getter for the player's name.
	 * 
	 * @return Name of the player.
	 */
	public final String getName() {
		return this.name;
	}

	/**
	 * Getter for the player's currency.
	 * 
	 * @return currency.
	 */
	public final int getCurrency() {
		return this.currency;
	}

	public final List<Boolean> getItem() {
		return this.item;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public void setItem(List<Boolean> item) {
		this.item = item;
	}

	/**
	 * Orders the currencys descending by currency.
	 * 
	 * @param currency
	 *            currency to compare the current one with.
	 * @return Comparison between the two currencys. Positive if the current one is
	 *         smaller, positive if its bigger, zero if its the same.
	 */
	@Override
	public final int compareTo(final Player currency) {
		int comparison = this.currency < currency.getCurrency() ? 1 : this.currency > currency
				.getCurrency() ? -1 : 0;
		return comparison;
	}

}
