import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;
import java.lang.System;


/**
 * @author Arun Muthu
 * Visual representation of Board class using canvas1 class
 *  Ton of aliasing with currentPiece and originalPiece
 *  Still to add: Checkmate logic needs fixing (king can avoid check by replacing a piece that is checking it),
 *  king piece randomly moving for no reason (probably aliasing) and castling (optional).
 */

public class Viewer implements MouseListener
{
    private Canvas canvas1;
    private Canvas canvas2;
    private final int canvaslength = 600;
    private Board board;
    private int width;
    private int piecespace; //originally piecegap
    private Piece originalPiece; //original piece that is about to replace another piece [EQUAL TO CURRENTPIECE FROM
    //BOARD CLASS]

    public Viewer()
    {
        canvas1 = new Canvas("ChessBoard1",canvaslength,canvaslength,Color.BLACK);
        canvas1.setFont(new Font("Dialog",1,20));
        canvas2 = new Canvas("ChessBoard2",canvaslength,canvaslength+1,Color.BLACK);
        canvas2.setFont(new Font("Serif",Font.BOLD,18));
        board = new Board();
        width = 600/8;
        piecespace = (width/2) - 15;
        displayBoard();
        displayPieces();
        canvas1.addMouseListener(this);
        canvas2.addMouseListener(this);
    }

    private void displayBoard()
    {

        Color colour = new Color(165, 119, 14);
        //draws squares
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (j%2 != 0) colour = Color.ORANGE;
                canvas1.drawRectangle(width*j,width*i,width*(j+1),width*(i+1),colour);
                canvas2.drawRectangle(width*j,width*i,width*(j+1),width*(i+1),colour);
                colour = new Color(165,119,14);
            }

        }

        //draws grid
        for (int m = 1; m <= 7; m++)
        {
            canvas1.drawRectangle(0,width*m,600,width*m + 5,Color.BLACK);
            canvas1.drawRectangle(width*m,0,width*m+5,600,Color.BLACK);
            canvas2.drawRectangle(0,width*m,600,width*m + 5,Color.BLACK);
            canvas2.drawRectangle(width*m,0,width*m+5,600,Color.BLACK);
        }

    }

    /**
     * Displays pieces on board
     */

    public void displayPieces()
    {
        for (Piece i: board.getPieces())
        {
            if (i.getisonBoard())
            {
                Color color;
                if (i.getTeam()) color = Color.WHITE;
                else color = Color.BLACK;
                canvas1.paintImage(i.getBi(), i.getPosition()[0] * width + 1, i.getPosition()[1] * width + 1, 70, 70);
                canvas2.paintImage(i.getBi(), (7-i.getPosition()[0]) * width + 1, (7-i.getPosition()[1]) * width + 1, 70, 70);
            }
        }

    }

    public void updateDisplay()
    {
        displayBoard();
        displayPieces();
    }

    /**
     * ALWAYS BE AWARE OF ALIASING
     */
    public void mouseClicked(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON3)
        {
            for (Piece piece: board.getPieces())
            {
                if (piece.getName() == "king")
                {
                    if (board.checkMate(piece))
                    {
                        System.out.println("Game finished");

                        canvas1.drawRectangle(0,0,canvaslength , canvaslength ,Color.WHITE);
                        canvas1.drawString("Game finished",canvaslength/2, canvaslength/2, Color.BLUE);
                    }
                }
            }
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {}
    public void mouseEntered(MouseEvent mouseEvent) {}


    /**
     * something not right with use of originalPiece and currentPiece. Unrelated pieces just randomly moving during
     * game (have only observed King piece doing this).
     * @param e
     */
    public void mousePressed(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            int positionX = e.getX() / 75;
            int positionY = e.getY() / 75;
            if (e.getComponent().getBounds().height == 601)
            {
                positionX = 7 - positionX;
                positionY = 7 - positionY;
            }
            if (board.legalPiece(positionX, positionY))
            {
                originalPiece = board.getCurrentPiece();
            }
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            int positionX = e.getX() / 75;
            int positionY = e.getY() / 75;
            if (e.getComponent().getBounds().height == 601)
            {
                positionX = 7 - positionX;
                positionY = 7 - positionY;
            }
            if (positionX < 0 || positionX > 7 || positionY < 0 || positionY > 7)
            {
                throw new IllegalArgumentException("x and y need to be between 0 and 7 inclusive");
            }
            else
            {
                Piece tobereplaced = board.occupiedSquare(positionX, positionY);
                if (tobereplaced != null)
                {
                    if (tobereplaced.getName() == "king") return;
                }
                if (originalPiece.legalMove(positionX, positionY)) //board.getCurrentPiece().legalMove
                {
                    if (board.legalPiece(positionX, positionY))
                    {
                        if (tobereplaced.getisWhite() != originalPiece.getisWhite()) board.remove(tobereplaced);
                        else return; //return statement by itself can be used for a void method
                        // this return statement is for when chosen piece is on same team, thus cannot be replaced
                    }
                    originalPiece.setPosition(positionX, positionY);
                }
                updateDisplay();
            }
        }
    }


    public static void main(String[] args) {
        Viewer v = new Viewer();
        //v.mousePressed(184,98); can't do this because parameter is a MouseEvent. Thus need to create MouseEvent object
        // with one of its attributes being a relative (not absolute) position of (184,98)
        //v.mouseReleased(187,170);
    }
}
