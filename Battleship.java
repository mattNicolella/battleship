public class Battleship extends Ship
{
	private final int size = 4;
	public Battleship()
	{
		super("Battleship", 4);
	}
	public int getSize()
	{
		return size;
	}
}