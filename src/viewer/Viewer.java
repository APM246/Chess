package viewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Font;
import java.lang.System;
import java.util.Arrays;

import pieces.*;
import board.*;

/**
 * @author Arun Muthu
 *  Ton of aliasing with currentPiece and movingPiece
 *  Still to add: Checkmate logic needs fixing (king can avoid check by replacing a piece that is checking it),
 *  king piece randomly moving for no reason (probably aliasing) and castling (optional).
 */

public class Viewer implements MouseListener
{
    private Canvas canvas1;
    private Canvas canvas2;
    private Board board;
    private CPUBot bot;
    private final int width;
    private Piece movingPiece; //original piece that is about to replace another piece [EQUAL TO CURRENTPIECE FROM
    //BOARD CLASS]

    // CONSTANTS 
    private final int CANVAS_LENGTH = 600;
    private final int BOARD_WIDTH = CANVAS_LENGTH/8;

    // WHEN GAME HAS BEEN COMPLETED
    private boolean isFinished;

    public Viewer()
    {
        canvas1 = new Canvas("Player",CANVAS_LENGTH,CANVAS_LENGTH,Color.BLACK);
        canvas1.setFont(new Font("Dialog",1,20));
        canvas2 = new Canvas("Bot", CANVAS_LENGTH, CANVAS_LENGTH + 1, Color.BLACK);
        canvas2.setFont(new Font("Serif", Font.BOLD, 18));
        canvas2.addMouseListener(this);
        board = new Board();
        width = BOARD_WIDTH;
        displayBoard();
        displayPieces();
        canvas1.addMouseListener(this);
        bot = new CPUBot(true, board);
        isFinished = false;
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

                        canvas1.drawRectangle(0,0,CANVAS_LENGTH , CANVAS_LENGTH ,Color.WHITE);
                        canvas1.drawString("Game finished",CANVAS_LENGTH/2, CANVAS_LENGTH/2, Color.BLUE);
                        isFinished = true;
                    }
                }
            }
        }
    }

    public void mouseExited(MouseEvent mouseEvent) {}
    public void mouseEntered(MouseEvent mouseEvent) {}


    /**
     * something not right with use of movingPiece and currentPiece. Unrelated pieces just randomly moving during
     * game (have only observed King piece doing this).
     * @param e
     */
    public void mousePressed(MouseEvent e)
    {
        if (isFinished) return;

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
                movingPiece = board.getCurrentPiece();
            }
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if (isFinished) return;

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
                Piece stationaryPiece = board.occupiedSquare(positionX, positionY);
                if (stationaryPiece != null)
                {
                    if (stationaryPiece.getName() == "king") return;
                }
                if (movingPiece.islegalMove(positionX, positionY)) //board.getCurrentPiece().legalMove
                {
                    if (board.legalPiece(positionX, positionY))
                    {
                        if (stationaryPiece.getisWhite() != movingPiece.getisWhite()) 
                        {
                            board.remove(stationaryPiece);
                            bot.removeCPUpiece(stationaryPiece);
                        }

                        else return; // chosen piece is on same team, thus cannot be replaced
                    }
                    movingPiece.setPosition(positionX, positionY);
                }
                updateDisplay();
            }

            // AI makes its move
            try
            {
                //Thread.sleep(1000); // make AI seem more realistic by taking several seconds to make move
                int[] new_position = bot.getRandomCPUMove();
                movingPiece = bot.getMovingPiece();
                Piece stationaryPiece = board.occupiedSquare(new_position[0], new_position[1]);
                if (stationaryPiece == null)
                {
                    movingPiece.setPosition(new_position[0], new_position[1]);
                }
                else if (stationaryPiece.getisWhite() != movingPiece.getisWhite())
                {
                    movingPiece.setPosition(new_position[0], new_position[1]);
                    board.remove(stationaryPiece);
                }
                else
                {
                    return; // can't move to spot if occupied by member of same team
                }
                updateDisplay();
                // rewrite getAvailableMoves() in CPUBot to ensure available moves are legal, i.e. moving to empty spot or eliminating opponent only
                // or make the above code recursive (keep trying until legal move is found)
            }

            catch (Exception ie)
            {
                ie.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Viewer();
        //v.mousePressed(184,98); can't do this because parameter is a MouseEvent. Thus need to create MouseEvent object
        // with one of its attributes being a relative (not absolute) position of (184,98)
        //v.mouseReleased(187,170);
    }
}
