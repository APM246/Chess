package pieces;

import board.*;

public class King extends Piece
{
    public King(int[] position, boolean isWhite, Board board)
    {
        super(position, isWhite, board, "king", "png", true);
    }

    public boolean islegalMove(int x, int y)
    {
        if (position[0] == x + 1 && position[1] == y-1) return true;
        if (position[0] == x + 1 && position[1] == y+1) return true;
        if (position[0] == x - 1 && position[1] == y-1) return true;
        if (position[0] == x - 1 && position[1] == y+1) return true;
        if (position[1] == y-1 && position[0] == x) return true;
        if (position[1] == y+1 && position[0] == x) return true;
        if (position[1] == y && position[0] == x-1) return true;
        if (position[1] == y && position[0] == x+1) return true;
        return false;
    }

    public String getName()
    {
        return name;
    }
}
