package es.ucm.fdi.tp.chess;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by mfreire on 25/04/17.
 */
public class ChessBoardTest {

    @Test
    public void testGet() {
        ChessBoard cb = new ChessBoard();
        assertEquals("borders are outside", cb.get(-1, -2), ChessBoard.OUTSIDE);
        assertEquals("borders are outside", cb.get(8, 0), ChessBoard.OUTSIDE);

        assertEquals("centers are inside", cb.get(3, 4), ChessBoard.EMPTY);

        assertTrue("black pieces are black", ChessBoard.black(cb.get(1, 1)));
        assertTrue("black pieces are black", ChessBoard.black(cb.get(0, 1)));
        assertTrue("black pieces are black", ChessBoard.black(cb.get(0, 4)));

        assertTrue("white pieces are white", ChessBoard.white(cb.get(6, 1)));
        assertTrue("white pieces are white", ChessBoard.white(cb.get(7, 1)));
        assertTrue("white pieces are white", ChessBoard.white(cb.get(7, 1)));
    }

    public static void main(String ... args) {
        ChessBoard cb = new ChessBoard();
        System.out.println(cb.toString());
    }
}
