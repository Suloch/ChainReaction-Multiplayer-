
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import game.*;

public class GameServer
{
  public static void main(String [] args) throws Exception
  {
    int nplayers;
    int length;
    int width;

    Scanner s = new Scanner(System.in);
    ServerSocket listener = new ServerSocket(6666);

    System.out.print("Enter the number of players: ");
    nplayers = s.nextInt();
    System.out.print("Enter the length: ");
    length = s.nextInt();
    System.out.print("Enter the width: ");
    width = s.nextInt();

    System.out.println("Chain Reaction server is starting...");

    try
    {
      while(true)
      {
        Game game = new Game(length, width, nplayers);
        Player [] players = new Player[nplayers];
        for(int i = 0; i < nplayers; i++)
        {
          players[i] = new Player(listener.accept(), i, game);
        }

        for(int i = 0; i < nplayers; i++)
        {
          players[i].start();
        }
      }
    }finally
    {
      listener.close();
    }

  }
}
