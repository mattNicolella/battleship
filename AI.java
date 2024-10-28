import javax.swing.*;
import java.awt.Color;
import javafx.scene.control.Button;

public class AI
{
	private final int rows, cols;
	private boolean[][] p2Board, checked2;
	private BattleLogic cLogic;
	private BattleShipApplet b;
	private int difficulty;
	private int lastX, lastY, direction, origX, origY;
	private boolean hitPrev, isValid, didHit;
	private String[][] ships2;
	private Button[][] btnLoc2;
	private Ship sh;
	private String red = "-fx-background-color: red; -fx-border-color: black;";
	private String white = "-fx-background-color: white; -fx-border-color: black;";

	//Instantiate direction, hitPrev, isValid
	public AI(int difficulty, int rows, int cols, boolean[][] p2Board, boolean[][] checked2, BattleLogic cLogic, String[][] ships2, BattleShipApplet b, Button[][] btnLoc2, Ship sh)
	{
		this.sh = sh;
		this.btnLoc2 = btnLoc2;
		this.b = b;
		this.ships2 = ships2;
		this.p2Board = p2Board;
		this.cLogic = cLogic;
		this.checked2 = checked2;
		this.rows = rows;
		this.cols = cols;
		this.difficulty = difficulty;
		direction = 0;
		hitPrev = false;
		isValid = false;
	}

