package es.ucm.fdi.tp.chess;

import es.ucm.fdi.tp.base.model.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static es.ucm.fdi.tp.chess.ChessAction.Special;
import static es.ucm.fdi.tp.chess.ChessBoard.*;

/**
 * A chess state. Implements all rules except for move-repetition and
 * overly protracted endgames.
 *
 * @author mfreire
 */
@SuppressWarnings("serial")
public class ChessState extends GameState<ChessState, ChessAction> {

    private static final Logger log = Logger.getLogger(ChessState.class.getName());

    private final int turn;
    private boolean finished;
    private int winner;
    private final ChessBoard board;
    private final int canCastle[]; // {white, black}, 0=no, 1=short, 2=long, 3=both
    private final int enPassant; // -1 if none, column of pawn that advanced twice otherwise
    private final boolean inCheck;

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    protected static final int CASTLE_SHORT = 1;
    protected static final int CASTLE_LONG = 2;
    protected static final int CASTLE_DONE = 4;

    protected ArrayList<ChessAction> valid;

    /**
     * Creates an empty chess board;
     */
    public ChessState() {
        super(2);
        turn = WHITE;
        winner = -1;
        finished = false;
        board = new ChessBoard();
        canCastle = new int[]{
                CASTLE_SHORT | CASTLE_LONG, CASTLE_SHORT | CASTLE_LONG};
        enPassant = -1;
        inCheck = false;
        valid = null;
        updateValid();

    }

    /**
     * Creates a chess board with a given position
     * @param previous board (used mostly to look at turn)
     * @param board to use
     * @param canCastle representing the state of castling; canCastle[WHITE] is
     *                  0 if not allowed,
     *                  1 if only short castling still possible,
     *                  2 if only long castling still possible
     *                  3 if both types of castling are still possible
     * @param enPassant with column where enemy pawn just finished a double-advance
     * @param inCheck if king is currently in check
     */
    public ChessState(ChessState previous, ChessBoard board,
                      int[] canCastle, int enPassant, boolean inCheck) {
        super(2);
        this.board = board;
        this.turn = otherPlayer(previous.turn);
        this.canCastle = canCastle;
        this.enPassant = enPassant;
        this.inCheck = inCheck;
        valid = null;
        updateValid();

        if (valid.isEmpty()) {
            finished = true;
            if (inCheck) {
                winner = previous.turn;
            }
        } else {
            finished = false;
            winner = -1;
        }
    }

    /**
     * @param player
     * @return opposite player
     */
    public static int otherPlayer(int player) {
        return player == BLACK ? WHITE : BLACK;
    }

    public boolean isValid(ChessAction action) {
        return valid.contains(action);
    }

    /**
     * @param board to search in
     * @param turn of king to search for
     * @param kingPos to write position into
     * @return true if found, false if not
     */
    protected static boolean findKing(ChessBoard board, int turn, Point kingPos) {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                byte p = board.get(i, j);
                if (sameTurn(p, turn) && Piece.valueOf(p) == Piece.King) {
                    kingPos.setLocation(j, i);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Generates valid moves for current player.
     * This can only be called once per state
     * (as states are immutable, and results get cached).
     */
    private void updateValid() {
        if (valid != null) {
            return;
        }

        int otherTurn = otherPlayer(turn);

        valid = new ArrayList<>();
        ArrayList<ChessAction> candidates = new ArrayList<>();

        // generate
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                byte p = board.get(i, j);
                if (sameTurn(p, turn)) {
                    generateActions(p, turn, i, j, candidates);
                }
            }
        }

        // filter out those that expose king, add checks
        Point myKing = new Point();
        Point kingPos = new Point();
        Point otherKing = new Point();
        if ( ! findKing(board, turn, myKing)) {
            throw new IllegalStateException("king for " + turn + " not found: \n" + board);
        }
        if ( ! findKing(board, otherTurn, otherKing)) {
            throw new IllegalStateException("king for " + otherTurn + " not found: \n" + board);
        }
        for (ChessAction a : candidates) {
            ChessBoard next = new ChessBoard(board);
            a.applyTo(next);
            if (myKing.y == a.getSrcRow() && myKing.x == a.getSrcCol()) {
                kingPos.setLocation(a.getDstCol(), a.getDstRow());
            } else {
                kingPos.setLocation(myKing);
            }

            if ( ! threatenedBy(next, otherTurn, kingPos.y, kingPos.x)) {
                a.setCheck(threatenedBy(next, turn, otherKing.y, otherKing.x));
                valid.add(a);
            }
        }
    }

