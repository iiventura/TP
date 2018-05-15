package es.ucm.fdi.tp.ttt;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;

/**
 * A TickTackToe state.
 * Describes a board of TickTackToe that is either being
 * played or is already finished.
 */
public class TttState extends GameState<TttState, TttAction> {

	private static final long serialVersionUID = -2641387354190472377L;
	
	private final int turn;
    private final boolean finished;
    private final int[][] board;
    private final int winner;

    private final int dim;

    final static int EMPTY = -1;

    public TttState(int dim) {    	
        super(2);
        if (dim < 3 || dim > 4) {
            throw new IllegalArgumentException("Expected dim to be 3 or 4");
        }

        this.dim = dim;
        board = new int[dim][];
        for (int i=0; i<dim; i++) {
            board[i] = new int[dim];
            for (int j=0; j<dim; j++) board[i][j] = EMPTY;
        }
        this.turn = 0;
        this.winner = -1;
        this.finished = false;
    }
        
    public TttState(TttState prev, int[][] board, boolean finished, int winner) {
    	super(2);
    	this.dim = prev.dim;
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    }    

    public boolean isValid(TttAction action) {
        if (isFinished()) {
            return false;
        }
        return at(action.getRow(), action.getCol()) == EMPTY;
    }

    public List<TttAction> validActions(int playerNumber) {
        ArrayList<TttAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (at(i, j) == -1) {
                    valid.add(new TttAction(playerNumber, i, j));
                }
            }
        }
    
        return valid;
    }

    public static boolean isDraw(int[][] board) {
        boolean empty = false;
        for (int i=0; ! empty && i<board.length; i++) {
            for (int j=0; ! empty && j<board.length; j++) {
                if (board[i][j] == EMPTY) {
                    empty = true;
                }
            }
        }
        return ! empty;
    }

    private static boolean isWinner(int[][] board, int playerNumber, 
    		int x0, int y0, int dx, int dy) {
        boolean won = true;
        for (int i=0, x=x0, y=y0; won && i<board.length; i++, x+=dx, y+=dy) {
            if (board[y][x] != playerNumber) won = false;
        }
        return won;
    }


    public static boolean isWinner(int[][] board, int playerNumber) {
        boolean won = false;
        for (int i=0; !won && i<board.length; i++) {
            if (isWinner(board, playerNumber, 0, i, 1, 0)) won = true;
            if (isWinner(board, playerNumber, i, 0, 0, 1)) won = true;
        }
        return won ||
                isWinner(board, playerNumber, 0, 0, 1, 1) ||
                isWinner(board, playerNumber, 2, 0, -1, 1);
    }    

    public int at(int row, int col) {
        return board[row][col];
    }

    public int getTurn() {
        return turn;
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
    public int[][] getBoard() {
        int[][] copy = new int[board.length][];
        for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<board.length; i++) {
            sb.append("|");
            for (int j=0; j<board.length; j++) {
                sb.append(board[i][j] == EMPTY ? "   |" : board[i][j] == 0 ? " O |" : " X |");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
