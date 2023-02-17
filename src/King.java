import java.util.ArrayList;

import java.util.List;

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
        
        for (int i = 0; i < secondary.size(); i++) {
            primary.add(secondary.get(i))
        }
        return primary;
    }

}
