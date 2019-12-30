package board;

import java.util.ArrayList;
import java.lang.Math;
import pieces.*;

public class CPUBot
{
    private boolean isWhite; // the side the CPU takes
    private ArrayList<Piece> CPUpieces; // the chess pieces on the CPU's side
    private Board board;
    private Piece movingPiece;

    /*
    *   CREATE SHORTCUT FOR MAIN() LIKE IN INTELLIJ --> psvm alias
    */

    public CPUBot(boolean isWhite, Board board)
    {
        this.isWhite = isWhite;
        this.board = board;
        CPUpieces = new ArrayList<Piece>();
        populateCPUpieces();
    }

    public Piece getMovingPiece()
    {
        return movingPiece;
    }

    private void populateCPUpieces()
    {
        for (Piece piece: board.getPieces())
        {
            if (piece.getisWhite() == isWhite)
            {
                CPUpieces.add(piece);
            }
        }
    }

    public void removeCPUpiece(Piece toRemove)
    {
        for (Piece piece: CPUpieces)
        {
            if (piece == toRemove)
            {
                CPUpieces.remove(piece);
                return;
            }
        }   
    }

    private void calculateRandomCPUPiece()
    {
        Piece result;
        do
        {
            int random_number = (((int) (Math.random()*16))%CPUpieces.size());
            result = CPUpieces.get(random_number);
            result.calculateAvailableMoves();
        }
        while (result.getAvailableMoves().size() == 0);
       
        movingPiece = result;
    }

    // From random CPU piece chosen, choose a random legal move (function returns position it will move to)
    // CHANGE SO THAT IF A KILL IS AVAILABLE GO FOR THAT INSTEAD OF RANDOMLY CHOSEN MOVE
    public int[] getRandomCPUMove()
    {
        calculateRandomCPUPiece();
        int random_number = (int) (Math.random()*movingPiece.getAvailableMoves().size());
        return movingPiece.getAvailableMoves().get(random_number);
    }
}