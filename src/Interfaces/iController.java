package Interfaces;

public interface iController {

	/**
	 * Deals all respective cards available in the game: Holdcards and communitycards 
	 */
	public void dealHands();
	
	/**
	 * Adds a player with a specified name to the next empty seat
	 * @param name The players name
	 */
	public void addPlayer(String name);

	/**
	 * Starts a new round; A new round begins after a complete hand is finished.<br>
	 * Added method also for testing purposes etc. Consider refactoring
	 */
	public void newRound();

	/**
	 * Starts the game by
	 * <ul>
	 * <li>checking if minimum amount of players have joined</li>
	 * <li>invoking {@link #newRound()}
	 */
	public void startGame();

}
