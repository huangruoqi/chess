import java.util.ArrayList;

import java.util.List;
import javafx.util.Pair;

public class King extends Piece {

    public King(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
        setScore(200);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        ArrayList<Square> primary = new ArrayList<Square>();
        ArrayList<Square> secondary = new ArrayList<Square>();
        
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[y + k][x + i].isOccupied()){
                            secondary.add(board[y+k][x+i]);
                        } 
                        else if (board[y + k][x + i].getOccupyingPiece().getColor() != this.getColor()) {
                            primary.add(board[y + k][x + i]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
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
        
        for (int i = 1; i > -2; i--) {
            for (int k = 1; k > -2; k--) {
                if(!(i == 0 && k == 0)) {
                    try {
                        if(!board[y + k][x + i].isOccupied()){
                            Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+k][x+i]);
                            moves.add(new Pair<Integer, Pair<Piece, Square>>(0, pair));
                        } 
                        else if (board[y + k][x + i].getOccupyingPiece().getColor() != this.getColor()) {
                            Piece piece = board[y+k][x+i].getOccupyingPiece();
                            Pair<Piece, Square> pair = new Pair<Piece, Square>(this, board[y+k][x+i]);
                            moves.add(new Pair<Integer, Pair<Piece, Square>>(piece.getScore(), pair));
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }
        return moves;
    }
}
