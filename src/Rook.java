

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;


public class Rook extends Piece {

    public Rook(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
        setScore(5);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        return getLinearOccupations(board, x, y);
    }

    @Override
    public List<Pair<Integer, Pair<Piece, Square>>> getMovesWithScore(Board b) {
        ArrayList<Pair<Integer, Pair<Piece, Square>>> moves = new ArrayList<>();
        Square[][] board = b.getSquareArray();
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = y+1; i < 8; i++) {
            if (board[i][x].isOccupied()) {
                Piece piece = board[i][x].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[i][x]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            }
            else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[i][x]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
            }
        }
        for (int i = y-1; i >= 0; i--) {
            if (board[i][x].isOccupied()) {
                Piece piece = board[i][x].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[i][x]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            }
            else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[i][x]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
            }
        }

        for (int i = x + 1; i < 8; i++) {
            if (board[y][i].isOccupied()) {
                Piece piece = board[y][i].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y][i]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            }
            else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y][i]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            if (board[y][i].isOccupied()) {
                Piece piece = board[y][i].getOccupyingPiece();
                if (piece.getColor() != this.getColor()) {
                    Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y][i]);
                    moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                }
                break;
            }
            else {
                Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y][i]);
                moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
            }
        }
        return moves;
    }
}
