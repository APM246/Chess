package pieces;

import board.*;

public class Pawn extends Piece
{
    public Pawn(int[] position, boolean isWhite, Board board)
    {
        super(position, isWhite, board, "pawn", "png", true);
    }

    // can simplify boardforPieces.occupiedSquare(x,y)
    public boolean islegalMove(int x, int y)
    {
        if ((position[1] == 1 || position[1] == 6) && Math.abs(position[1] - y) == 2 && x == position[0]) return true;

        if (position[0] == x + 1 && position[1] == y-1)
        {
            if (boardforPieces.occupiedSquare(x,y) != null && isWhite) return true;
            else return false;
        }
        if (position[0] == x + 1 && position[1] == y+1)
        {
            if (boardforPieces.occupiedSquare(x,y) != null && !isWhite) return true;
            else return false;
        }
        if (position[0] == x - 1 && position[1] == y-1)
        {
            if (boardforPieces.occupiedSquare(x,y) != null && isWhite) return true;
            else return false;
        }
        if (position[0] == x - 1 && position[1] == y+1)
        {
            if (boardforPieces.occupiedSquare(x,y) != null && !isWhite) return true;
            else return false;
        }
        if (position[1] == y-1 && position[0] == x && isWhite && boardforPieces.occupiedSquare(x,y) == null) return true;
        if (position[1] == y+1 && position[0] == x && !isWhite && boardforPieces.occupiedSquare(x,y) == null) return true;

        return false;
    }

    public void promote()
    {

    }

    public int[] getPosition()
    {
        return position;
    }

    public String getName()
    {
        return name;
    }
}