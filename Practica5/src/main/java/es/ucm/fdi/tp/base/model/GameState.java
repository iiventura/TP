package es.ucm.fdi.tp.base.model;

import java.io.*;
import java.util.List;

/**
 * Contains the internal state of a game.
 * This includes everything needed to know which actions are valid
 * and which ones are not, and to draw a representation of the state.
 * States are immutable
 *  - applying an action to a state should must in a new GameState.
 *  - once created, states should be unmodifiable
 */
public abstract class GameState<S extends GameState<S, A>,
								A extends GameAction<S, A>> 
		implements Serializable {

    private static final long serialVersionUID = -3154723370370038046L;

    protected int playerCount;

    public GameState(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * @return the current number of players
     */
    public int getPlayerCount() {
        return playerCount;
    }

    /**
     * @return the index of a player with valid actions
     */
    public abstract int getTurn();

    /**
     * lists valid actions for a player.
     * @param playerNumber to generate actions for
     * @return a list of all valid actions for the specified player
     */
    public abstract List<A> validActions(int playerNumber);

    /**
     * @return true if game has finished
     * (and therefore, there is a winner or a draw)
     */
    public abstract boolean isFinished();

    /**
     * returns the playerNumber of the winner, if any.
     *
     * @return -1 if no winner (not finished or draw);
     * 0 if first player won, 1 if second player, and so on.
     */
    public abstract int getWinner();

    /**
     * Evaluates how close playerNumber is to winning (1) or losing (-1).
     * A draw or game that has not started is considered neutral.
     *
     * @param playerNumber to evaluate for
     * @return 0 if the game is a draw, or not yet in play and not won,
     * 1 (-1) if the game is a win (loss) for that player,
     * intermediate values for other scenarios.
     */
    public double evaluate(int playerNumber) {
        return isFinished() ?
            (playerNumber == getWinner() ? 1 : -1) :
            // default implementation returns 0 for all intermediate cases
            0;
    }

    /**
     * @param file to save to; can later be loaded via <code>load</code>
     */
    public void save(File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
        try (ObjectOutputStream oos = new ObjectOutputStream(fout)) {
        	oos.writeObject(this);
        } catch (IOException ioe) {
        	throw new IOException("Error saving to '" 
				+ file.getAbsolutePath() + "':", ioe);
        }
    }

    /**
     * @param file written in a previous call to <code>save()</code>
     */
    public static GameState<?,?> load(File file) throws IOException {
        FileInputStream fin = new FileInputStream(file);
        try (ObjectInputStream ois = new ObjectInputStream(fin)) {
        	return (GameState<?,?>) ois.readObject();
        } catch (ClassNotFoundException cnfe) {
        	throw new IOException("Error loading from '" 
				+ file.getAbsolutePath() + "':", cnfe);
        }
    }
}
