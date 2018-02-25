package game;


public class Grid
{
  int length;
  int width;
  int ncolors;
  int [] count;
  Cell [][] Board;

  Grid(int length, int width, int ncolors)
  {
    this.length = length;
    this.width = width;
    this.ncolors = ncolors;
    count = new int[ncolors];
    Board = new Cell[length][width];

    for(int i = 0; i < ncolors; i++)
    {
      count[i] = 0;
    }

    for(int i = 0; i < length; i++)
    {
      for(int j = 0; j < width; j++)
      {
        Board[i][j] = new Cell();
        Board[i][j].setXY(i, j);

        if(cornerCell(i,j))
        {
          Board[i][j].setStability(1);
        }
        else if(boundaryCell(i,j))
        {
          Board[i][j].setStability(2);
        }
        else
        {
          Board[i][j].setStability(3);
        }
      }
    }
  }

  boolean cornerCell(int i, int j)
  {
    if(i == 0 && j == 0)
    {
      return true;
    }
    if(i == length-1 && j == 0)
    {
      return true;
    }
    if(i == length-1 && j == width-1)
    {
      return true;
    }
    if(i == 0 && j == width-1)
    {
      return true;
    }

    return false;
  }

  boolean boundaryCell(int i, int j)
  {
    if(i == 0 || j == 0 || i == length-1 || j == width - 1)
    {
      return true;
    }

    return false;
  }

  public boolean place(int i, int j, int color)
  {
    if(Board[i][j].checkColor(color) || i < 0 || j < 0 || i >= length || j >= width)
    {
      return false;
    }
    fill(i, j, color);
    return true;
  }
  void fill(int i, int j, int color)
  {
    if(i < 0 || j < 0 || i >= length || j >= width)
    {
      return;
    }

    if(Board[i][j].getValue() < Board[i][j].getStability())
    {
      if(Board[i][j].getColor() != 9)
      {
        count[Board[i][j].getColor()] -= Board[i][j].getValue();
      }
      Board[i][j].setValue(Board[i][j].getValue()+1);
      Board[i][j].setColor(color);
      count[Board[i][j].getColor()] += Board[i][j].getValue();

    }
    else
    {
      count[Board[i][j].getColor()] -= Board[i][j].getValue();
      Board[i][j].setValue(0);
      Board[i][j].setColor(9);
      fill(i-1, j, color);
      fill(i+1, j, color);
      fill(i, j-1, color);
      fill(i, j+1, color);
    }
  }

  public int getValue(int i, int j)
  {
    return Board[i][j].getValue();
  }

  public int getColor(int i , int j)
  {
    return Board[i][j].getColor();
  }

  public int getCount(int i)
  {
    return count[i];
  }
}
