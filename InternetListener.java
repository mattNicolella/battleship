import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.*;

public class InternetListener extends Thread
{
	private Socket s;
	private DataInputStream in;
	private DataOutputStream out;
	private String ip, msg;
	private boolean newMsg;

	public InternetListener(String ip)
	{
		this.ip = ip;
		new Client(ip, 4444);
	}
	public void initStream()
	{
		try
		{
			s = new Socket(ip, 4445);
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
		} catch(Exception e) {e.printStackTrace();}
	}
	public void fire(int x, int y)
	{
		sendMsg("0 " + x + " " + y);
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
			s.close();
			in.close();
			out.close();
		} catch(Exception e) {e.printStackTrace();}
	}
	public void run()
	{
		try
		{
			msg = in.readUTF();
		} catch(Exception e) {e.printStackTrace();}
	}
	public void endGame()
	{
		sendMsg("2");
	}
	public boolean newMsg()
	{
		return newMsg;
	}
	public String getMsg()
	{
		newMsg = false;
		return msg;
	}
}