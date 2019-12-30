package pieces;

import board.*;

public class Bishop extends Piece
{
    public Bishop(int[] position, boolean isWhite, Board board, boolean isonBoard)
    {
            super(position, isWhite, board, "bishop", "png", isonBoard);
    }

    /**
     * Need to prevent bishop from being able to jump over pieces to reach destination (not allowed).
     */
    private boolean thereoticallyLegal(int x, int y)
    {
        double gradient = Math.abs(((double) y-position[1])/((double) x-position[0]));

        if (position[0] == x) return false; // vertical move not possible
        else if (gradient == 1.0) return true;
        else return false;
    }

    public boolean islegalMove(int x, int y)
    {
        if (!thereoticallyLegal(x,y)) return false;
        int startingy;
        int xmax; int xmin;
        boolean isyIncreasing;

        if (x > position[0])
        {
            xmax = x; xmin = position[0]; startingy = position[1];
            if (y > position[1])
            {
                isyIncreasing = true;
                startingy++;
            }
            else
            {
                isyIncreasing = false;
                startingy--;
            }
        }
        else
        {
            xmax = position[0]; xmin = x; startingy = y;
            if (y > position[1])
            {
                isyIncreasing = false;
                startingy--;

            }
            else
            {
                isyIncreasing = true;
                startingy++;
            }
        }

        while (xmin + 1 < xmax)
        {
            if (boardforPieces.occupiedSquare(xmin + 1, startingy) != null) return false;
            xmin++;
            if (isyIncreasing) startingy++;
            else startingy--;
        }

        return true;
    }

}
