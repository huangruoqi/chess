

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.util.Pair;

import javax.imageio.ImageIO;

public abstract class Piece {
    private final int color;
    private Square currentSquare;
    private BufferedImage img;
    private String imgFile;
    private int score;

    public Piece(int color, Square initSq, String img_file) {
        this.color = color;
        this.currentSquare = initSq;
        this.imgFile = img_file;

        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }

    public Piece copyPiece() {
        if (this.getClass().getName().equals("Bishop")) {
            return (new Bishop(this.color, this.currentSquare, this.imgFile));
        }
        else if (this.getClass().getName().equals("King")) {
            return (new King(this.color, this.currentSquare, this.imgFile));
        }
        else if (this.getClass().getName().equals("Knight")) {
            return (new Knight(this.color, this.currentSquare, this.imgFile));
        }
        else if (this.getClass().getName().equals("Pawn")) {
            return (new Pawn(this.color, this.currentSquare, this.imgFile));
        }
        else if (this.getClass().getName().equals("Queen")) {
            return (new Queen(this.color, this.currentSquare, this.imgFile));
        }
        else if (this.getClass().getName().equals("Rook")) {
            return (new Rook(this.color, this.currentSquare, this.imgFile));
        }
        else  {
            return null;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int s) {
        score = s;
    }

    public String getPositionName() {
        String colorName = (this.color == 0) ? "B" : "W";
        String position = currentSquare.getPositionName();
        if (this.getClass().getName().equals("Bishop")) {
            return (colorName + "B-" + position);
        }
        else if (this.getClass().getName().equals("King")) {
            return (colorName + "K-" + position);
        }
        else if (this.getClass().getName().equals("Knight")) {
            return (colorName + "N-" + position);
        }
        else if (this.getClass().getName().equals("Pawn")) {
            return (colorName + "P-" + position);
        }
        else if (this.getClass().getName().equals("Queen")) {
            return (colorName + "Q-" + position);
        }
        else if (this.getClass().getName().equals("Rook")) {
            return (colorName + "R-" + position);
        }
        else  {
            return "Null Piece";
        }
    }

    public boolean move(Square fin) {
        Piece occup = fin.getOccupyingPiece();
        
        if (occup != null) {
            if (occup.getColor() == this.color) return false;
            else fin.capture(this);
        }
        
        currentSquare.removePiece();
        this.currentSquare = fin;
        currentSquare.put(this);
        return true;
    }
    
    public Square getPosition() {
        return currentSquare;
    }
    
    public void setPosition(Square sq) {
        this.currentSquare = sq;
    }
    
    public int getColor() {
        return color;
    }
    
    public Image getImage() {
        return img;
    }

    public void draw(Graphics g) {
        int x = currentSquare.getX();
        int y = currentSquare.getY();
        
        g.drawImage(this.img, x, y, null);
    }
    
    public List<Square> getLinearOccupations(Square[][] board, int x, int y) {
        ArrayList<Square> primary = new ArrayList<Square>();
        ArrayList<Square> secondary = new ArrayList<Square>();
        
        for (int i = y+1; i < 8; i++) {
            if (board[i][x].isOccupied()) {
                if (board[i][x].getOccupyingPiece().getColor() != this.color) {
                    primary.add(board[i][x]);
                }
                break;
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
                break;
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
                break;
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
                break;
            }
            else {
                secondary.add(board[y][i]);
            }
        }
        primary.addAll(secondary);
        return primary;
    }
    
    public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
        ArrayList<Square> diagOccup = new ArrayList<Square>();
        ArrayList<Square> secondary = new ArrayList<Square>();

        
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
                if (board[yNW][xNW].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[yNW][xNW]);
                    break;
                }
            } else {
                secondary.add(board[yNW][xNW]);
                yNW--;
                xNW--;
            }
        }
        
        while (xSW >= 0 && ySW < 8) {
            if (board[ySW][xSW].isOccupied()) {
                if (board[ySW][xSW].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[ySW][xSW]);
                    break;
                }
            } else {
                secondary.add(board[ySW][xSW]);
                ySW++;
                xSW--;
            }
        }
        
        while (xSE < 8 && ySE < 8) {
            if (board[ySE][xSE].isOccupied()) {
                if (board[ySE][xSE].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[ySE][xSE]);
                    break;
                }
            } else {
                secondary.add(board[ySE][xSE]);
                ySE++;
                xSE++;
            }
        }
        
        while (xNE < 8 && yNE >= 0) {
            if (board[yNE][xNE].isOccupied()) {
                if (board[yNE][xNE].getOccupyingPiece().getColor() == this.color) {
                    break;
                } else {
                    diagOccup.add(board[yNE][xNE]);
                    break;
                }
            } else {
                secondary.add(board[yNE][xNE]);
                yNE--;
                xNE++;
            }
        }
        for (int i = 0 ; i < secondary.size(); i++) {
            diagOccup.add(secondary.get(i));
        }
        
        return diagOccup;
    }
    
    // No implementation, to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board b);

    public abstract List<Pair<Integer, Pair<Piece, Square>>> getMovesWithScore(Board b);
}