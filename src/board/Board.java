package board;

/**
 * Keeps track of all pieces and how they interact with each other.
 *
 * Check: check if position that king is in is a legal move for all pieces surrounding it,
 * and for the 7 positions surrounding it
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import pieces.*;

public class Board
{
    private ArrayList<Piece> pieces;
    private Piece currentPiece; //piece that has been selected

    /**
     * FIX THE UNNECESSARY 2ND FOR LOOP
     */
    public Board()
    {
        pieces = new ArrayList<Piece>();
        //sets up pawns
        for (int i = 0; i < 8; i++)
        {
            for (int j = 1; j < 2; j++)
            {
                pieces.add(new Pawn(new int[]{i, j}, true, this));
                pieces.add(new Pawn(new int[]{i, 7 - j}, false, this));
            }
        }

        pieces.add(new Rook(new int[] {0,0}, true, this, true));
        pieces.add(new Rook(new int[] {7,0}, true, this, true));
        pieces.add(new Rook(new int[] {0,7}, false, this, true));
        pieces.add(new Rook(new int[] {7,7}, false, this, true));
        pieces.add(new Knight(new int[] {1,0}, true, this));
        pieces.add(new Knight(new int[] {6,0}, true, this));
        pieces.add(new Knight(new int[] {1,7}, false, this));
        pieces.add(new Knight(new int[] {6,7}, false, this));
        pieces.add(new Bishop(new int[] {2,0}, true, this, true));
        pieces.add(new Bishop(new int[] {5,0}, true, this, true));
        pieces.add(new Bishop(new int[] {2,7}, false, this, true));
        pieces.add(new Bishop(new int[] {5,7}, false, this, true));
        pieces.add(new King(new int[] {4,7}, false, this));
        pieces.add(new King(new int[] {4,0}, true, this));
        pieces.add(new Queen(new int[] {3,0}, true, this));
        pieces.add(new Queen(new int[] {3,7}, false, this));

    }

    public Board(String test)
    {
        pieces = new ArrayList<Piece>();

        pieces.add(new Rook(new int[] {0,0}, true, this, true));
        pieces.add(new Rook(new int[] {7,0}, true, this, true));
        pieces.add(new Rook(new int[] {2,0}, false, this, true));
        pieces.add(new Rook(new int[] {7,7}, false, this, true));
        pieces.add(new King(new int[] {4,7}, false, this));
        pieces.add(new King(new int[] {4,0}, true, this));
    }

    public ArrayList<Piece> getPieces()
    {
        return pieces;
    }

    public void setCurrentPiece(Piece currentPiece)
    {
        this.currentPiece = currentPiece;
    }

    public Piece getCurrentPiece()
    {
        return currentPiece;
    }

    //never actually used
    public void move(int x, int y)
    {
        if (x <= 7 && y <= 7)
        {
            if (currentPiece.islegalMove(x, y)) currentPiece.setPosition(x, y);
        }
        else throw new IllegalArgumentException("x and y need to be lesser than 8");
    }

    /**
     * Create array containing all possible moves of King and note its size. Compare to number of those spots
     * which are legalMoves for the pieces on the board. If number does not match then return false.
     * Returns true when game is over.
     */

    public ArrayList<int[]> getUnchecked(Piece king)
    {
        if (king.getName() != "king") throw new IllegalArgumentException();

        ArrayList<int[]> unchecked = new ArrayList<int[]>();
        int i = king.getPosition()[0]- 1;
        int imax = i + 3;
        int j = king.getPosition()[1] - 1;
        int jmax = j + 3;
        while (i < imax)
        {
            while (j < jmax)
            {
                if (i >= 0 && i < 8 && j >= 0 && j < 8)
                {
                    if (occupiedSquare(i, j) == null) unchecked.add(new int[]{i, j});
                }
                j++;
            }
            i++;
            j = king.getPosition()[1] - 1;
        }

        return unchecked;
    }

    public boolean checkMate(Piece king)
    {
        int originalx = king.getPosition()[0];
        int originaly = king.getPosition()[1];
        int number = 0;
        ArrayList<int[]> uncheckedspots = getUnchecked(king);
        for (int[] square: uncheckedspots)
        {
            for (Piece piece: pieces)
            {
                if (piece.getClass().getName() != "king" && piece.getisWhite() != king.getisWhite())
                {
                    king.setPosition(square[0],square[1]);
                    if (piece.islegalMove(square[0],square[1]))
                    {
                        number++; break;
                    }
                }
            }
        }
        king.setPosition(originalx, originaly);
        if (number < uncheckedspots.size() || number == 0) return false;
        else return true;
    }

    public boolean legalPiece(int x, int y, boolean isPlayer)
    {
        for (Piece piece: pieces)
        {
            if (Arrays.equals(piece.getPosition(),new int[] {x,y}))
            {
                if (isPlayer && piece.getisWhite())
                {
                    continue;
                }
                setCurrentPiece(piece);
                return true;
            }
        }
        return false;
    }

    public Piece occupiedSquare(int x, int y)
    {
        for (Piece piece: pieces)
        {
            if (Arrays.equals(piece.getPosition(),new int[] {x,y}))
            {
                return piece;
            }
        }
        return null;
    }

    //when a chess piece replaces another
    public void remove(Piece piece)
    {
        pieces.remove(piece);
    }

}

