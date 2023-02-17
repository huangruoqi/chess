

import java.util.ArrayList;
import java.util.List;

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

}
