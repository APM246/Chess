package pieces;

import board.*;

public class Rook extends Piece
{
    public Rook(int[] position,boolean isWhite, Board board, boolean isonBoard)
    {
        super(position, isWhite, board, "rook", "png", isonBoard);
    }

    public void move(int x, int y)
    {
        if (position[0] == x || position[1] == y)
        {
            position[0] = x;
            position[1] = y;
        }
        else throw new IllegalArgumentException();
    }

    public boolean islegalMove(int x, int y)
    {
        boolean isIncrease = true;
        if (!(position[0] == x || position[1] == y)) return false;
        else
        {
            int x1;
            if (position[1] == y)
            {
                if (x > position[0]) x1 = position[0] + 1;
                else x1 = position[0] - 1; isIncrease = false;
                return runThroughSquares(x1,y,x,y,true, isIncrease);
            }
            else
            {
                int y1;
                if (y > position[1]) y1 = position[1] + 1;
                else y1 = position[1] - 1; isIncrease = false;
                return runThroughSquares(x,y1,x,y,false, isIncrease);
            }
        }
    }
        //checks if any squares are occupied between original and selected square
        public boolean runThroughSquares(int x1, int y1, int x, int y, boolean isX, boolean increase)
        {
            if (isX)
            {
                while (x1 != x)
                {
                    if (boardforPieces.occupiedSquare(x1, y) != null) return false;
                    if (x1 < x) x1++;
                    else x1--;
                }
            }
                else
                    {
                    while (y1 != y)
                        {
                            if (boardforPieces.occupiedSquare(x, y1) != null) return false;
                            if (y1 < y) y1++;
                            else y1--;
                        }
                    }
            return true;
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
