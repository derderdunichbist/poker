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
	 * Starts a new round
	 * Added also for testing purposes etc. Consider refactoring
	 */
	public void newRound();

}
