
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient
{
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  private static int PORT = 6666;
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  Scanner s = new Scanner(System.in);
  int x;
  int y;

  public GameClient(String serverAddress) throws Exception
  {

    socket = new Socket(serverAddress, PORT);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    out = new PrintWriter(socket.getOutputStream(), true);
  }

  public void play() throws Exception
  {
    String response;
    try
    {
      while(true)
      {
        response = in.readLine();
        //out.println ("-");
        if(response.startsWith("WELCOME"))
        {
          System.out.println("Welcome, new game started");
        }
        else if(response.startsWith("MESSAGE"))
        {
          System.out.println(response.substring(8));
        }
        else if(response.startsWith("WAIT"))
        {
          System.out.print("Enter x: ");
          x = s.nextInt();
          System.out.print("Enter y: ");
          y = s.nextInt();

          out.print("MOVE ");
          out.print(x);
          out.println(y);
        }
        else if(response.startsWith("GAMEOVER"))
        {
          System.out.println("GAMEOVER\n\n");
          return;
        }
        else if(response.startsWith("BOARD"))
        {
          int length = response.charAt(6) - '0';
          int width = response.charAt(7) - '0';
          int count = 8;
          System.out.println("--------------------------------");
          for(int i = 0; i < length; i++)
          {
            for(int j = 0; j < width; j++)
            {
              switch(response.charAt(count++) - '0')
              {
                case 0:System.out.print(ANSI_RED); break;
                case 1:System.out.print(ANSI_CYAN); break;
                case 2:System.out.print(ANSI_PURPLE); break;
                case 3:System.out.print(ANSI_BLUE); break;
                case 4:System.out.print(ANSI_YELLOW); break;
              }
              System.out.print(response.charAt(count++) - '0');
              System.out.print(" " + ANSI_RESET);
            }
            System.out.println();
          }
          System.out.println("---------------------------------");
        }
      }
    }finally
    {
      socket.close();
    }
  }

  public static void main(String [] args) throws Exception
  {
    while(true)
    {
      String serverAddress = (args.length == 0) ? "localhost" : args[0];
      GameClient client = new GameClient(serverAddress);
      client.play();
    }
  }
}
