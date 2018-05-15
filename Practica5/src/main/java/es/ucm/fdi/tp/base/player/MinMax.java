package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import java.util.List;
import java.util.Random;

/**
 * A simple MinMax implementation.
 * Adapted from code by Simon Pickin
 */
public class MinMax implements AiAlgorithm {

    private int depth;

    public MinMax() {
        this(5);
    }

    public MinMax(int depth) {
        if (depth < 1) {
            throw new IllegalArgumentException(
                    "Invalid depth ('" + depth + "') for the MinMax algorithm, expected > 0");
        }
        this.depth = depth;
    }

    private static class Node<S extends GameState<S,A>, A extends GameAction<S,A>> {
        public A move;
        public double value;
        public Node(A move, double value) {
            this.move = move;
            this.value = value;
        }
    };

    @Override
    public <S extends GameState<S,A>, A extends GameAction<S,A>> A chooseAction(int playerNumber, S state) {
        try {
            Node<S,A> best = minmax(depth,
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                    playerNumber, state);
            return best.move;
        } catch (InterruptedException ie) {
            System.err.println("Interrupted while thinking! Choosing randomly!");
			List<A> valid = state.validActions(playerNumber);
            return valid.get(new Random().nextInt(valid.size()));
        }
    }

    public <S extends GameState<S,A>, A extends GameAction<S,A>> double evaluateAction(A action, int playerNumber, S state) {
        try {
            int d = depth - 1;
            state = action.applyTo(state);
            if (state.isFinished() || d<1) {
                return evaluateFinished(state, d, playerNumber);
            } else {
                Node<S,A> best = minmax(d,
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
                        playerNumber, state);
                return best.value;
            }
        } catch (InterruptedException ie) {
            System.err.println("Interrupted while thinking! Evaluating to zero!");
            return 0;
        }
    }

    private double evaluateFinished(GameState<?,?> state, int d, int playerNumber) {
        double v = state.evaluate(playerNumber);
        if (state.isFinished()) {
            // if winning, try to do it sooner; if losing, try to drag it later
            v *= 1.5 * (d+1);
        }
        return v;
    }

    private <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> minmax(int d, double alpha, double beta, int playerNumber, S state)
            throws InterruptedException {
    	
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }

        if (state.isFinished() || d < 1) {
            // finished or depth reached: evaluate state
            return new Node<S,A>(null, evaluateFinished(state, d, playerNumber));
        }

        // generate all moves
		List<A> actions = state.validActions(state.getTurn());
        assert (actions.size() > 0);

        // max is playerNumber, min are all other players
        if (playerNumber == state.getTurn()) {
            // return better than current best (alpha)
            return max(d, alpha, beta, playerNumber, state, actions);
        } else {
            // return only worse than current worst (beta)
            return min(d, alpha, beta, playerNumber, state, actions);
        }
    }
    
    private <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> max(int d, double alpha, double beta, int player, S state,
                     List<A> actions) throws InterruptedException {

        A chosen = null;
        for (A a : actions) {
			S next = a.applyTo(state);
            Node<S,A> result = minmax(d - 1, alpha, beta, player, next);
            if (result.value > alpha) {
                alpha = result.value;
                chosen = a;
            }
            if (alpha >= beta) {
                return new Node<S,A>(chosen, alpha);
            }
        }

        return new Node<S,A>(chosen, alpha);
    }

    private <S extends GameState<S,A>, A extends GameAction<S,A>> Node<S,A> min(int d, double alpha, double beta, int player, S state,
                     List<A> actions) throws InterruptedException {

        A chosen = null;
        for (A a : actions) {
            S next = a.applyTo(state);
            Node<S,A> result = minmax(d - 1, alpha, beta, player, next);
            if (result.value < beta) {
                beta = result.value;
                chosen = a;
            }
            if (beta <= alpha) {
                return new Node<S,A>(chosen, beta);
            }
        }

        return new Node<S,A>(chosen, beta);
    }
}
