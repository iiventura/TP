package es.ucm.fdi.tp.mvc;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * Can send GameEvents to GameObservers
 */
public interface GameObservable<S extends GameState<S, A>, A extends GameAction<S, A>> {
    void addObserver(GameObserver<S,A> o);
    void removeObserver(GameObserver<S,A> o);
}
