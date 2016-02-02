package Backend;

public interface iController {

	/**
	 * Deals cards to the players; calls {@link Poker#burnCards()} for instance
	 */
	public void dealHands();

	/**
	 * Hands 2 cards to each player
	 */
	public void beginRound();

}
