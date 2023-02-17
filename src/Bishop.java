

import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;

public class Bishop extends Piece {

    public Bishop(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
        setScore(3);
    }
    
    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        return getDiagonalOccupations(board, x, y);
    }

    @Override
    public List<Pair<Integer, Pair<Piece, Square>>> getMovesWithScore(Board b) {
        ArrayList<Pair<Integer, Pair<Piece, Square>>> moves = new ArrayList<>();
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();

        int xNW = x - 1;
        int xSW = x - 1;
        int xNE = x + 1;
        int xSE = x + 1;
        int yNW = y - 1;
        int ySW = y + 1;
        int yNE = y - 1;
        int ySE = y + 1;
        
        while (xNW >= 0 && yNW >= 0) {
            if (board[yNW][xNW].isOccupied()) {
                Piece piece = board[yNW][xNW].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[yNW][xNW]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            } else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[yNW][xNW]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                yNW--;
                xNW--;
            }
        }
        
        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                Piece piece = board[ySW][xSW].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[ySW][xSW]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            } else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[ySW][xSW]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                ySW++;
                xSW--;
            }
        }
        
        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                Piece piece = board[ySE][xSE].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[ySE][xSE]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            } else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[ySE][xSE]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                ySE++;
                xSE++;
            }
        }
        
        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                Piece piece = board[yNE][xNE].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[yNE][xNE]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            } else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[yNE][xNE]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                yNE--;
                xNE++;
            }
        }
        
        return moves;
    }
        
}
