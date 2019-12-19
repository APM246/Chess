package pieces;

import board.*;

public class Queen extends Piece
{
    private Rook rook;
    private Bishop bishop;

    public Queen(int[] position,boolean isWhite, Board board)
    {
        super(position, isWhite, board, "queen", "png", true);
        rook = new Rook(this.getPosition(), this.getisWhite(), board, false);
        bishop = new Bishop(this.getPosition(), this.getisWhite(), board, false);
    }

    public boolean islegalMove(int x, int y)
    {
        if (rook.islegalMove(x,y) || bishop.islegalMove(x,y)) return true;
        else return false;
    }



}
