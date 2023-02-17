

import java.util.ArrayList;

import java.util.List;

public class Knight extends Piece {

    public Knight(int color, Square initSq, String img_file) {
        super(color, initSq, img_file);
        setScore(3);
    }

    @Override
    public List<Square> getLegalMoves(Board b) {
        ArrayList<Square> primary = new ArrayList<Square>();
        ArrayList<Square> secondary = new ArrayList<Square>();
        Square[][] board = b.getSquareArray();
        
        int x = this.getPosition().getXNum();
        int y = this.getPosition().getYNum();
        
        for (int i = 2; i > -3; i--) {
            for (int k = 2; k > -3; k--) {
                if(Math.abs(i) == 2 ^ Math.abs(k) == 2) {
                    if (k != 0 && i != 0) {
                        try {
                            if (board[y+k][x+i].isOccupied()) {
                                if (board[y+k][x+i].getOccupyingPiece().getColor()!=this.getColor()){
                                    primary.add(board[y + k][x + i]);
                                }
                            }
                            else {
                                secondary.add(board[y+k][x+i]);
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            continue;
                        }
                    }
                }
            }
        }
        primary.addAll(secondary);
        return primary;
    }

}
