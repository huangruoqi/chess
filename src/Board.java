

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

    private Piece currPiece;
    private int currX;
    private int currY;
    
    private CheckmateDetector cmd;
    private int count;
    private int score = 0;

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
        Wpieces.add(board[7][3].getOccupyingPiece());
        board[0][3].put(new Queen(0, board[0][3], RESOURCES_BQUEEN_PNG));
        Bpieces.add(board[0][3].getOccupyingPiece());
        
        Bk = new King(0, board[0][4], RESOURCES_BKING_PNG);
        Wk = new King(1, board[7][4], RESOURCES_WKING_PNG);
        board[0][4].put(Bk);
        board[7][4].put(Wk);

        board[0][0].put(new Rook(0, board[0][0], RESOURCES_BROOK_PNG));
        board[0][7].put(new Rook(0, board[0][7], RESOURCES_BROOK_PNG));
        board[7][0].put(new Rook(1, board[7][0], RESOURCES_WROOK_PNG));
        board[7][7].put(new Rook(1, board[7][7], RESOURCES_WROOK_PNG));
        Bpieces.add(board[0][0].getOccupyingPiece());
        Bpieces.add(board[0][7].getOccupyingPiece());
        Wpieces.add(board[7][0].getOccupyingPiece());
        Wpieces.add(board[7][7].getOccupyingPiece());

        board[0][1].put(new Knight(0, board[0][1], RESOURCES_BKNIGHT_PNG));
        board[0][6].put(new Knight(0, board[0][6], RESOURCES_BKNIGHT_PNG));
        board[7][1].put(new Knight(1, board[7][1], RESOURCES_WKNIGHT_PNG));
        board[7][6].put(new Knight(1, board[7][6], RESOURCES_WKNIGHT_PNG));
        Bpieces.add(board[0][1].getOccupyingPiece());
        Bpieces.add(board[0][6].getOccupyingPiece());
        Wpieces.add(board[7][1].getOccupyingPiece());
        Wpieces.add(board[7][6].getOccupyingPiece());

        board[0][2].put(new Bishop(0, board[0][2], RESOURCES_BBISHOP_PNG));
        board[0][5].put(new Bishop(0, board[0][5], RESOURCES_BBISHOP_PNG));
        board[7][2].put(new Bishop(1, board[7][2], RESOURCES_WBISHOP_PNG));
        board[7][5].put(new Bishop(1, board[7][5], RESOURCES_WBISHOP_PNG));
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
        Wpieces.add(Bk);
        cmd = new CheckmateDetector(this /*, Wpieces, Bpieces, wk, bk*/);
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

        newBoard.cmd = new CheckmateDetector(newBoard /*, newBoard.Wpieces, newBoard.Bpieces, wk, bk*/);

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
    	int legalMovesScore = 0;
        int piecesScore = 0;
    	for (Piece p: Bpieces) {
    		legalMovesScore += p.getLegalMoves(this).size();
            piecesScore += p.getScore();
    	}
    	for (Piece p: Wpieces) {
//    		legalMovesScore -= p.getLegalMoves(this).size();
            piecesScore -= p.getScore();
    	}
    	return (int)(piecesScore + legalMovesScore / 10.0);
    }
    
    private Pair<Integer, Pair<Piece, Square>> Minimax(boolean turnSelector, int depthLevel, Piece selected, int alpha, int beta) {
        String strDepth = g.depth.getText();
        int gameTreeDepth = Integer.parseInt(strDepth.substring(17).trim());
        gameTreeDepth = (gameTreeDepth > 0) ? gameTreeDepth : DEPTH_LEVEL;
        
        if (depthLevel > gameTreeDepth) {
            // Return MinMax Value if Depth Limit has reached
            int value = getScore();
            count++;
            return (new Pair<Integer, Pair<Piece, Square>>(value, null));
        }
        int minimaxValue;
        Piece best_piece = null;
        Square best_square = null;
        if (selected != null) {
        	Piece piece = selected;
        	minimaxValue = Integer.MIN_VALUE;
            List<Square> possibleMoves = piece.getLegalMoves(this);
            for (int j = 0; j < possibleMoves.size(); j++) {
                Square square = possibleMoves.get(j);
                if ((square.getOccupyingPiece() == null) ||
                        ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                        ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                        )
                {
                    Square currSq = null;
                    Piece capturedPiece = null;
                    if (square != null) {
                        if (square.isOccupied()) {
                            capturedPiece = square.getOccupyingPiece();
                        }
                        currSq = piece.getPosition();
                    }
                    boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                    if (!success) continue;

                    int valMinMax = 0;
                    Pair<Integer, Pair<Piece, Square>> r = Minimax(!turnSelector, depthLevel+1, null, alpha, beta);
                    valMinMax = r.getKey();

                    // Undo the move
                    piece.move(currSq);

                    if (capturedPiece != null) {
                        if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                            Bpieces.add(capturedPiece);
                        }
                        else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                            Wpieces.add(capturedPiece);
                        }
                        capturedPiece.move(square);
                    }
                    cmd.update();
                    if (valMinMax > minimaxValue) {
                        minimaxValue = valMinMax;
                        best_piece = piece;
                        best_square = square;
                    }
                }

            }
            return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
        }
        if (turnSelector) {
            minimaxValue = Integer.MAX_VALUE;
            for (int i = 0; i < Wpieces.size(); i++) {
                Piece piece = Wpieces.get(i);
                List<Square> possibleMoves = piece.getLegalMoves(this);
                for (int j = 0; j < possibleMoves.size(); j++) {
                    Square square = possibleMoves.get(j);
                    if ((square.getOccupyingPiece() == null) ||
                            ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                            ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                            )
                    {
                        Square currSq = null;
                        Piece capturedPiece = null;
                        if (square != null) {
                            if (square.isOccupied()) {
                                capturedPiece = square.getOccupyingPiece();
                            }
                            currSq = piece.getPosition();
                        }
                        boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                        if (!success) continue;

                        int valMinMax = 0;
                        Pair<Integer, Pair<Piece, Square>> r = Minimax(!turnSelector, depthLevel+1, null, alpha, beta);
                        valMinMax = r.getKey();

                        // Undo the move
                        piece.move(currSq);

                        if (capturedPiece != null) {
                            if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                                Bpieces.add(capturedPiece);
                            }
                            else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                                Wpieces.add(capturedPiece);
                            }
                            capturedPiece.move(square);
                        }
                        cmd.update();
                        if (valMinMax < minimaxValue) {
                            minimaxValue = valMinMax;
                            
                            best_piece = piece;
                            best_square = square;
                        }
                    }
                }
            }

        }
        else {
            minimaxValue = Integer.MIN_VALUE;
            for (int i = 0; i < Bpieces.size(); i++) {
                Piece piece = Bpieces.get(i);
                List<Square> possibleMoves = piece.getLegalMoves(this);
                for (int j = 0; j < possibleMoves.size(); j++) {
                    Square square = possibleMoves.get(j);
                    if ((square.getOccupyingPiece() == null) ||
                            ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                            ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                            )
                    {
                        Square currSq = null;
                        Piece capturedPiece = null;
                        if (square != null) {
                            if (square.isOccupied()) {
                                capturedPiece = square.getOccupyingPiece();
                            }
                            currSq = piece.getPosition();
                        }
                        boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                        if (!success) continue;
                        int valMinMax = 0;
                        Pair<Integer, Pair<Piece, Square>> r = Minimax(!turnSelector, depthLevel+1, null, alpha, beta);
                        valMinMax = r.getKey();

                        // Undo the move
                        piece.move(currSq);
                        if (capturedPiece != null) {
                            if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                                Bpieces.add(capturedPiece);
                            }
                            else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                                Wpieces.add(capturedPiece);
                            }
                            capturedPiece.move(square);
                        }
                        cmd.update();
                        if (valMinMax > minimaxValue) {
                            minimaxValue = valMinMax;
                 
                            best_piece = piece;
                            best_square = square;
                        }
                    }
                }
            }
        }
        return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
    }


    private Pair<Integer, Pair<Piece, Square>> Minimax_pruning(boolean turnSelector, int depthLevel, Piece selected, int alpha, int beta) {
        String strDepth = g.depth.getText();
        int gameTreeDepth = Integer.parseInt(strDepth.substring(17).trim());
        gameTreeDepth = (gameTreeDepth >= 0) ? gameTreeDepth : DEPTH_LEVEL;
        if (depthLevel > gameTreeDepth) {
            // Return MinMax Value if Depth Limit has reached
            count++;
            return (new Pair<Integer, Pair<Piece, Square>>(getScore(), null));
        }
        int minimaxValue;
        Piece best_piece = null;
        Square best_square = null;
        if (selected != null) {
        	Piece piece = selected;
        	minimaxValue = Integer.MIN_VALUE;
            List<Square> possibleMoves = piece.getLegalMoves(this);
            for (int j = 0; j < possibleMoves.size(); j++) {
                Square square = possibleMoves.get(j);
                if ((square.getOccupyingPiece() == null) ||
                        ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                        ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                        )
                {
                    Square currSq = null;
                    Piece capturedPiece = null;
                    if (square != null) {
                        if (square.isOccupied()) {
                            capturedPiece = square.getOccupyingPiece();
                        }
                        currSq = piece.getPosition();
                    }

                    boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                    if (!success) continue;
                    int valMinMax = 0;
                    Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning(!turnSelector, depthLevel+1, null, alpha, beta);
                    valMinMax = r.getKey();

                    // Undo the move
                    piece.move(currSq);
                    if (capturedPiece != null) {
                        if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                            Bpieces.add(capturedPiece);
                        }
                        else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                            Wpieces.add(capturedPiece);
                        }
                        capturedPiece.move(square);
                    }
                    cmd.update();
                    if (valMinMax > minimaxValue) {
                        minimaxValue = valMinMax;
                        alpha = Math.max(alpha, valMinMax);
                        if (beta <= alpha) {
                            return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
                        }
                        best_piece = piece;
                        best_square = square;
                    }
                }

            }
            return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
        }
        if (turnSelector) {
            minimaxValue = Integer.MAX_VALUE;
            for (int i = 0; i < Wpieces.size(); i++) {
                Piece piece = Wpieces.get(i);
                List<Square> possibleMoves = piece.getLegalMoves(this);
//                Collections.shuffle(possibleMoves);
                for (int j = 0; j < possibleMoves.size(); j++) {
                    Square square = possibleMoves.get(j);
                    if ((square.getOccupyingPiece() == null) ||
                            ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                            ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                            )
                    {
                        Square currSq = null;
                        Piece capturedPiece = null;
                        if (square != null) {
                            if (square.isOccupied()) {
                                capturedPiece = square.getOccupyingPiece();
                            }
                            currSq = piece.getPosition();
                        }
                        boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                        if (!success) continue;

                        int valMinMax = 0;
                        Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning(!turnSelector, depthLevel+1, null, alpha, beta);
                        valMinMax = r.getKey();

                        // Undo the move
                        piece.move(currSq);
                        if (capturedPiece != null) {
                            if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                                Bpieces.add(capturedPiece);
                            }
                            else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                                Wpieces.add(capturedPiece);
                            }
                            capturedPiece.move(square);
                        }
                        cmd.update();
                        if (valMinMax < minimaxValue) {
                            minimaxValue = valMinMax;
                            beta = Math.min(beta, valMinMax);
                            if (beta <= alpha) {
                                return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
                            }
                            best_piece = piece;
                            best_square = square;
                        }
                    }
                }
            }

        }
        else {
            minimaxValue = Integer.MIN_VALUE;
            for (int i = 0; i < Bpieces.size(); i++) {
                Piece piece = Bpieces.get(i);
                List<Square> possibleMoves = piece.getLegalMoves(this);
//                Collections.shuffle(possibleMoves);
                for (int j = 0; j < possibleMoves.size(); j++) {
                    Square square = possibleMoves.get(j);
                    if ((square.getOccupyingPiece() == null) ||
                            ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                            ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                            )
                    {
                        Square currSq = null;
                        Piece capturedPiece = null;
                        if (square != null) {
                            if (square.isOccupied()) {
                                capturedPiece = square.getOccupyingPiece();
                            }
                            currSq = piece.getPosition();
                        }
                        boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                        if (!success) continue;
                        int valMinMax = 0;
                        Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning(!turnSelector, depthLevel+1, null, alpha, beta);
                        valMinMax = r.getKey();

                        // Undo the move
                        piece.move(currSq);
                        if (capturedPiece != null) {
                            if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                                Bpieces.add(capturedPiece);
                            }
                            else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                                Wpieces.add(capturedPiece);
                            }
                            capturedPiece.move(square);
                        }
                        cmd.update();
                        if (valMinMax > minimaxValue) {
                            minimaxValue = valMinMax;
                            alpha = Math.max(alpha, valMinMax);
                            if (beta <= alpha) {
                                return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
                            }
                            best_piece = piece;
                            best_square = square;
                        }
                    }
                }
            }
        }
        return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
    }

    private Pair<Integer, Pair<Piece, Square>> Minimax_pruning_sorted(boolean turnSelector, int depthLevel, Piece selected, int alpha, int beta) {
        String strDepth = g.depth.getText();
        int gameTreeDepth = Integer.parseInt(strDepth.substring(17).trim());
        gameTreeDepth = (gameTreeDepth >= 0) ? gameTreeDepth : DEPTH_LEVEL;
        if (depthLevel > gameTreeDepth) {
            // Return MinMax Value if Depth Limit has reached
            count++;
            return (new Pair<Integer, Pair<Piece, Square>>(getScore(), null));
        }
        int minimaxValue;
        Piece best_piece = null;
        Square best_square = null;
        if (selected != null) {
        	Piece piece = selected;
        	minimaxValue = Integer.MIN_VALUE;
            List<Square> possibleMoves = piece.getLegalMoves(this);
            for (int j = 0; j < possibleMoves.size(); j++) {
                Square square = possibleMoves.get(j);
                if ((square.getOccupyingPiece() == null) ||
                        ((turnSelector == false) && (square.getOccupyingPiece().getColor() == 1)) ||
                        ((turnSelector == true) && (square.getOccupyingPiece().getColor() == 0))
                        )
                {
                    Square currSq = null;
                    Piece capturedPiece = null;
                    if (square != null) {
                        if (square.isOccupied()) {
                            capturedPiece = square.getOccupyingPiece();
                        }
                        currSq = piece.getPosition();
                    }

                    boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                    if (!success) continue;
                    int valMinMax = 0;
                    Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning_sorted(!turnSelector, depthLevel+1, null, alpha, beta);
                    valMinMax = r.getKey();

                    // Undo the move
                    piece.move(currSq);
                    if (capturedPiece != null) {
                        if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                            Bpieces.add(capturedPiece);
                        }
                        else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                            Wpieces.add(capturedPiece);
                        }
                        capturedPiece.move(square);
                    }
                    cmd.update();
                    if (valMinMax > minimaxValue) {
                        minimaxValue = valMinMax;
                        alpha = Math.max(alpha, valMinMax);
                        if (beta <= alpha) {
                            return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
                        }
                        best_piece = piece;
                        best_square = square;
                    }
                }

            }
            return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
        }
        List<Pair<Integer, Pair<Piece, Square>>> moves = getAllMoves(turnSelector);
        if (depthLevel==0) {
        	System.out.println(moves.size());
        }

        if (turnSelector) {
            minimaxValue = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                Piece piece = moves.get(i).getValue().getKey();
                Square square = moves.get(i).getValue().getValue();
                Piece capturedPiece = null;
                Square currSq = null;
                if (square.isOccupied()) {
                    capturedPiece = square.getOccupyingPiece();
                }
                currSq = piece.getPosition();
                boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                if (!success) {
                	continue;
                }
                int valMinMax = 0;
                Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning_sorted(!turnSelector, depthLevel+1, null, alpha, beta);
                valMinMax = r.getKey();

                // Undo the move
                piece.move(currSq);
                if (capturedPiece != null) {
                    if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                        Bpieces.add(capturedPiece);
                    }
                    else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                        Wpieces.add(capturedPiece);
                    }
                    capturedPiece.move(square);
                }
                cmd.update();
                if (valMinMax < minimaxValue) {
                    minimaxValue = valMinMax;
                    beta = Math.min(beta, valMinMax);
                    if (beta <= alpha) {
                        return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
                    }
                    best_piece = piece;
                    best_square = square;
                }
            }
        }
        else {
            minimaxValue = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                Piece piece = moves.get(i).getValue().getKey();
                Square square = moves.get(i).getValue().getValue();

                Piece capturedPiece = null;
                Square currSq = null;
                if (square.isOccupied()) {
                    capturedPiece = square.getOccupyingPiece();
                }
                currSq = piece.getPosition();
                boolean success = this.takeTurnEx(piece, square, turnSelector, "", depthLevel);
                if (!success) continue;
                int valMinMax = 0;
                Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning_sorted(!turnSelector, depthLevel+1, null, alpha, beta);
                valMinMax = r.getKey();
                // Undo the move
                piece.move(currSq);
                if (capturedPiece != null) {
                    if ((capturedPiece.getColor() == 0) && (!Bpieces.contains(capturedPiece))) {
                        Bpieces.add(capturedPiece);
                    }
                    else if ((capturedPiece.getColor() == 1) && (!Wpieces.contains(capturedPiece))) {
                        Wpieces.add(capturedPiece);
                    }
                    capturedPiece.move(square);
                }
                cmd.update();
                if (valMinMax > minimaxValue) {
                    minimaxValue = valMinMax;
                    alpha = Math.max(alpha, valMinMax);
                    if (beta <= alpha) {
                        return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
                    }
                    best_piece = piece;
                    best_square = square;
                }
            }
        }
        return (new Pair<Integer, Pair<Piece, Square>>(minimaxValue, new Pair<Piece, Square>(best_piece, best_square)));
    }


    private List<Pair<Integer, Pair<Piece, Square>>> getAllMoves(boolean turnSelector) {
        List<Piece> pieces = turnSelector ? Wpieces : Bpieces;
        List<Pair<Integer, Pair<Piece, Square>>> moves = new ArrayList<>();
        for (int i = 0 ; i < pieces.size(); i++) {
            moves.addAll(pieces.get(i).getMovesWithScore(this));
        }
        moves.sort(Comparator.<Pair<Integer, Pair<Piece, Square>>>comparingInt(Pair::getKey).reversed());
        return moves;
    }













    private boolean EvadeCheck() {

        Stack<String> tempFutureMoves = new Stack<String>();
        // Try to find best square to move the King
        count = 0;
        Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning(false, 0, Bk, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println(count);
        int valMinMax = r.getKey();
        Square sq = r.getValue().getValue();

        if (!takeTurnEx(Bk, sq, false, "", 0)) {
            List<Square> kingsMoves = Bk.getLegalMoves(this);
            Iterator<Square> iterator = kingsMoves.iterator();

            // If best square is not available pick any available square
            while (iterator.hasNext()) {
                sq = iterator.next();
                if (!cmd.testMove(Bk, sq)) continue;
                if (cmd.wMoves.get(sq.hashCode()).isEmpty()) {
                    takeTurnEx(Bk, sq, false, "", 0);
                    return true;
                }
            }
        }
        else {
            return true;
        }
        return false;
    }

    private boolean takeTurnEx(Piece piece, Square sq, boolean turnSelector, String prevPos, int depthLevel) {
        String newText = "";
        boolean success = false;
        
        if (piece != null) {
            if (piece.getColor() == 0 && turnSelector) {
                newText = prevPos + "Black Piece on White's turn\r\n";
                return false;
            }
            else if (piece.getColor() == 1 && !turnSelector) {
                newText = prevPos + "White Piece on Black's turn\r\n";
                return false;
            }
            else {
//                List<Square> legalMoves = piece.getLegalMoves(this);
//                movable = cmd.getAllowableSquares(turnSelector);
//
//                if (legalMoves.contains(sq) && movable.contains(sq)
//                        && cmd.testMove(piece, sq)) {
                    sq.setDisplay(true);
                    piece.move(sq);
                    cmd.update();
                    success = true;

                    if (g.watchMoves.isSelected()) {
                        int valMinMax = getScore();
                        newText = prevPos + piece.getPositionName();
                        newText = newText + " Level: " + Integer.toString(depthLevel);
                        newText = newText + " Val: " + Integer.toString(valMinMax) + "\r\n";
                        g.moves.setText(newText);
                        g.moves.update(g.moves.getGraphics());
                    }

                    if (cmd.blackCheckMated()) {
                        newText = newText + "Black Checkmated\r\n";
                    } else if (cmd.whiteCheckMated()) {
                        newText = newText + "White Checkmated\r\n";
                    }
                    else {
                        if (cmd.blackInCheck()) {
                            newText = newText + "Black in Check\r\n";
                        } else if (cmd.whiteInCheck()) {
                            newText = newText + "White in Check\r\n";
                        }
                    }
//                }
            }
        }

        if (g.watchMoves.isSelected()) {
            this.update(this.getGraphics());
        }

        return success;
    }

    private void takeTurn(Square sq) {
        String newText = "";
        if (currPiece != null) {
            if (currPiece.getColor() == 0 && whiteTurn) {
                newText = "Black Piece on White's turn\r\n";
            }
            else if (currPiece.getColor() == 1 && !whiteTurn) {
                newText = "White Piece on Black's turn\r\n";
            }
            else {
                List<Square> legalMoves = currPiece.getLegalMoves(this);
                movable = cmd.getAllowableSquares(whiteTurn);
//                
// 				Assume player move is legal
//                if (legalMoves.contains(sq) && movable.contains(sq)
//                        && cmd.testMove(currPiece, sq)) {
                    sq.setDisplay(true);
                    
                    currPiece.move(sq);
                    cmd.update();

                    newText = currPiece.getPositionName() + "\r\n";
                    boolean blackCheckEvaded = false;
                    if (cmd.blackCheckMated()) {
                        currPiece = null;
                        repaint();
                        this.removeMouseListener(this);
                        this.removeMouseMotionListener(this);
                        g.checkmateOccurred(0);
                        newText = newText + "Black Checkmated\r\n";
                    } else if (cmd.whiteCheckMated()) {
                        currPiece = null;
                        repaint();
                        this.removeMouseListener(this);
                        this.removeMouseMotionListener(this);
                        g.checkmateOccurred(1);
                        newText = newText + "White Checkmated\r\n";
                    }
                    else {
                        boolean bInCheck = cmd.blackInCheck();
                        if (bInCheck) {
                            newText = newText + "Black in Check\r\n";

                            g.gameStatus.setText("Status: Computing");
                            g.buttons.update(g.buttons.getGraphics());

                            long startTime = System.nanoTime();
                            count = 0;
                            if (EvadeCheck()) {
                                System.out.println(""+(System.nanoTime() - startTime)/1000000000 + " seconds");
                                System.out.println(count + " evaluations");
                                currPiece = Bk;
                                whiteTurn = !whiteTurn;
                                newText = newText + "Check evaded\r\n";
                                blackCheckEvaded = true;
                                g.gameStatus.setText("Status: Move to " + currPiece.getPositionName());
                                g.buttons.update(g.buttons.getGraphics());
                            }
                        } else if (cmd.whiteInCheck()) {
                            newText = newText + "White in Check\r\n";
                        }
                        whiteTurn = !whiteTurn;
                        if (!blackCheckEvaded) {
	                        currPiece = null;
	                        if (!whiteTurn) {

	                            // Let Computer pick the next turn
	                            g.gameStatus.setText("Status: Computing");
	                            g.buttons.update(g.buttons.getGraphics());
	
	                            Stack<String> futureMoves = new Stack<String>();
	                            count = 0;
	                            long startTime = System.nanoTime();
	                            Pair<Integer, Pair<Piece, Square>> r = Minimax_pruning_sorted(false, 0, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
	                            System.out.println(""+(System.nanoTime() - startTime)/1000000000 + " seconds");
	                            System.out.println(count + " evaluations");
	                            Pair<Piece, Square> m = r.getValue();
	                            currPiece = m.getKey();
	                            Square s = m.getValue();
	                            int x = currPiece.getPosition().getXNum();
	                            int y = currPiece.getPosition().getYNum();
	                            int xq = s.getXNum();
	                            int yq = s.getYNum();
	                            System.out.println(x + " " + y + "->" + xq + " " + yq);
	                            boolean success = takeTurnEx(m.getKey(), m.getValue(), whiteTurn, newText, 0);
	                            whiteTurn = true; // Change the turn back to White
	
	                            //newText = g.moves.getText();
	//                            newText += "Anticipated Moves:\r\n";
	//                            String futureMove = futureMoves.isEmpty() ? "" : futureMoves.pop();
	//                            while (!futureMoves.isEmpty()) {
	//                                newText += futureMove + "\r\n";
	//                                futureMove = futureMoves.pop();
	//                            }
	                            g.gameStatus.setText("Status: Move to " + currPiece.getPositionName());
	                            g.buttons.update(g.buttons.getGraphics());
	                        }
	                        
                        }
                    }
//                } else {
                    currPiece.getPosition().setDisplay(true);
//                    currPiece = null;
//                    newText = newText + "Invalid Move\r\n";
//                }
            }
        }
        else {
            newText = "Null Piece\r\n";
        }

        g.moves.setText(newText);

        repaint();
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