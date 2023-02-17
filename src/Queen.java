

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

public class Queen extends Piece {

    public Queen(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
        setScore(9);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        ArrayList<Square> primary = new ArrayList<Square>();
        ArrayList<Square> secondary = new ArrayList<Square>();
        
        for (int i = y+1; i < 8; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.getColor()) {
                    primary.add(board[i][x]);
                }
                else {
                    break;
                }
            }
            else {
                secondary.add(board[i][x]);
            }
        }
        for (int i = y-1; i >= 0; i--) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.getColor()) {
                    primary.add(board[i][x]);
                }
                else {
                    break;
                }
            }
            else {
                secondary.add(board[i][x]);
            }
        }

        for (int i = x + 1; i < 8; i++) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.getColor()) {
                    primary.add(board[y][i]);
                }
                else {
                    break;
                }
            }
            else {
                secondary.add(board[y][i]);
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            if (board[y][i].isOccupied()) {
                if (board[y][i].getOccupyingPiece().getColor() != this.getColor()) {
                    primary.add(board[y][i]);
                }
                else {
                    break;
                }
            }
            else {
                secondary.add(board[y][i]);
            }
        }
        primary.addAll(getDiagonalOccupations(board, x, y));
        primary.addAll(secondary);
        
        return primary;
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
