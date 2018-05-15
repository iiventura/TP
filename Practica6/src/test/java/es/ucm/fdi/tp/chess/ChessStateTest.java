package es.ucm.fdi.tp.chess;

import org.junit.Test;

import static es.ucm.fdi.tp.chess.ChessState.BLACK;
import static es.ucm.fdi.tp.chess.ChessState.WHITE;
import static es.ucm.fdi.tp.chess.ChessState.threatenedBy;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by mfreire on 25/04/17.
 */
public class ChessStateTest {

    @Test
    public void testInitialMoves() {
        ChessState cs = new ChessState();
        assertEquals("initial move count", 8+8+4, cs.validActions(WHITE).size());
        assertTrue("initial openings", cs.isValid(new ChessAction(
                WHITE, 6, 4, 4, 4)));
        assertEquals("initial move count", 8+8+4, cs.validActions(WHITE).size());
    }

    @Test
    public void testThreats() {
        ChessState cs = new ChessState();
        cs = new ChessAction(WHITE, 6, 4, 4, 4).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 5, 2, 5).applyTo(cs);
        assertTrue("white pawn threatens diagonals",
                threatenedBy(cs.getBoard(), WHITE, 3, 3));
        assertTrue("white pawn threatens diagonals",
                threatenedBy(cs.getBoard(), WHITE, 3, 5));
        assertTrue("black pawn threatens diagonals",
                threatenedBy(cs.getBoard(), BLACK, 3, 4));
        assertTrue("black pawn threatens diagonals",
                threatenedBy(cs.getBoard(), BLACK, 3, 6));
        assertTrue("white queen threatens diagonals",
                threatenedBy(cs.getBoard(), WHITE, 3, 7));
        assertTrue("others unthreatened 1",
                !threatenedBy(cs.getBoard(), WHITE, 4, 0));
        assertTrue("others unthreatened 2",
                !threatenedBy(cs.getBoard(), BLACK, 5, 0));
    }

    @Test
    public void testEnPassant() {
        ChessState cs = new ChessState();
        cs = new ChessAction(WHITE, 6, 4, 4, 4).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 7, 2, 7).applyTo(cs);
        cs = new ChessAction(WHITE, 4, 4, 3, 4).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 5, 3, 5).applyTo(cs);

        // cs.validActions(WHITE).forEach(System.out::println);

        assertTrue("white can capture en-passant",
                cs.validActions(WHITE).contains(
                        new ChessAction(WHITE, 3, 4, 2, 5,
                                ChessAction.Special.EnPassant)
                ));
        cs = new ChessAction(WHITE, 3, 4, 2, 5,
                ChessAction.Special.EnPassant).applyTo(cs);

        // System.err.println(cs);

        assertTrue("Capturing pawn ok", cs.getBoard().get(2, 5)
                == ChessBoard.Piece.Pawn.white());
        assertTrue("Captured pawn now gone", cs.getBoard().get(3, 5)
                == ChessBoard.EMPTY);
    }


    @Test
    public void testCastling() {
        ChessState cs = new ChessState();
        cs = new ChessAction(WHITE, 6, 1, 5, 1).applyTo(cs);
        ChessBoard board = cs.getBoard();
        for (int i : new int[] {1, 2, 3, 5, 6}) {
            board.set(7, i, ChessBoard.EMPTY);
        }

        ChessState c2 = new ChessState(cs, board, cs.canCastle(), -1, false);
        // c2.validActions(WHITE).forEach(System.out::println);

        assertTrue("white can long-castle",
                c2.validActions(WHITE).contains(
                        new ChessAction(WHITE, 7, 4, 7, 2,
                                ChessAction.Special.LongCastle)
                ));
        assertTrue("white can short-castle",
                c2.validActions(WHITE).contains(
                        new ChessAction(WHITE, 7, 4, 7, 6,
                                ChessAction.Special.ShortCastle)
                ));

        c2 = new ChessState(cs, board, cs.canCastle(), -1, true);
        assertFalse("white cannot long-castle while in check",
                c2.validActions(WHITE).contains(
                        new ChessAction(WHITE, 7, 4, 7, 2,
                                ChessAction.Special.LongCastle)
                ));
        assertFalse("white cannot short-castle while in check",
                c2.validActions(WHITE).contains(
                        new ChessAction(WHITE, 7, 4, 7, 6,
                                ChessAction.Special.ShortCastle)
                ));

        board.set(6, 2, ChessBoard.Piece.Pawn.black());
        c2 = new ChessState(cs, board, cs.canCastle(), -1, false);
        assertFalse("white cannot long-castle through threat",
                c2.validActions(WHITE).contains(
                        new ChessAction(WHITE, 7, 4, 7, 2,
                                ChessAction.Special.LongCastle)
                ));
    }

    @Test
    public void testMovesAvoidCheck() {
        /*
             0 1 2 3 4 5 6 7
             R N B Q . B N R - 0
             . P P P P K P P - 1
             P . . . . . . . - 2
             . . . . . P . . - 3
             p . . . . . . . - 4
             . p . . . . . . - 5
             . b p p p p p p - 6
             r n . q k b n r - 7
            whiteCastle: 3 blackCastle: 0 black to play
         */
        ChessState cs = new ChessState();

        cs = new ChessAction(WHITE, 6, 1, 5, 1).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 0, 2, 0).applyTo(cs);
        cs = new ChessAction(WHITE, 6, 0, 5, 0).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 5, 3, 5).applyTo(cs);
        cs = new ChessAction(WHITE, 5, 0, 4, 0).applyTo(cs);
        cs = new ChessAction(BLACK, 0, 4, 1, 5).applyTo(cs);
        cs = new ChessAction(WHITE, 7, 2, 6, 1).applyTo(cs);

        //cs.validActions(BLACK).forEach(System.out::println);

        assertTrue("king at 2,5 would be eaten",
                threatenedBy(cs.getBoard(), WHITE, 2, 5));

        assertFalse("cannot move king into threat",
                cs.validActions(BLACK).contains(
                        new ChessAction(BLACK, 1, 5, 2, 5)
                ));

    }

    @Test
    public void testFoolsMate() {
        ChessState cs = new ChessState();
        cs = new ChessAction(WHITE, 6, 4, 4, 4).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 5, 2, 5).applyTo(cs);
        cs = new ChessAction(WHITE, 6, 7, 5, 7).applyTo(cs);
        cs = new ChessAction(BLACK, 1, 6, 3, 6).applyTo(cs);
        cs = new ChessAction(WHITE, 7, 3, 3, 7).applyTo(cs);
        assertTrue("white wins", cs.getWinner() == WHITE);
    }

    public static void main(String ... args) {
        ChessState cs = new ChessState();
        System.out.println(cs.toString());
        cs.validActions(WHITE).forEach(System.out::println);
    }
}
