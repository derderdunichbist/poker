package Interfaces;

public interface iController {
	
	/**
	 * Starts the game by
	 * <ul>
	 * <li>checking if minimum amount of players have joined</li>
	 * <li>invoking {@link #newRound()}
	 */
	public void startGame();
	
	/**
	 * Adds a player with a specified name to the next empty seat
	 * @param name The players name
	 */
	public void addPlayer(String name);
	
	/**
	 * Removes a player with a specified seat number
	 * @param seatNumber The seat number to remove the player from
	 */
	public void removePlayer(int seatNumber);
	
	/**
	 * Removes a player with a specified name
	 * @param playerName The name of the player who will be removed from the game
	 */
	public void removePlayer(String playerName);

}
