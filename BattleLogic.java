import javax.swing.*;
import java.util.*;
public class BattleLogic
{
	private Ship[] ships;
	private String[][] who;
	private char[][] charBoard;
	private boolean[][] board;
	private BoardBuilder builder;
	private playerPlacer placer;
	private final int rows, cols;
	private boolean isPlayer;

	public BattleLogic(int rows, int cols, boolean autoPlace)
	{
		this.rows = rows;
		this.cols = cols;
		ships = new Ship[5];
		ships[0] = new Carrier();
		ships[1] = new Battleship();
		ships[2] = new Destroyer();
		ships[3] = new Submarine();
		ships[4] = new Patrolboat();
		//ships[5] = new Ship("Banana Boat", 7);

		Arrays.sort(ships);
		board = new boolean[rows][cols];
		who = new String[rows][cols];
		builder = new BoardBuilder(rows, cols);

		if(autoPlace)
			place(ships);
		else
			for(Ship ship : ships)
				placer = new playerPlacer(ship, rows, cols);

		isPlayer = false;
	}
	public boolean[][] getBoard()
	{
		return board;
	}
	public String[][] getShips()
	{
		return who;
	}
	public boolean shipHit(int x, int y)
	{
		switch(who[x][y])
		{
			case "Carrier":
				ships[0].addHit();
				return checkHits(ships[0]);
			case "Battleship":
				ships[1].addHit();
				return checkHits(ships[1]);
			case "Destroyer":
				ships[2].addHit();
				return checkHits(ships[2]);
			case "Submarine":
				ships[3].addHit();
				return checkHits(ships[3]);
			case "Patrolboat":
				ships[4].addHit();
				return checkHits(ships[4]);
		}
		return false;
	}
	public boolean checkHits(Ship sh)
	{
		return sh.testHits();
	}
	public boolean anyShipsRemain()
	{
		int count = 0;
		boolean alive = true;
		for(Ship s : ships)
			if(!s.isAlive())
				count++;
		if(count == ships.length)
			alive = false;
		return alive;
	}
	public void setPlayer(boolean player)
	{
		isPlayer = player;
		for(Ship s : ships)
			s.setPlayer(isPlayer);
	}
	public boolean isPlayer()
	{
		return isPlayer;
	}
	public void place(Ship[] ships)
	{
		char[][] checked = new char[rows][cols];
		Random rand = new Random();
		boolean placed = false;

		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				board[r][c] = false; // Empty Space

		for (int i = ships.length - 1; i >=0; i--)
		{
			for (int j = 0; j < rows; j++)
				for (int k = 0; k < cols; k++)
					checked[j][k] = 'U'; // Unchecked

			while (!placed)
			{
				int r = rand.nextInt(rows);
				int c = rand.nextInt(cols);
				if (checked[r][c] == 'U')
				{
					checked[r][c] = 'C'; // Checked position
					if (board[r][c] == false)
					{
						int direction = rand.nextInt(4);
						// 0 = North
						// 1 = East
						// 2 = South
						// 3 = West

						if (canPlace(ships[i], r, c, direction))
						{
							place(ships[i], r, c, direction);
							placed = true;
						}
					}
				}
			}
			placed = false;
	       }
		}

	    private void place(Ship ship, int row, int col, int direction)
	    {
	        int size = ship.getSize();
	        switch (direction)
	        {
				case 0: // North
					for (int  i = row; i >= row - (size - 1); i--)
					{
						board[i][col] = true;
						who[i][col] = ship.getClass().getName();
					}
					break;
				case 1: // South
					for (int i = row; i <= row + (size - 1); i++)
					{
						board[i][col] = true;
						who[i][col] = ship.getClass().getName();
					}
					break;

				case 2: // East
					for (int i = col; i <= col + (size - 1); i++)
					{
						board[row][i] = true;
						who[row][i] = ship.getClass().getName();
					}
					break;

				case 3: // West
					for (int i = col; i >= col - (size - 1); i--)
					{
						board[row][i] = true;
						who[row][i] = ship.getClass().getName();
					}
					break;
				default: System.out.println("Broken In place()"); break;
	        }
	    }

	    private boolean canPlace(Ship ship, int row, int col, int direction)
	    {
	        int size = ship.getSize();
	        boolean isRoom = true;
	        switch (direction)
	        {
				case 0: // North
					if (row - (size - 1) < 0)
						isRoom = false;
					else
						for (int  i = row; i >= row - (size - 1) && isRoom; i--)
							isRoom = isRoom & (board[i][col] == false);
				break;
				case 1: // South
					if (row + (size - 1) >= rows)
						isRoom = false;
					else
						for (int i = row; i <= row + (size - 1) && isRoom; i++)
							isRoom  = isRoom & (board[i][col] == false);
				break;
				case 2: // East
					if (col + (size - 1) >= cols)
						isRoom = false;
					else
						for (int i = col; i <= col + (size - 1) && isRoom; i++)
							isRoom = isRoom & (board[row][i] == false);
				break;

				case 3: // West
					if (col - (size - 1) < 0)
						isRoom = false;
					else
						for (int i = col; i >= col - (size - 1) && isRoom; i--)
							isRoom = isRoom & (board[row][i] == false);
				break;
				default: System.out.println("Broken In canPlace()"); break;
			}
	        return isRoom;
    }
}