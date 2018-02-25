package game;

public class Game
{
  int length;
  int width;
  int nplayers;
  int turn;
  boolean [] start;
  Grid grid1;

  public Game(int length, int width, int nplayers)
  {
    this.width = width;
    this.length = length;
    this.nplayers = nplayers;
    start = new boolean[nplayers];
    for(int i = 0; i < nplayers; i++)
    {
      start[i] = true;
    }
    turn = 0;
    grid1 = new Grid(length, width, nplayers);
  }

  public synchronized void move(int i, int j)
  {
    if(start[turn] || grid1.getCount(turn) != 0 )
    {
      if(!grid1.place(i, j, turn))
      {
        return;
      }
    }
    start[turn] = false;
    turn++;
    turn = turn % nplayers;
  }

  public int getTurn()
  {
    return turn;
  }

  public boolean gameover()
  {
    int lost = 0;
    for(int i = 0; i < nplayers; i++)
    {
      if(!start[i] && grid1.getCount(i) == 0)
      {
        lost++;
      }
    }
    if(lost == nplayers - 1)
    {
      return true;
    }
    return false;
  }

  public int getCellValue(int i, int j)
  {
    return grid1.getValue(i , j);
  }

  public int getCellColor(int i, int j)
  {
    return grid1.getColor(i, j);
  }

  public int getLength()
  {
    return length;
  }

  public int getWidth()
  {
    return width;
  }
}
