import javax.swing.*;
import java.util.*;

public class BoardBuilder
{

	private final int rows, cols;
	private String[][] whatShip;
	private char[][] board;

    public BoardBuilder(int rows, int cols)
    {
        this.rows = rows;
        this.cols = cols;
        board = new char[rows][cols];
        whatShip = new String[rows][cols];
    }

    public void place(Ship[] ships)
    {
	char[][] checked = new char[rows][cols];
        Random rand = new Random();
        boolean placed = false;

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                board[r][c] = '-'; // Empty Space

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
					if (board[r][c] == '-')
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
					board[i][col] = 'S';
					whatShip[i][col] = ship.getClass().getName();
				}
				break;
			case 1: // South
				for (int i = row; i <= row + (size - 1); i++)
				{
					board[i][col] = 'S';
					whatShip[i][col] = ship.getClass().getName();
				}
				break;

			case 2: // East
				for (int i = col; i <= col + (size - 1); i++)
				{
					board[row][i] = 'S';
					whatShip[row][i] = ship.getClass().getName();
				}
				break;

			case 3: // West
				for (int i = col; i >= col - (size - 1); i--)
				{
					board[row][i] = 'S';
					whatShip[row][i] = ship.getClass().getName();
				}
				break;
			default: System.out.println("Broken In place()"); break;
        }
    }

    private boolean canPlace(Ship ship, int row, int col, int direction)
    {
        int size = ship.getSize();
        boolean thereIsRoom = true;
        switch (direction)
        {
			case 0: // North
				if (row - (size - 1) < 0)
					thereIsRoom = false;
				else
					for (int  i = row; i >= row - (size - 1) && thereIsRoom; i--)
						thereIsRoom = thereIsRoom & (board[i][col] == '-');
			break;
			case 1: // South
				if (row + (size - 1) >= rows)
					thereIsRoom = false;
				else
					for (int i = row; i <= row + (size - 1) && thereIsRoom; i++)
						thereIsRoom  = thereIsRoom & (board[i][col] == '-');
			break;
			case 2: // East
				if (col + (size - 1) >= cols)
					thereIsRoom = false;
				else
					for (int i = col; i <= col + (size - 1) && thereIsRoom; i++)
						thereIsRoom = thereIsRoom & (board[row][i] == '-');
			break;
			
			case 3: // West
				if (col - (size - 1) < 0)
					thereIsRoom = false;
				else
					for (int i = col; i >= col - (size - 1) && thereIsRoom; i--)
						thereIsRoom = thereIsRoom & (board[row][i] == '-');
			break;
			default: System.out.println("Broken In canPlace()"); break;
		}
        return thereIsRoom;
    }
    public char[][] getBoard()
    {
		for(char[] ch : board)
		{
			for(char c : ch)
				System.out.print(c);
			System.out.println();
		}
		return board;
	}
	public String[][] getShipInfo()
	{
		return whatShip;
	}

}