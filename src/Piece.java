import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Parent class for all chess pieces. Provides position of chess piece and whether
 * it is on black or white team.
 * Also defines all actions a chess piece can perform, i.e its moving action and potentially
 *  * its ability to be promoted (only for pawns).
 * When method in Board is called, don't need to specify which particular Piece, thus use this abstract class
 * Abstract class is like an interface but can provide implementations of some methods
 */
public abstract class Piece
{
    protected int[] position;
    protected boolean isWhite;
    protected Board boardforPieces;
    protected BufferedImage bi;
    protected String name;
    protected boolean isonBoard;

    public Piece(int[] position,boolean isWhite, Board board, String name, String format, boolean isonBoard)
    {
        this.position = position;
        this.isWhite = isWhite;
        this.name = name;
        this.isonBoard = isonBoard;
        boardforPieces = board;
        bi = null;
        try {
            if (isWhite) bi = ImageIO.read(new File("uniformchesspieces\\" + name + "white." + format));
            else bi = ImageIO.read(new File("uniformchesspieces\\" + name + "black." + format));
        } catch (Exception e) {
        }
    }

    public void setPosition(int x, int y)
    {
        position[0] = x;
        position[1] = y;
    }

    public abstract boolean legalMove(int x, int y); /*checks if selected square would be a
                                       legal move for the current piece*/

    public String getName()
    {
      return name;
    }

    public int[] getPosition()
    {
        return position;
    }

    public boolean getTeam()
    {
        return isWhite;
    }

    public boolean getisWhite()
    {
        return isWhite;
    }

    public BufferedImage getBi()
    {
        return bi;
    }

    public boolean getisonBoard()
    {
        return isonBoard;
    }


}
