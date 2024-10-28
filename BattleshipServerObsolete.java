import java.io.*;
import java.net.*;
import java.util.*;

public class BattleshipServer
{
	private ServerSocket s;
	private Socket playerOne, playerTwo;
	private String playerOneIp, playerTwoIp;
	private User p1, p2;
	private int port = 4445;
	private boolean[][] playerOneBoard, playerTwoBoard;

	public static void main(String [] args)
	{
		new BattleshipServer();
	}

	public BattleshipServer()
	{
		try
		{
			s = new ServerSocket(port);

			System.out.println(InetAddress.getLocalHost().getHostAddress());

			playerOne = s.accept();
			p1 = new User(playerOne, "playerone");

			System.out.println("Player One Connected.");

			playerTwo = s.accept();
			p2 = new User(playerTwo, "playertwo");
			p2.sendMsg("3");
			System.out.println("Player Two Connected.");

			s.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void fire(int x, int y, String name)
	{
		try
		{
			if(name.equals("playerone"))
				p2.sendMsg("0 " + x + " " + y);
			else
				p1.sendMsg("0 " + x + " " + y);
		} catch(Exception e) {e.printStackTrace();}
	}
	public void hitOrMiss(int hit, String name)
	{
		if(name.equals("playerone"))
			p2.sendMsg("1 " + hit);
		else
			p1.sendMsg("1 " + hit);
		changeTurn();
	}
	public void endGame(String name)
	{
		if(name.equals("playerone"))
			p2.sendMsg("2");
		else
			p1.sendMsg("2");
	}
	public void changeTurn()
	{
		p1.sendMsg("3");
		p2.sendMsg("3");
	}
	//3 Changes Turn On Clients
	public void handler(String str, String name)
	{
		Scanner chop = new Scanner(str);

		switch(chop.nextInt())
		{
			case 0: fire(chop.nextInt(), chop.nextInt(), name); break;	//Shoot
			case 1: hitOrMiss(chop.nextInt(), name); break;				//Return For fire
			case 2: endGame(name);
			default: System.out.println("Unhandled Action in " + s.getInetAddress());

		}
	}
	private class User extends Thread
	{
		private Socket s;
		private DataOutputStream out;
		private DataInputStream in;
		private boolean isNew;
		private String name;

		public User(Socket s, String name)
		{
			this.s = s;
			this.name = name;
		}
		public void initStream()
		{
			try
			{
				out = new DataOutputStream(s.getOutputStream());
				in = new DataInputStream(s.getInputStream());
			} catch (Exception e) {e.printStackTrace();}
		}
		public void run()
		{
			try
			{
				handler(in.readUTF(), name);
			} catch (Exception e) {closeStream();}
		}
		public void sendMsg(String str)
		{
			try
			{
				out.writeUTF(str);
			} catch(Exception e) {e.printStackTrace();}
		}
		public void closeStream()
		{
			try
			{
				in.close();
				out.close();
				s.close();
			} catch (Exception e) {e.printStackTrace();}
		}
	}
}