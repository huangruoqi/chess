

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.util.List;
import javafx.util.Pair;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
    private static final String RESOURCES_WBISHOP_PNG = "resources/wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "resources/bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "resources/wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "resources/bknight.png";
	private static final String RESOURCES_WROOK_PNG = "resources/wrook.png";
	private static final String RESOURCES_BROOK_PNG = "resources/brook.png";
	private static final String RESOURCES_WKING_PNG = "resources/wking.png";
	private static final String RESOURCES_BKING_PNG = "resources/bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "resources/bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "resources/wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "resources/wpawn.png";
	private static final String RESOURCES_BPAWN_PNG = "resources/bpawn.png";

    private static final int DEPTH_LEVEL = 3;

	// Logical and graphical representations of board
	private Square[][] board;
    private GameWindow g;
    
    // List of pieces and whether they are movable
    public LinkedList<Piece> Bpieces;
    public LinkedList<Piece> Wpieces;
    public King Wk;
    public King Bk;

    public List<Square> movable;

    private boolean whiteTurn;
    private boolean running;

    private Piece currPiece;
    private int currX;
    private int currY;
    
    private int count;

    public Board(GameWindow g) {
        initializeBoard(g);
    }

    public Board(GameWindow g, boolean init) {
        if (init) {
            initializeBoard(g);
        }
        else {
            this.g = g;
            board = new Square[8][8];
            whiteTurn = true;
            running = false;
        }
    }

    public void initializeBoard(GameWindow g) {
        this.g = g;
        board = new Square[8][8];
        Bpieces = new LinkedList<Piece>();
        Wpieces = new LinkedList<Piece>();
        setLayout(new GridLayout(8, 8, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xMod = x % 2;
                int yMod = y % 2;

                if ((xMod == 0 && yMod == 0) || (xMod == 1 && yMod == 1)) {
                    board[x][y] = new Square(this, 1, y, x);
                    this.add(board[x][y]);
                } else {
                    board[x][y] = new Square(this, 0, y, x);
                    this.add(board[x][y]);
                }
            }
        }

        initializePieces();

        this.setPreferredSize(new Dimension(400, 400));
        this.setMaximumSize(new Dimension(400, 400));
        this.setMinimumSize(this.getPreferredSize());
        this.setSize(new Dimension(400, 400));

        whiteTurn = true;

    }

    private void initializePieces() {
    	
        
        board[7][3].put(new Queen(1, board[7][3], RESOURCES_WQUEEN_PNG));
        board[0][3].put(new Queen(0, board[0][3], RESOURCES_BQUEEN_PNG));

        Bk = new King(0, board[0][4], RESOURCES_BKING_PNG);
        Wk = new King(1, board[7][4], RESOURCES_WKING_PNG);
        board[0][4].put(Bk);
        board[7][4].put(Wk);

        board[0][0].put(new Rook(0, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(0, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(1, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(1, board[7][7], RESOURCES_WROOK_PNG));
        

        board[0][1].put(new Knight(0, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(0, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(1, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(1, board[7][6], RESOURCES_WKNIGHT_PNG));
        

        board[0][2].put(new Bishop(0, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(0, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(1, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(1, board[7][5], RESOURCES_WBISHOP_PNG));
        
        Wpieces.add(board[7][3].getOccupyingPiece());
        Bpieces.add(board[0][3].getOccupyingPiece());
        Bpieces.add(board[0][0].getOccupyingPiece());
        Bpieces.add(board[0][7].getOccupyingPiece());
        Wpieces.add(board[7][0].getOccupyingPiece());
        Wpieces.add(board[7][7].getOccupyingPiece());
        Bpieces.add(board[0][1].getOccupyingPiece());
        Bpieces.add(board[0][6].getOccupyingPiece());
        Wpieces.add(board[7][1].getOccupyingPiece());
        Wpieces.add(board[7][6].getOccupyingPiece());
        Bpieces.add(board[0][2].getOccupyingPiece());
        Bpieces.add(board[0][5].getOccupyingPiece());
        Wpieces.add(board[7][2].getOccupyingPiece());
        Wpieces.add(board[7][5].getOccupyingPiece());
        for (int x = 0; x < 8; x++) {
            board[1][x].put(new Pawn(0, board[1][x], RESOURCES_BPAWN_PNG));
            Bpieces.add(board[1][x].getOccupyingPiece());
            board[6][x].put(new Pawn(1, board[6][x], RESOURCES_WPAWN_PNG));
            Wpieces.add(board[6][x].getOccupyingPiece());
        }

        Bpieces.add(Bk);
        Wpieces.add(Wk);
    }

    public Square[][] getSquareArray() {
        return this.board;
    }

    public boolean getTurn() {
        return whiteTurn;
    }

    public void setCurrPiece(Piece p) {
        this.currPiece = p;
    }

    public Piece getCurrPiece() {
        return this.currPiece;
    }

    @Override
    public void paintComponent(Graphics g) {
        // super.paintComponent(g);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Square sq = board[y][x];
                if (sq != null) {
                    sq.paintComponent(g);
                }
            }
        }

        if (currPiece != null) {
            if ((currPiece.getColor() == 1 && whiteTurn)
                    || (currPiece.getColor() == 0 && !whiteTurn)) {
                final Image i = currPiece.getImage();
                if (i != null) {
                    g.drawImage(i, currX, currY, null);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (sq.isOccupied()) {
            currPiece = sq.getOccupyingPiece();
            if (currPiece.getColor() == 0 && whiteTurn)
                return;
            if (currPiece.getColor() == 1 && !whiteTurn)
                return;
            sq.setDisplay(false);
        }
        repaint();
    }

    private Board copyBoard() {
        Board newBoard = new Board(g, false);

        int i = 0;
        int j = 0;

        // Reposition the pieces based on Board being copied
        King wk = null;
        King bk = null;
        newBoard.Bpieces = new LinkedList<Piece>();
        newBoard.Wpieces = new LinkedList<Piece>();
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                newBoard.board[i][j] = new Square(newBoard, 0, j, i);
                Piece piece = this.board[i][j].getOccupyingPiece();
                if (piece != null) {
                    Piece newPiece = piece.copyPiece();
                    newBoard.board[i][j].put(newPiece);
                    if (piece.getColor() == 0) {
                        newBoard.Bpieces.add(newPiece);
                        if (newPiece.getClass().getName().equals("King")) {
                            bk = (King) newPiece;
                        }
                    }
                    else {
                        newBoard.Wpieces.add(newPiece);
                        if (newPiece.getClass().getName().equals("King")) {
                            wk = (King) newPiece;
                        }
                    }
                    if (piece.equals(currPiece)) {
                        newBoard.currPiece = newPiece;
                    }
                }
            }
        }

        newBoard.currX = this.currX;
        newBoard.currY = this.currY;
        newBoard.whiteTurn = this.whiteTurn;


        return newBoard;
    }

    private Square FindSquare(Square sq) {
        return this.board[sq.getYNum()][sq.getXNum()];
    }

    private Piece FindPiece(Piece chessPiece) {

        int i = 0;
        int j = 0;

        for (i = 0; i < this.board.length; i++) {
            for (j = 0; j < this.board[i].length; j++) {
                Piece piece = this.board[i][j].getOccupyingPiece();
                if (piece != null) {
                    if (piece.getColor() == chessPiece.getColor()) {
                        if (piece.getClass().getName().equals(chessPiece.getClass().getName())) {
                            if ((piece.getPosition().getXNum() == chessPiece.getPosition().getXNum()) &&
                                    (piece.getPosition().getYNum() == chessPiece.getPosition().getYNum())) {
                                return piece;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
    
    private int getScore() {
        int piecesScore = 0;
    	for (Piece p: Bpieces) {
            piecesScore += p.getScore();
    	}
    	for (Piece p: Wpieces) {
            piecesScore -= p.getScore();
    	}

        List<Pair<Integer, Pair<Piece, Square>>> whiteMoves = getAllMoves(true);
        List<Pair<Integer, Pair<Piece, Square>>> blackMoves = getAllMoves(false);
        int threatScore = 0;
        int moveScore = (whiteMoves.size() + blackMoves.size());
        for (int i = 0; i < whiteMoves.size(); i++) {
            threatScore -= whiteMoves.get(i).getKey();
        }
        for (int i = 0; i < blackMoves.size(); i++) {
            threatScore += blackMoves.get(i).getKey();
        }

    	return piecesScore + threatScore/2 + moveScore/10;
    }
    
    
    private Pair<Integer, Pair<Piece, Square>> Minimax_pruning_sorted(boolean turnSelector, int depthLevel, Piece selected, int alpha, int beta, int gameTreeDepth) {
        List<Piece> opponentPieces = turnSelector ? Bpieces : Wpieces;
        count++;
        if (depthLevel >= gameTreeDepth) {
            // Return MinMax Value if Depth Limit has reached
        	int score = getScore();        	

            return (new Pair<Integer, Pair<Piece, Square>>(score, null));
        }
        Piece best_piece = null;
        Square best_square = null;
        List<Pair<Integer, Pair<Piece, Square>>> moves = getAllMoves(turnSelector, turnSelector);
        int minimaxValue = turnSelector ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        for (int i = 0; i < moves.size(); i++) {
            Piece piece = moves.get(i).getValue().getKey();
            Square square = moves.get(i).getValue().getValue();
            Piece capturedPiece = square.getOccupyingPiece();
            Square currSq = piece.getPosition();
            if (capturedPiece != null) {
                opponentPieces.remove(capturedPiece);
            }
        	square.setOccupyingPiece(piece);
            piece.setPosition(square);
            currSq.setOccupyingPiece(null);

            Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning_sorted(!turnSelector, depthLevel+1, null, alpha, beta, gameTreeDepth);

            int valMinMax = r.getKey();

            // Undo the move
            if (capturedPiece != null) {
                opponentPieces.add(capturedPiece);
            }
            square.setOccupyingPiece(capturedPiece);
            piece.setPosition(currSq);
            currSq.setOccupyingPiece(piece);

            if (turnSelector && valMinMax < minimaxValue) {
                minimaxValue = valMinMax;
                beta = Math.min(beta, valMinMax);
                if (beta <= alpha) {
                	return (new Pair<Integer, Pair<Piece, Square>>(Integer.MIN_VALUE, new Pair<Piece, Square>(best_piece, best_square)));
                }
                best_piece = piece;
                best_square = square;
            }
            if (!turnSelector && valMinMax > minimaxValue) {
                minimaxValue = valMinMax;
                alpha = Math.max(alpha, valMinMax);
                if (beta <= alpha) {
                	return (new Pair<Integer, Pair<Piece, Square>>(Integer.MAX_VALUE, new Pair<Piece, Square>(best_piece, best_square)));
                }
                best_piece = piece;
                best_square = square;
            }
        }
        return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
    }


    private List<Pair<Integer, Pair<Piece, Square>>> getAllMoves(boolean turnSelector, boolean increasing) {
        List<Piece> pieces = turnSelector ? Wpieces : Bpieces;
        List<Pair<Integer, Pair<Piece, Square>>> moves = new ArrayList<>();
        for (int i = 0 ; i < pieces.size(); i++) {
            moves.addAll(pieces.get(i).getMovesWithScore(this));
        }
        if (increasing) {
            moves.sort(Comparator.<Pair<Integer, Pair<Piece, Square>>>comparingInt(Pair::getKey));
        } else {
            moves.sort(Comparator.<Pair<Integer, Pair<Piece, Square>>>comparingInt(Pair::getKey).reversed());
        }
        return moves;
    }

    private List<Pair<Integer, Pair<Piece, Square>>> getAllMoves(boolean turnSelector) {
        List<Piece> pieces = turnSelector ? Wpieces : Bpieces;
        List<Pair<Integer, Pair<Piece, Square>>> moves = new ArrayList<>();
        for (int i = 0 ; i < pieces.size(); i++) {
            moves.addAll(pieces.get(i).getMovesWithScore(this));
        }
        return moves;
    }

    private boolean isCheckmate(boolean turnSelector) {
        List<Pair<Integer, Pair<Piece, Square>>> moves = getAllMoves(turnSelector);
        for (int i = 0; i < moves.size(); i++) {
            Piece piece = moves.get(i).getValue().getKey();
            Square square = moves.get(i).getValue().getValue();
            if (isLegal(piece, square)) {
                return false;
            }
        }
        return true;
    }

    private boolean isLegal(Piece piece, Square square) {
        List<Piece> opponentPieces = piece.getColor()==0 ? Wpieces : Bpieces;
        // move
        Piece capturedPiece = square.getOccupyingPiece();
        Square currSq = piece.getPosition();
        if (capturedPiece != null) {
            opponentPieces.remove(capturedPiece);
        }
        square.setOccupyingPiece(piece);
        piece.setPosition(square);
        currSq.setOccupyingPiece(null);

        boolean result = isCheck(piece.getColor()!=0);

        // Undo the move
        if (capturedPiece != null) {
            opponentPieces.add(capturedPiece);
        }
        square.setOccupyingPiece(capturedPiece);
        piece.setPosition(currSq);
        currSq.setOccupyingPiece(piece);

        return !result;
    }

    private boolean isCheck(boolean turnSelector) {
        List<Pair<Integer, Pair<Piece, Square>>> moves = getAllMoves(!turnSelector, false);
        return moves.get(0).getKey()==200;
    }

    private void takeTurn(Square sq) {
    	if (running) {
            g.moves.setText("Turn ongoing\r\n");
            repaint();
    		return;
    	}
    	running = true;
        if (currPiece == null) {
            g.moves.setText("Null Piece\r\n");
            repaint();
            running = false;
            return;
        }
        
        if (currPiece.getColor() == 0 && whiteTurn) {
            g.moves.setText("Black Piece on White's turn\r\n");
            repaint();
            running = false;
            return;
        }
        if (currPiece.getColor() == 1 && !whiteTurn) {
            g.moves.setText("White Piece on Black's turn\r\n");
            repaint();
            running = false;
            return;
        }

        String newText = "";
        List<Pair<Integer, Pair<Piece, Square>>> moves = getAllMoves(whiteTurn, false);
        boolean hasMove = false;
        for (int i = 0; i < moves.size(); i++) {
            Piece piece = moves.get(i).getValue().getKey();
            Square square = moves.get(i).getValue().getValue();
            if (piece==currPiece && square==sq) {
                hasMove = true;
            }
        }
        if (hasMove && isLegal(currPiece, sq)) {
            sq.setDisplay(true);
            
            // move
            Piece capturedPiece = sq.getOccupyingPiece();
            List<Piece> opponentPieces = Bpieces;
            Square currSq = currPiece.getPosition();
            if (capturedPiece != null) {
                opponentPieces.remove(capturedPiece);
            }
            sq.setOccupyingPiece(currPiece);
            currPiece.setPosition(sq);
            currSq.setOccupyingPiece(null);
            
            newText = currPiece.getPositionName() + "\r\n";
            if (isCheckmate(false)) {
                currPiece = null;
                repaint();
                this.removeMouseListener(this);
                this.removeMouseMotionListener(this);
                g.checkmateOccurred(0);
                newText = "Black Checkmated\r\n";
            }
            else {
                boolean blackInCheck = isCheck(false);
                if (blackInCheck) {
                    g.moves.setText("Black in Check\r\n");
                } 
                whiteTurn = !whiteTurn;
                currPiece = null;
                if (!whiteTurn) {

                    // Let Computer pick the next turn
                    g.gameStatus.setText("Status: Computing");
                    g.buttons.update(g.buttons.getGraphics());

                    
                    String strDepth = g.depth.getText();
                    int gameTreeDepth = Integer.parseInt(strDepth.substring(17).trim());
                    gameTreeDepth = (gameTreeDepth >= 0) ? gameTreeDepth : DEPTH_LEVEL;
                    
                    count = 0;
                    long startTime = System.nanoTime();
                    Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning_sorted(false, 0, null, Integer.MIN_VALUE, Integer.MAX_VALUE, gameTreeDepth);
                    System.out.println(""+(System.nanoTime() - startTime)/1000000000 + " seconds");
                    System.out.println("Score: " + r.getKey());
                    System.out.println(count);

                    
                    Pair<Piece, Square> m = r.getValue();
                    currPiece = m.getKey();
                    Square square = m.getValue();
                    Piece piece = m.getKey();
                    capturedPiece = square.getOccupyingPiece();
                    opponentPieces = whiteTurn ? Bpieces : Wpieces;
                    currSq = piece.getPosition();
                    if (capturedPiece != null) {
                        opponentPieces.remove(capturedPiece);
                    }
                    square.setOccupyingPiece(piece);
                    currPiece.setPosition(square);
                    currSq.setOccupyingPiece(null);
                    
                    whiteTurn = true; // Change the turn back to White
                    g.gameStatus.setText("Status: Move to " + currPiece.getPositionName());
                    g.buttons.update(g.buttons.getGraphics());
                    square.setDisplay(true);
                    repaint();

                    if (blackInCheck) {
                        if (isCheck(false)) { 
                            newText = "Check not evaded, minimax failed!!!";
                            System.err.println(newText);
                        } else {
                            newText = "Check evaded.";
                        }
                    }
                }
                if (isCheckmate(true)) {
                    currPiece = null;
                    this.removeMouseListener(this);
                    this.removeMouseMotionListener(this);
                    g.checkmateOccurred(1);
                    newText = newText + "White Checkmated\r\n";
                }
                else if (isCheck(true)) {
                    newText = newText + "White in Check\r\n";
                }
            }
        } else {
            currPiece.getPosition().setDisplay(true);
            currPiece = null;
            newText = "Invalid Move\r\n";
        }
        g.moves.setText(newText);
        repaint();
        g.buttons.update(g.buttons.getGraphics());

        running = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

        takeTurn(sq);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currX = e.getX() - 24;
        currY = e.getY() - 24;

        repaint();
    }

    // Irrelevant methods, do nothing for these mouse behaviors
    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}