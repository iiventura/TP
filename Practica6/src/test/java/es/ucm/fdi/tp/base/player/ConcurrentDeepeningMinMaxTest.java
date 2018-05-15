package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.chess.ChessAction;
import es.ucm.fdi.tp.chess.ChessState;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by mfreire on 25/04/17.
 */
public class ConcurrentDeepeningMinMaxTest {

    @Test
    public void simpleTest() {
        ConcurrentDeepeningMinMax cmm1 = new ConcurrentDeepeningMinMax(1);
        ConcurrentDeepeningMinMax cmm2 = new ConcurrentDeepeningMinMax(4);

        ChessState cs = new ChessState();

        long t;
        t = System.currentTimeMillis();
        //MinMax.Node<ChessState, ChessAction> n1 = cmm1.chooseNode(cs, ChessState.WHITE, 2000);
        System.err.println("after CMM1: " + (System.currentTimeMillis() - t));

        t = System.currentTimeMillis();
        MinMax.Node<ChessState, ChessAction> n2 = cmm2.chooseNode(cs, ChessState.WHITE, 2000);
        System.err.println("after CMM2: " + (System.currentTimeMillis() - t));

        System.err.println("" + cmm1.getEvaluationCount() + " vs " + cmm2.getEvaluationCount());

        assertTrue("move is valid", cs.isValid(n2.getMove()));
        assertTrue("more threads is faster", cmm1.getEvaluationCount() < cmm2.getEvaluationCount());
    }
}
