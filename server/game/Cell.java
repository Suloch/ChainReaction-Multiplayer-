package game;

public class Cell
{
  int x, y;
  int stability;
  int value;
  int color;

  Cell()
  {
    this.x = -1;
    this.y = -1;
    this.stability = -1;
    value = 0;
    color = 9;
  }
  public int getValue()
  {
    return value;
  }
  public int getColor()
  {
    return color;
  }
  public int getStability()
  {
    return stability;
  }
  public void setValue(int value)
  {
    this.value = value;
  }
  public void setColor(int color)
  {
    this.color = color;
  }
  public void setXY(int x, int y)
  {
    this.x = x;
    this.y = y;
  }
  public void setStability(int stability)
  {
    this.stability = stability;
  }

  public boolean checkColor(int color)
  {
    if(this.color == 9)
    {
      return false;
    }

    else if(this.color != color)
    {
      return true;
    }

    return false;
  }
}
