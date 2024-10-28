import java.util.*;

public class playerPlacer
{
	private final int rows, cols;
	private String name;
	//private Ship ship;
	private int size, direction, coorRow, coorCol;
	//private int[] ship;
	private int[][] board;
	private boolean[][] loc;
	private int[][] coords;
	private Scanner sc;

	public static void main(String [] args)
	{
		new playerPlacer();
	}

	public playerPlacer(Ship ship, int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		sc = new Scanner(System.in);
		name = ship.getClass().getName();
		size = ship.getSize();
		//ship = new int[size];
		loc = new boolean[rows][cols];
		board = new int[rows][cols];
		coords = new int[size][2];
	}
	public void getPlacement()
	{
		System.out.print("Input Row For " + name + ": ");
		coorRow = sc.nextInt();
		System.out.print("\n\nInput Column For " + name + ": ");
		coorCol = sc.nextInt();
		System.out.println("\nKey For Direction:");
		System.out.println("1. Up");
		System.out.println("2. Right");
		System.out.println("3. Down");
		System.out.println("4. Left");
		System.out.print("\n\nInput Direction For " + name + ": ");
		direction = sc.nextInt();
		if(direction > 4 || direction < 1)
		{
			System.err.println("Invalid Direction, 1 ~ 4");
			getPlacement();
		}
		else if((coorCol > 9 || coorCol < 0)||
		   		(coorRow > 9 || coorRow < 0))
		{
			System.err.println("Invalid Input, Out Of Bounds (10 by 10, starting at 0 ending at 9)");
			getPlacement();
		}
		else if(!canPlace(coorRow, coorCol, direction, size))
		{
			System.err.println("Invalid Origin + Direction, Would Go Out Of Bounds");
			getPlacement();
		}
		else if(alreadyPlaced(coorRow, coorCol))
		{
			System.err.println("Invalid Placement of " + name + ". Another Ship is Already There");
			getPlacement();
		}
		else
		{
			loc[coorRow][coorCol] = true;
			if( (coorRow + size - 1 < 10) ||
				(coorRow - size + 1 >= 0) ||
				(coorCol + size - 1 < 10) ||
				(coorCol - size + 1 >= 0)	)
			{
				for(int i = 1; i < size; i++)
				{
					if(direction == 1)//Up
					{
						loc[coorRow - i][coorCol] = true;
					}
					else if(direction == 2)//Right
					{
						loc[coorRow][coorCol + i] = true;
					}
					else if(direction == 3)//Down
					{
						loc[coorRow + i][coorCol] = true;
					}
					else if(direction == 4)//Left
					{
						loc[coorRow][coorCol - i] = true;
					}
				}
			}
		}
	}
	public boolean canPlace(int row, int col, int way, int size)
	{
		if(way == 1)//Up
		{
			if(row - size + 1 > 9)
				return false;
		}
		else if(way == 2)//Right
		{
			if(col + size - 1 > 9)
				return false;
		}
		else if(way == 3)//Down
		{
			if(row + size - 1 < 0)
				return false;
		}
		else if(way == 4)//Left
		{
			if(col - size + 1 < 0)
				return false;
		}
		return true;
	}
	public boolean alreadyPlaced(int row, int col)
	{
		if(loc[row][col] == true)
			return true;
		return false;
	}
	public String toString()
	{
		String str = "";
		str += name + "\n" + size;
		for(boolean[] b : loc)
			str += "\n" + Arrays.toString(b);
		return str;
	}
	public boolean[][] getLoc()
	{
		return loc;
	}
}