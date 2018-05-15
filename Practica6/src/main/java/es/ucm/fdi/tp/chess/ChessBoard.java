package es.ucm.fdi.tp.chess;

/**
 * Represents a chess board.
 * This is not the full state, only the board component.
 * Uses a somewhat-efficient internal representation
 * to speed up move generation. Using bit-boards would be much faster,
 * but also a lot more work.
 */
public class ChessBoard {

    /*
     * internal representation:
     * 12x12 = 144 bytes, with 8x8 board starting at 2,2 with
     *    1 ...  6 white pieces (1=pawn, 6=king)
     *    9 ... 14 black pieces (9=pawn, 14=king)
     *   16 = empty (0x10)
     *   32 = outside (0x20)
     * this allows many bit-wise tricks to test certain positions,
     * and having a 2-width border avoids checking for bounds.
     */
    private byte[] board;

    protected final static int DIM = 8;
    protected static int BORDER = 2;
    protected static int DIM_WITH_BORDERS = 2 + 8 + 2;

    protected static final byte COLOR_MASK = 0x08;
    protected static final byte EMPTY = 0x10;
    protected static final byte OUTSIDE = 0x20;
    protected static final byte NON_PIECE_MASK = COLOR_MASK | EMPTY | OUTSIDE;

    private static final int[][] DIAGONALS = new int[][]{
            {1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    private static final int[][] ROWS_N_COLS = new int[][]{
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private static final int[][] KNIGHT_JUMPS = new int[][]{
            {1, 2}, {1,-2}, {-1, 2}, {-1,-2},
            {2, 1}, {2,-1}, {-2, 1}, {-2,-1}};
    private static final int[][] DIAGONALS_ROWS_N_COLS = new int[][]{
            {1, 1}, {-1, 1}, {1, -1}, {-1, -1},
            {1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static boolean white(byte p) {
        return (p & NON_PIECE_MASK) == 0;
    }

    public static boolean black(byte p) {
        return (p & NON_PIECE_MASK) == COLOR_MASK;
    }

    /**
     * checks if a piece has a given turn
     * @param p board-piece
     * @param turn (WHITE=0 or BLACK=1)
     * @return true if and only if piece is of same turn as indicated
     */
    public static boolean sameTurn(byte p, int turn) {
        return (p & NON_PIECE_MASK) == (turn << 3);
    }

    public static boolean outside(byte p) {
        return p == OUTSIDE;
    }

    public static boolean empty(byte p) {
        return p == EMPTY;
    }

    public static boolean friend(byte a, byte b) {
        // assumes that a or b is non-empty, non-outside
        return ((a ^ b) & NON_PIECE_MASK) == 0;
    }

    public static boolean enemy(byte a, byte b) {
        // assumes that a or b is non-empty, non-outside
        return ((a ^ b) & NON_PIECE_MASK) == COLOR_MASK;
    }

    public byte get(int row, int col) {
        return board[(row+BORDER) * DIM_WITH_BORDERS + (col+BORDER)];
    }

    public byte set(int row, int col, byte p) {
        return board[(row+BORDER) * DIM_WITH_BORDERS + (col+BORDER)] = p;
    }

    /**
     * Initializes a chess-board with the pieces at their start positions.
     */
    public ChessBoard() {
        board = new byte[DIM_WITH_BORDERS * DIM_WITH_BORDERS];

        // initialize outside-border and empty-center
        for (int i=0; i<DIM_WITH_BORDERS; i++) {
            for (int j=0; j<DIM_WITH_BORDERS; j++) {
                board[i*DIM_WITH_BORDERS + j] =
                        (i<BORDER || i>=DIM+BORDER || j<BORDER || j>=DIM+BORDER) ?
                                OUTSIDE : EMPTY;
            }
        }

        // lines of pawns
        for (int i=0; i<DIM; i++) {
            set(1, i, Piece.Pawn.black());
            set(6, i, Piece.Pawn.white());
        }
        set(0, 0, Piece.Rook.black());
        set(0, 7, Piece.Rook.black());
        set(7, 0, Piece.Rook.white());
        set(7, 7, Piece.Rook.white());
        set(0, 1, Piece.Knight.black());
        set(0, 6, Piece.Knight.black());
        set(7, 1, Piece.Knight.white());
        set(7, 6, Piece.Knight.white());
        set(0, 2, Piece.Bishop.black());
        set(0, 5, Piece.Bishop.black());
        set(7, 2, Piece.Bishop.white());
        set(7, 5, Piece.Bishop.white());
        set(0, 3, Piece.Queen.black());
        set(7, 3, Piece.Queen.white());
        set(0, 4, Piece.King.black());
        set(7, 4, Piece.King.white());
    }

    public ChessBoard(ChessBoard other) {
        board = other.board.clone();
    }

    /**
     * Symbolic pieces. Encapsulates most piece operations.
     */
    public enum Piece {
        Pawn(1, 'p', 1, null, false),
        Knight(2, 'n', 3.5, KNIGHT_JUMPS, false),
        Bishop(3, 'b', 3.5, DIAGONALS, true),
        Rook(4, 'r', 5, ROWS_N_COLS, true),
        Queen(5, 'q', 9, DIAGONALS_ROWS_N_COLS, true),
        King(6, 'k', 100, DIAGONALS_ROWS_N_COLS, false),
        Empty(EMPTY, '.', 0, null, false),
        Outside(OUTSIDE, '?', 0, null, false);

        private final byte id;
        private final char symbol;
        private final double value;
        private int[][] deltas;
        private boolean linear;

        /**
         * Returns icon-names for each piece.
         * @param p (as obtained by ChessBoard.get())
         * @return icon names such as "p_b.png" for a pawn that is black
         */
        public static String iconName(byte p) {
            return "" + valueOf(p).getSymbol(false) + "_"
                    + (ChessBoard.black(p)  ? "b" : "w") + ".png";
        }

        public byte white() { return id; }
        public byte black() { return (byte)(id | COLOR_MASK); }
        public byte forTurn(int turn) {
            return turn == ChessState.WHITE ? white() : black();
        }

        public static Piece valueOf(byte pieceCode) {
            switch (pieceCode) {
                case 1: case 8+1: return Pawn;
                case 2: case 8+2: return Knight;
                case 3: case 8+3: return Bishop;
                case 4: case 8+4: return Rook;
                case 5: case 8+5: return Queen;
                case 6: case 8+6: return King;
                case EMPTY: return Empty;
                default: return Outside;
            }
        }

        public char getSymbol(boolean black) {
            return black ? Character.toUpperCase(symbol) : symbol;
        }

        public static char symbolOf(byte p) {
            return valueOf(p).getSymbol(ChessBoard.black(p));
        }

        public double getValue(boolean negative) {
            return negative ? -1 * value : value;
        }

        public int[][] getDeltas() {
            return deltas;
        }

        public boolean hasLinearMotion() {
            return linear;
        }

        Piece(int id, char symbol, double value, int[][] deltas, boolean linear) {
            this.id = (byte)id;
            this.symbol = symbol;
            this.value = value;
            this.deltas = deltas;
            this.linear = linear;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<DIM; i++) {
            sb.append(" "+i);
        }
        sb.append("\n");
        for (int i=0; i<DIM; i++) {
            for (int j=0; j<DIM; j++) {
                sb.append(' ').append(Piece.symbolOf(get(i, j)));
            }
            sb.append(" - " + i + "\n");
        }
        return sb.toString();
    }
}
