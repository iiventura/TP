package es.ucm.fdi.tp.base.model;

/**
 * A player of games. Can generate game actions to apply on the game.
 */
public interface GamePlayer {

	/**
	 * joins this player to a game.
	 * 
	 * @param playerNumber
	 *            in the game
	 */
	void join(int playerNumber);

	/**
	 * returns the number assigned to the player when joining the game (-1 if
	 * not joined yet)
	 * 
	 * @return player number
	 */
	int getPlayerNumber();

	/**
	 * @return the player's name. Used when describing the player's actions.
	 */
	String getName();

	/**
	 * demands a move from the player. 
	 */
	<S extends GameState<S, A>, A extends GameAction<S, A>> A requestAction(S state);
}
