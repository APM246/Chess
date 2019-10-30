public class Knight extends Piece
{
    public Knight(int[] position, boolean isWhite, Board board)
    {
        super(position, isWhite, board, "knight", "png", true);
    }

    public boolean legalMove(int x, int y)
    {
      if (x == position[0] - 1)
      {
          if (y == position[1] - 2 || y == position[1] + 2) return true;
          else return false;
      }

      if (x == position[0] + 1)
      {
          if (y == position[1] - 2 || y == position[1] + 2) return true;
          else return false;
      }

      if (y == position[1] - 1)
      {
          if (x == position[0] + 2 || x == position[0] - 2) return true;
          else return false;
      }

      if (y == position[1] + 1)
      {
          if (x == position[0] + 2 || x == position[0] - 2) return true;
          else return false;
      }

      return false;
    }
}
