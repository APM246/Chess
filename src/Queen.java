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

    public boolean legalMove(int x, int y)
    {
        if (rook.legalMove(x,y) || bishop.legalMove(x,y)) return true;
        else return false;
    }



}