    /**
     * Returns a list of valid actions for the current player.
     * @param playerNumber
     *            to generate actions for
     * @return
     */
    public List<ChessAction> validActions(int playerNumber) {
        return valid;
    }

    /**
     * Returns the piece (see ChessBoard) at the given position.
     * @param row
     * @param col
     * @return piece at the given position. Note that
     *  chess piece codes are NOT player codes (there are many piece types).
     *  See ChessBoard to make sense of the returned int:
     *  ChessBoard.black(p) and ChessBoard.white(p) will tell you piece-color
     *  ChessBoard.Piece.valueOf(p) will tell you piece-type.
     */
    public int at(int row, int col) {
        return board.get(row, col);
    }

    public int getTurn() {
        return turn;
    }

    @Override
    public double evaluate(int turn) {
        if (finished) {
            return turn == winner ? 1 : -1;
        }

        /*
         * static evaluation of positions in chess is a huge topic;
         * feel free to improve on this heuristic.
         */


        // the greater the piece-value advantage, the better
        double score = 0;
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                byte p = board.get(i, j);
                if (!ChessBoard.empty(p)) {
                    score += .01 * Piece.valueOf(p)
                            .getValue(!sameTurn(p, turn));
                }
            }
        }

        // the more possible moves, the better ("board control")
        score += .0005 * valid.size();

        // castling, or having the option to do so, is good
        score += canCastle[turn] == 0 ? 0 :
                (canCastle[turn] == CASTLE_DONE ? .001 : .0005);

        return score;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getWinner() {
        return winner;
    }

    /**
     * @return a copy of the board
     */
    public ChessBoard getBoard() {
        return new ChessBoard(board);
    }

    public String toString() {
        return board.toString()
                + ("whiteCastle: " + canCastle[0] + " ")
                + ("blackCastle: " + canCastle[1] + " ")
                + (enPassant != -1 ? "passant" + enPassant + " " : "")
                + (turn == WHITE ? "; white" : "black") + " to play\n";
    }

    protected void addPawnAction(int turn, int row, int col,
                                 int dstRow, int dstCol, int queenRow,
                                 ArrayList<ChessAction> as) {
        if (row != queenRow) {
            as.add(new ChessAction(turn, row, col, dstRow, dstCol));
        } else {
            for (Special s : ChessAction.QUEENING_SPECIALS) {
                as.add(new ChessAction(turn, row, col, dstRow, dstCol, s));
            }
        }
    }

    protected void pawnActions(byte p, int turn,
                               int row, int col, ArrayList<ChessAction> as,
                               int dy, int queenRow, int doubleRow, int passantRow) {

        // advance?
        if (empty(board.get(row + dy, col))) {
            addPawnAction(turn, row, col, row + dy, col, queenRow, as);
            if (row == doubleRow && empty(board.get(row + dy + dy, col))) {
                as.add(new ChessAction(turn, row, col, row + dy + dy, col));
            }
        }

        // capture?
        if (enemy(p, board.get(row + dy, col + 1))) {
            addPawnAction(turn, row, col, row + dy, col + 1, queenRow, as);
        }
        if (enemy(p, board.get(row + dy, col - 1))) {
            addPawnAction(turn, row, col, row + dy, col - 1, queenRow, as);
        }

        // en-passant capture?
        if (row == passantRow && enPassant != -1) {
            if (enPassant == col + 1) {
                as.add(new ChessAction(turn, row, col,
                        row + dy, col + 1, Special.EnPassant));
            } else if (enPassant == col - 1) {
                as.add(new ChessAction(turn, row, col,
                        row + dy, col - 1, Special.EnPassant));
            }
        }
    }

    protected void castlingActions(byte p, int turn,
                                   int row, int col, ArrayList<ChessAction> as,
                                   int kingRow) {
        int other = turn == BLACK ? WHITE : BLACK;

        // not in inCheck, empty&non-threatened king transit, empty rook-transit
        if (!inCheck) {
            if ((canCastle[turn] & CASTLE_SHORT) != 0
                    && empty(board.get(kingRow, 5))
                    && !threatenedBy(board, other, kingRow, 5)
                    && empty(board.get(kingRow, 6))
                    && !threatenedBy(board, other, kingRow, 6)) {
                as.add(new ChessAction(turn, row, col,
                        kingRow, 6, Special.ShortCastle));
            }
            if ((canCastle[turn] & CASTLE_LONG) != 0
                    && empty(board.get(kingRow, 1))
                    && empty(board.get(kingRow, 2))
                    && !threatenedBy(board, other, kingRow, 2)
                    && empty(board.get(kingRow, 3))
                    && !threatenedBy(board, other, kingRow, 3)) {
                as.add(new ChessAction(turn, row, col,
                        kingRow, 2, Special.LongCastle));
            }
        }
    }

    protected static boolean pawnCanCapture(byte p, int srcRow, int srcCol,
                                     int dstRow, int dstCol, int dy) {
        return srcRow + dy == dstRow
                && ((srcCol == dstCol + 1) || (srcCol == dstCol - 1));
    }

    /**
     * Used to generate non-pawn actions. Simply looks for empty-or-enemy squares
     * along a given direction, given by row and column deltas. If 'multiple' is se to true,
     * continues looking until non-empty square found (useful for rook, bishop, queen)
     */
    protected void deltaActions(byte p, int turn,
                                int row, int col, int dy, int dx, ArrayList<ChessAction> as,
                                boolean multiple) {
        int dRow = row, dCol = col;
        byte target;
        do {
            dRow += dy;
            dCol += dx;
            target = board.get(dRow, dCol);
            if (empty(target) || enemy(p, target)) {
                as.add(new ChessAction(turn, row, col, dRow, dCol));
            }
        } while (multiple && empty(target));
    }

    protected static boolean canDeltaCapture(ChessBoard b, byte p, int row, int col, int dy, int dx,
                                      int dstRow, int dstCol, boolean multiple) {
        int dRow = row, dCol = col;
        do {
            dRow += dy;
            dCol += dx;
            if (dRow == dstRow && dCol == dstCol) return true;
        } while (multiple && empty(b.get(dRow, dCol)));
        return false;
    }

    protected void generateActions(byte p, int turn,
                                   int row, int col,
                                   ArrayList<ChessAction> as) {
        Piece piece = Piece.valueOf(p);
        if (piece == Piece.Pawn) {
            if (ChessBoard.black(p)) {
                pawnActions(p, turn, row, col, as, 1, 6, 1, 4);
            } else {
                pawnActions(p, turn, row, col, as, -1, 1, 6, 3);
            }
        } else if (piece.getDeltas() != null) {
            for (int[] d : piece.getDeltas()) {
                deltaActions(p, turn, row, col,
                        d[0], d[1], as, piece.hasLinearMotion());
            }
            if (piece == Piece.King) {
                if (ChessBoard.black(p)) {
                    castlingActions(p, turn, row, col, as, 0);
                } else {
                    castlingActions(p, turn, row, col, as, 7);
                }
            }
        }
    }

    /**
     * @param b board to check in
     * @param turn of player that threatens
     * @param row of victim
     * @param col of victim
     * @return true if there is at least 1 piece of turn that can capture the victim
     */
    protected static boolean threatenedBy(ChessBoard b, int turn, int row, int col) {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                byte p = b.get(i, j);
                if (sameTurn(p, turn) && canCapture(b, p, i, j, row, col)) {
                    log.fine("" + row +", "+col+" : by " + i+","+j + " " + Piece.valueOf(p));
                    return true;
                }
            }
        }
        return false;
    }

    protected static boolean canCapture(ChessBoard b, byte p, int row, int col, int dstRow, int dstCol) {
        Piece piece = Piece.valueOf(p);
        if (piece == Piece.Pawn) {
            return pawnCanCapture(p, row, col, dstRow, dstCol,
                    ChessBoard.black(p) ? 1 : -1);
        } else if (piece.getDeltas() != null) {
            for (int[] d : piece.getDeltas()) {
                if (canDeltaCapture(b, p, row, col,
                        d[0], d[1], dstRow, dstCol, piece.hasLinearMotion())) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int[] canCastle() {
        return canCastle.clone();
    }

    /**
     * @return true if the current player is in check
     */
    public boolean isInCheck() {
        return inCheck;
    }
    
    public int getDimension() {
    	return DIM;
    }
    
    
    public String getGameDescription() {
	return "Chess";
    }
    
}