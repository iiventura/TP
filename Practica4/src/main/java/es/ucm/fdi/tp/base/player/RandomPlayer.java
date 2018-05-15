package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import java.util.List;
import java.util.Random;

/**
 * An AI player that plays randomly
 */
public class RandomPlayer extends AiPlayer {
    public RandomPlayer(String name) {
        super(name, new AiAlgorithm() {
            private Random random = new Random();

            @Override
            public <S extends GameState<S,A>, A extends GameAction<S,A>> A chooseAction(int playerNumber, S state) {
				List<A> valid = state.validActions(playerNumber);
                return valid.get(random.nextInt(valid.size()));
            }
        });
    }
}
