package es.ucm.fdi.tp.base.model;

import java.io.Serializable;

/**
 * A player action which can be applied to a GameState state to change it.
 * A typical action in a board game is to set or move a piece on the board.
 * A typical action in a card game is to place a card on the table, possibly
 * moving other cards into player's hands as a result.
 */
public interface GameAction<S extends GameState<S, A>,
							A extends GameAction<S, A> > extends Serializable {
    /**
     * returns the player that requested the action
     * @return 0 for the first player, 1 for the second, and so on
     */
    int getPlayerNumber();

    /**
     * applies this action on a game state, returning a new state
     */
    S applyTo(S state);
}
