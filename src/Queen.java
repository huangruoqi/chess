

import java.util.ArrayList;
import java.util.List;

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
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
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
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
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
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
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
                if (board[y][i].getOccupyingPiece().getColor() != this.color) {
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
        primary.addAll(secondary());
        
        return primary;
    }
    
}
