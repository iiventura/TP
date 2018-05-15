package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * Encapsulates the part of an artificial player that chooses actions.
 */
public interface AiAlgorithm {

    /**
     * @param playerNumber for which to choose action
     * @param state for which to choose action
     * @return the chosen action
     */
	<S extends GameState<S,A>, A extends GameAction<S,A>> A chooseAction(int playerNumber, S state);
}
