import javax.swing.*;
import java.util.*;

public class Ship implements Comparable<Ship>
{
	private String name;
	private final int size;
	private int hits;
	private boolean alive, isPlayer;
	public Ship(String name, int size)
	{
		this.name = name;
		this.size = size;
		hits = 0;
		alive = true;
	}
	public Ship()
	{
		size = 0;
		name = "";
		hits = 0;
	}
	public void addHit()
	{
		hits++;
	}
	public int getSize()
	{
		return size;
	}
	public boolean testHits()
	{	if(isPlayer)
			System.out.println("\n\tHits:"+hits+"\n\tSize: "+size);
		if(hits >= size)
		{
			alive = false;
			return true;
		}
		return false;
	}
	public void setPlayer(boolean isPlayer)
	{
		this.isPlayer = isPlayer;
	}
	public boolean getPlayer()
	{
		return isPlayer;
	}
	public boolean isAlive()
	{
		return alive;
	}
	public int compareTo(Ship s)
	{
		if(size > s.size)
			return -1;
		else if(size == s.size)
			return 0;
		else
			return 1;
	}
}