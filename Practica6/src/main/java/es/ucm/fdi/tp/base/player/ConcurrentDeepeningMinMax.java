package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * A concurrent, iterative-deepening min-max, built atop a normal min-max.
 *
 * @author mfreire
 */
public class ConcurrentDeepeningMinMax {

    private static final Logger log = Logger.getLogger(
            ConcurrentDeepeningMinMax.class.getName());

    private MinMax.Node<?,?> bestNode;
    private int threads;

    /**
     * Counts the number of board evaluations within min-max, across all threads.
     * Since this is updated by concurrent threads, we use AtomicInteger to
     * guarantee that no data races will occur.
     */
    private AtomicInteger evaluationCount = new AtomicInteger();

    /**
     * Constructor.
     * @param threads to run concurrently. The actual number of concurrently running
     *                threads can be calculated as
     *                Min(threads, actualProcessors, validMovesInInitialState)
     */
    public ConcurrentDeepeningMinMax(int threads) {
        this.threads = threads;
    }

    /**
     * Searches for the best action for the specified player in the provided state.
     * Search-depth is increased until the time runs out, and the deepest
     * fully-explored result is used when calculating "best".
     *
     * @param state to examine
     * @param playerNumber that is due to move
     * @param millis (ms) to invest in searching this space
     * @param <S> state type
     * @param <A> action (move) type
     * @return a node with the best move and its expected value. If no level was
     *  fully explored, returns a random move with -Infinity as value. Once this
     *  method has executed, getEvaluationCount returns the total number of board
     *  evaluations performed during the search.
     */
    @SuppressWarnings("unchecked")
	public <S extends GameState<S,A>, A extends GameAction<S,A>> MinMax.Node<S,A>
            chooseNode(S state, int playerNumber, int millis) {

        evaluationCount.set(0);
        List<A> validActions = state.validActions(playerNumber);

        // choose random best-initial-action, and consider it very-bad
        bestNode = new MinMax.Node<>(
                validActions.get(new Random().nextInt(validActions.size())),
                Double.NEGATIVE_INFINITY);

        long remainingMillis = millis;
        boolean timerExpired = false;
        ArrayList<Future<MinMax.Node<S, A>>> level = new ArrayList<>();
        for (int depth = 1; ! timerExpired && remainingMillis > 0; depth++) {

            // start timing
            long t = System.currentTimeMillis();

            MinMax mm = new MinMax(depth, () -> evaluationCount.incrementAndGet());
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            try {
                // for this level, submit one task per possible move
                for (A a : validActions) {
                    level.add(executor.submit(() -> {
                        // calls min-max on the state that results after applying a
                        S afterMove = a.applyTo(state);
                        return new MinMax.Node<>(a, -mm.chooseNode(
                                afterMove.getTurn(), afterMove).getValue());
                    }));
                }

                // wait for all to finish
                executor.shutdown();
                executor.awaitTermination(remainingMillis, TimeUnit.MILLISECONDS);
                // and send InterruptedException to those that did not finish in time
                executor.shutdownNow();
                // substract current level calculation time
                remainingMillis -= (System.currentTimeMillis() - t);
                // calculate best move
                for (Future<MinMax.Node<S,A>> f : level) {
                    if (f.get().getValue() > bestNode.getValue()) {
                        // if a Future was interrupted, get throws ExecutionException
                        log.fine("Better yet: " + f.get());
                        bestNode = f.get();
                    }
                }
                log.fine("Level " + depth + " complete! Let us go deeper! " + remainingMillis + " left...");
            } catch (ExecutionException | RejectedExecutionException e) {
                timerExpired = true;
            } catch (InterruptedException e) {
                timerExpired = true;
                bestNode = null;
            }
            level.clear();
        }
        log.fine("Timer rang after " + getEvaluationCount() + " evals");
        if (Thread.currentThread().isInterrupted()) {
        	return null;
        } else {
        	return (MinMax.Node<S,A>)bestNode;
        }
    }

    /**
     * @return the number of board evaluations performed during the last
     *  chooseNode() search
     */
    public int getEvaluationCount() {
        return evaluationCount.get();
    }
}