	/*Get which move (ie Easy, Medium, or Hard) based on what user picked at the beginning of the program*/
	public void move()
	{
		switch(difficulty)
		{
			case 0: aiMoveEasy((int)(Math.random()*rows), (int)(Math.random()*cols)); break;
			case 1: smartAiMove(lastX, lastY); break;
			case 2: cheatAiMove(lastX, lastY); break;
			default: System.out.println("This Is Matts Fault"); break;
		}
	}
	/*Easy Move: always random spots, if checked already, recurse. if sunk, say which is sinked.
	Red = hit and checked
	White = not hit and checked
	Blue = not checked
	Grey = Sunk*/
	public void aiMoveEasy(int x, int y)
	{
		if(p2Board[x][y] && !checked2[x][y])
		{
			checked2[x][y] = true;
			//System.out.println("You Have Hit: " + );
			if(cLogic.shipHit(x, y))
			{
				b.sinkShip2(ships2[x][y]);
				System.out.println("Sir He Got Our " + ships2[x][y] + "!");
			}
			else
			{
				btnLoc2[x][y].setStyle(red);
				hitPrev = true;
				lastX = x;
				lastY = y;
			}
		}
		else if(checked2[x][y])
		{
			if(b.anyLeft(checked2))
				aiMoveEasy((int)(Math.random()*rows), (int)(Math.random()*cols));
			else
				b.endGame(false);
		}
		else
		{
			btnLoc2[x][y].setStyle(white);
			checked2[x][y] = true;
		}
	}
	/*Medium Move: random move until hit. When hit, check each spot around the spot hit, once hit again,
	go in that direction until not hit or sunk.  Then, random again.
	Red = hit and checked
	White = not hit and checked
	Blue = not checked
	Grey = Sunk
	*/
	public void smartAiMove(int x, int y)
	{
		if(!sh.getPlayer())//Computer's turn
		{
			if((b.getIsValid(x - 1, y)) && (hitPrev == true))//up from previous hit
			{
				if((p2Board[x - 1][y]) && (!checked2[x - 1][y]))//Not checked and on the board
				{
					if(cLogic.shipHit(x - 1, y))//If AI sunk a ship
					{
						b.sinkShip2(ships2[x - 1][y]);
						System.out.println("Sir He Got Our " + ships2[x - 1][y] + "!");
						hitPrev = false;
						checked2[x - 1][y] = true;
					}
					else//if AI hit but not sunk
					{
						checked2[x - 1][y] = true;
						btnLoc2[x - 1][y].setStyle(red);
						didHit = true;
						lastX = x - 1;
						lastY = y;
					}
				}
				else if(checked2[x - 1][y])//If AI has checked that spot already
				{
					if(b.anyLeft(checked2))
						aiMoveEasy((int)(Math.random()*rows), (int)(Math.random()*cols));
					else
						b.endGame(false);
				}
				else//If AI fired and missed
				{
					btnLoc2[x - 1][y].setStyle(white);
					checked2[x - 1][y] = true;
				}
			}
			else if((b.getIsValid(x, y + 1)) && (hitPrev == true))//right from previous hit
			{
				if((p2Board[x][y + 1]) && (!checked2[x][y + 1]))//not checked and on the board
				{
					if(cLogic.shipHit(x, y + 1))//AI sunk a ship
					{
						b.sinkShip2(ships2[x][y + 1]);
						System.out.println("Sir He Got Our " + ships2[x][y + 1] + "!");
						hitPrev = false;
						checked2[x][y + 1] = true;
					}
					else//AI fired and hit
					{
						checked2[x][y + 1] = true;
						btnLoc2[x][y + 1].setStyle(red);
						didHit = true;
						lastX = x;
						lastY = y + 1;
					}
				}
				else if(checked2[x][y + 1])//AI checked that spot, so it does random again
				{
					if(b.anyLeft(checked2))
						aiMoveEasy((int)(Math.random()*rows), (int)(Math.random()*cols));
					else
						b.endGame(false);
				}
				else//AI fired and missed
				{
					btnLoc2[x][y + 1].setStyle(white);
					checked2[x][y + 1] = true;
				}
			}
			else if((b.getIsValid(x + 1, y)) && (hitPrev == true))//down from previous hit
			{
				if((p2Board[x + 1][y]) && (!checked2[x + 1][y]))//Not checked and on the board
				{
					if(cLogic.shipHit(x + 1, y))//ship sunk
					{
						b.sinkShip2(ships2[x + 1][y]);
						System.out.println("Sir He Got Our " + ships2[x + 1][y] + "!");
						hitPrev = false;
						checked2[x + 1][y] = true;
					}
					else//fired and hit
					{
						checked2[x + 1][y] = true;
						btnLoc2[x + 1][y].setStyle(red);
						didHit = true;
						lastX = x + 1;
						lastY = y;
					}
				}
				else if(checked2[x + 1][y])//checked spot alreaady
				{
					if(b.anyLeft(checked2))
						aiMoveEasy((int)(Math.random()*rows), (int)(Math.random()*cols));
					else
						b.endGame(false);
				}
				else//fired and missed
				{
					btnLoc2[x + 1][y].setStyle(white);
					checked2[x + 1][y] = true;
				}
			}
			else if((b.getIsValid(x, y - 1)) && (hitPrev == true))//left from previous hit
			{
				if((p2Board[x][y - 1]) && (!checked2[x][y - 1]))//Not checked and on the board
				{
					if(cLogic.shipHit(x, y - 1))//sunk ship
					{
						b.sinkShip2(ships2[x][y - 1]);
						System.out.println("Sir He Got Our " + ships2[x][y - 1] + "!");
						hitPrev = false;
						checked2[x][y - 1] = true;
					}
					else//fired and hit
					{
						checked2[x][y - 1] = true;
						btnLoc2[x][y - 1].setStyle(red);
						didHit = true;
						lastX = x;
						lastY = y - 1;
					}
				}
				else if(checked2[x][y - 1])//checked spot
				{
					if(b.anyLeft(checked2))
						aiMoveEasy((int)(Math.random()*rows), (int)(Math.random()*cols));
					else
						b.endGame(false);
				}
				else//fired and missed
				{
					btnLoc2[x][y - 1].setStyle(white);
					checked2[x][y - 1] = true;
				}
			}
			else if(!hitPrev)//if did not hit in the previous move
			{
				if(didHit)//didHit a ship in one of the previous moves
				{
					origX = x;
					origY = y;
					didHit = false;
					if((b.getIsValid(origX + 1, y)) && (!hitPrev))//up from previous hit
						if((p2Board[origX + 1][y]) && (!checked2[origX + 1][y]))
							btnLoc2[origX + 1][origY].setStyle(red);

					else if((b.getIsValid(x, origY - 1)) && (!hitPrev))//right from previous hit
						if((p2Board[x][origY - 1]) && (!checked2[x][origY - 1]))
							btnLoc2[origX][origY - 1].setStyle(red);

					else if((b.getIsValid(origX - 1, y)) && (!hitPrev))//down from previous hit
						if((p2Board[origX - 1][y]) && (!checked2[origX - 1][y]))
							btnLoc2[origX - 1][y].setStyle(red);

					else if((b.getIsValid(x, origY + 1)) && (!hitPrev))//left from previous hit
						if((p2Board[x][origY + 1]) && (!checked2[x][origY + 1]))
							btnLoc2[origX][origY + 1].setStyle(red);
				}
				x = (int)(Math.random()*rows);
				y = (int)(Math.random()*cols);
				aiMoveEasy(x, y);
			}
		}
	}
	/*Cheat (hard) move: Knows where the ships are before checked.
	Red = hit and checked
	White = not hit and checked
	Blue = not checked
	Grey = Sunk*/
	public void cheatAiMove(int x, int y) //Good Code But Cheats.
			{
				boolean gotMove = false;
				for(int i = 0; i < rows; i++)
					for(int j = 0; j < cols; j++)
					{
						if(p2Board[i][j] && !checked2[i][j] && !gotMove)
						{
							x = i;
							y = j;
							gotMove = true;
							checked2[i][j] = true;

							if(cLogic.shipHit(x, y))
							{
	                            b.sinkShip2(ships2[x][y]);
							} else
							{
							    btnLoc2[x][y].setStyle(red);
							}
						}
						if(!b.anyLeft(checked2))
						{
							//primaryStage.close();
						    b.endGame(false);
						}
					}
		}
}