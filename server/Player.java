
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import game.*;

public class Player extends Thread
{
  int color;
  int x;
  int y;
  Socket socket;
  Game game;
  BufferedReader input;
  PrintWriter output;

  public Player(Socket socket, int color, Game game)
  {
    this.socket = socket;
    this.color = color;
    this.game = game;

    try
    {
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);
      output.println("WELCOME ");
      output.println("MESSAGE Waiting for opponents to connect...");
    }catch(IOException e)
    {
      System.out.println("Player disconnected: " + e);
    }
  }

  public void displayBoard()
  {
    output.print("BOARD ");
    output.print(game.getLength());
    output.print(game.getWidth());

    for(int i = 0; i < game.getLength(); i++)
    {
      for(int j = 0; j < game.getWidth(); j++)
      {
        output.print(game.getCellColor(i, j));
        output.print(game.getCellValue(i, j));
      }
    }
    output.println();
  }

  public void run()
  {
    try
    {
      output.println("MESSAGE All players are connected");

      if(color == 0)
      {
        output.println("MESSAGE Your turn");
      }

      while(true)
      {
        int turn = game.getTurn();
        while(this.color != turn)
        {
            if(turn != game.getTurn())
            {
              if(game.gameover())
              {
                output.print("GAMEOVER ");
                output.println(turn);
                return;
              }
              turn = game.getTurn();
              displayBoard();
            }
            output.println('-');
        }

        output.println("MESSAGE Waiting for your move:");
        output.println("WAIT ");

        String command = input.readLine();
        if(command.startsWith("MOVE"))
        {
          int x = command.charAt(5) - '0';
          int y = command.charAt(6) - '0';

          game.move(x, y);
          displayBoard();
          if(game.gameover())
          {
            output.print("GAMEOVER ");
            output.println(color);
            return;
          }
        }
        else if(command.startsWith("QUIT"))
        {
          return;
        }
      }
    }catch(IOException e)
    {
      System.out.println("Player disconnected:" + e);
    }
    finally
    {
      try{socket.close();} catch(IOException e){}
    }
  }
}
