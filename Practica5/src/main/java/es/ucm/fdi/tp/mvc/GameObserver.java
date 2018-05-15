package es.ucm.fdi.tp.mvc;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * Can be notified of GameEvents for a particular game
 */
public interface GameObserver<S extends GameState<S,A>, A extends GameAction<S,A>> {
    /**
     * Notifies the observer of an event. Typically called by a GameObservable
     * that this observer has registered with
     * @param e the event
     */
    void notifyEvent(GameEvent<S,A> e);
}