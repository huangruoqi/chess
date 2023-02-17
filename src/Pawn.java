

import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;


public class Pawn extends Piece {
    private boolean wasMoved;
    
    public Pawn(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
        setScore(1);
    }

    public void setMoved(boolean moved) {
        this.wasMoved = moved;
    }

    @Override
    public boolean move(Square fin) {
        boolean b = super.move(fin);
        wasMoved = true;
        return b;
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        ArrayList<Square> primary = new ArrayList<Square>();
        ArrayList<Square> secondary = new ArrayList<Square>();
        
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int c = this.getColor();
        
        if (c == 0) {
            if (!wasMoved) {
                if (((y+2) < 8) && (!board[y+2][x].isOccupied())) {
                    secondary.add(board[y+2][x]);
                }
            }
            
            if (y+1 < 8) {
                if (!board[y+1][x].isOccupied()) {
                    secondary.add(board[y+1][x]);
                }
            }
            
            if (x+1 < 8 && y+1 < 8) {
                if (board[y+1][x+1].isOccupied()&&board[y+1][x+1].getOccupyingPiece().getColor()!=this.getColor()) {
                	primary.add(board[y+1][x+1]);
                }
            }
                
            if (x-1 >= 0 && y+1 < 8) {
                if (board[y+1][x-1].isOccupied()&&board[y+1][x-1].getOccupyingPiece().getColor()!=this.getColor()) {
                	primary.add(board[y+1][x-1]);
                }
            }
        }
        
        if (c == 1) {
            if (!wasMoved) {
                if (((y-2) >= 0) && (!board[y-2][x].isOccupied())) {
                    secondary.add(board[y-2][x]);
                }
            }
            
            if (y-1 >= 0) {
                if (!board[y-1][x].isOccupied()) {
                    secondary.add(board[y-1][x]);
                }
            }
            
            if (x+1 < 8 && y-1 >= 0) {
                if (board[y-1][x+1].isOccupied()&&board[y-1][x+1].getOccupyingPiece().getColor()!=this.getColor()) {
                	primary.add(board[y-1][x+1]);
                }
            }
                
            if (x-1 >= 0 && y-1 >= 0) {
                if (board[y-1][x-1].isOccupied()&&board[y-1][x-1].getOccupyingPiece().getColor()!=this.getColor()) {
                	primary.add(board[y-1][x-1]);
                }
            }
        }
        primary.addAll(secondary);

        
        return primary;
    }

    @Override
    public List<Pair<Integer, Pair<Piece, Square>>> getMovesWithScore(Board b) {
        ArrayList<Pair<Integer, Pair<Piece, Square>>> moves = new ArrayList<>();
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        int c = this.getColor();
        if (c == 0) {
            if (!wasMoved) {
                if (((y+2) < 8) && (!board[y+2][x].isOccupied())) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+2][x]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                }
            }
            
            if (y+1 < 8) {
                if (!board[y+1][x].isOccupied()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+1][x]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                }
            }
            
            if (x+1 < 8 && y+1 < 8) {
                if (board[y+1][x+1].isOccupied()) {
                    Piece piece = board[y+1][x+1].getOccupyingPiece();
                    if (piece.getColor()!=this.getColor()) {
                    	Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+1][x+1]);
                        moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                    }
                }
            }
                
            if (x-1 >= 0 && y+1 < 8) {
                if (board[y+1][x-1].isOccupied()) {
                    Piece piece = board[y+1][x-1].getOccupyingPiece();
                    if (piece.getColor()!=this.getColor()) {
                    	Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+1][x+1]);
                        moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                    }
                }
            }
        }
        
        if (c == 1) {
            if (!wasMoved) {
                if (((y-2) >= 0) && (!board[y-2][x].isOccupied())) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y-2][x]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                }
            }
            
            if (y-1 >= 0) {
                if (!board[y-1][x].isOccupied()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y-1][x]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                }
            }
            
            if (x+1 < 8 && y-1 >= 0) {
                if (board[y-1][x+1].isOccupied()) {
                    Piece piece = board[y-1][x+1].getOccupyingPiece();
                    if (piece.getColor()!=this.getColor()) {
                    	Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+1][x+1]);
                        moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                    }
                }
            }
                
            if (x-1 >= 0 && y-1 >= 0) {
                if (board[y-1][x-1].isOccupied()) {
                    Piece piece = board[y-1][x-1].getOccupyingPiece();
                    if (piece.getColor()!=this.getColor()) {
                    	Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+1][x+1]);
                        moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                    }
                }
            }
        }
        return moves;
    }

}
