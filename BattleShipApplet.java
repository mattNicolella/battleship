import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class BattleShipApplet extends Application
{
	//Initialize Logic Variables
	private int rows, cols;
	private int pvp = getOpponent();
	private int difficulty;
	private boolean placement = getPlacement();

	private boolean[][] p1Board, p2Board, checked, checked2;
	private String[][] ships, ships2;

	//Initialize Applet Objects
	private Button[][] btnLoc, btnLoc2;
	//Initialize All Other Game Logic And Objects
    private AI ai;
	//private BoardBuilder builder;
	private playerPlacer p;
	private BattleLogic pLogic, cLogic;
	private Ship sh;
	private String red, white, blue, gray, black;
	private buttonListener listener;
	private Stage primaryStage;
	private InternetListener list;
	private int locX = 0, locY = 0;

	//Only For Testing Purposes

	public static void main(String[] args)
	{
		launch(args);
	}

	public void start(Stage primaryStage)
	{
		if(pvp == 2)
			difficulty = getDifficulty();

		this.primaryStage = primaryStage;
		rows = 10;
		cols = 10;
		pLogic = new BattleLogic(rows, cols, placement);
		cLogic = new BattleLogic(rows, cols, true);
		red = "-fx-background-color: red; -fx-border-color: black;";
		white = "-fx-background-color: white; -fx-border-color: black;";
		blue = "-fx-background-color: blue; -fx-border-color: black;";
		gray = "-fx-background-color: gray; -fx-border-color: black;";
		black = "-fx-background-color: black; -fx-border-color: red;";

		loader load = new loader();
		p1Board = pLogic.getBoard();
		pLogic.setPlayer(true);
		p2Board = cLogic.getBoard();
		checked = new boolean[rows][cols];
		checked2 = new boolean[rows][cols];
		sh = new Ship();

		GridPane layout = new GridPane();

		ColumnConstraints column = new ColumnConstraints(cols);
		//column.setPercentWidth(cols);

		RowConstraints row = new RowConstraints(rows);
		//row.setPercentHeight(rows);

		layout.getRowConstraints().add(new RowConstraints(2));

		GridPane player1 = new GridPane();
		GridPane player2 = new GridPane();

		//row.setHgrow(Priority.ALWAYS);
		//column.setHgrow(Priority.ALWAYS);

		player1.getColumnConstraints().add(column);
		player1.getRowConstraints().add(row);

		player2.getColumnConstraints().add(column);
		player2.getRowConstraints().add(row);

		ships = pLogic.getShips();
		ships2 = cLogic.getShips();

		//player1.setPrefSize(dim.width/2, dim.height);
		//player2.setPrefSize(dim.width/2, dim.height);

		listener = new buttonListener();
		btnLoc = new Button[rows][cols];
		btnLoc2 =  new Button[rows][cols];

		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc2[i][j] = new Button();
				btnLoc2[i][j].setStyle(blue);
				player2.add(btnLoc2[i][j], i+1, j+1);
				//player2.setHgrow(btnLoc2[i][j], Priority.ALWAYS);
				//btnLoc2[i][j].hfill(true);
				//btnLoc[i][j].setPadding(new Insets(10, 10, 10, 10));
				//btnLoc2[i][j].setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc[i][j] = new Button();
				btnLoc[i][j].setStyle(blue);
				player1.add(btnLoc[i][j], i+1, j+1);
				btnLoc[i][j].setOnAction(listener);
				//player1.setHgrow(btnLoc[i][j], Priority.ALWAYS);
				//btnLoc[i][j].setPrefSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}

		HBox root = new HBox();

		root.getChildren().addAll(player1,player2);

		//player1.setPrefSize(dim.width/2, dim.height);
		//player2.setPrefSize(dim.width/2, dim.height);
		player1.setPadding(new javafx.geometry.Insets(75, 0, 0, 50));
		player2.setPadding(new javafx.geometry.Insets(75, 0, 0, 100));

		//root.setPrefSize(dim.width, dim.height);
		//root.setHgrow(player1, Priority.ALWAYS);
		//root.setHgrow(player2, Priority.ALWAYS);
		Scene s = new Scene(root, 600, 400);
		root.setStyle("-fx-background-image: url('images/back.png'); -fx-background-repeat: stretch;");
		primaryStage.getIcons().add(new javafx.scene.image.Image("images/battleship.png"));
		primaryStage.setScene(s);
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("Battleship");
		primaryStage.show();

		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				if(p2Board[i][j])
					btnLoc2[i][j].setStyle(gray);

		javax.swing.Timer tmr = new javax.swing.Timer(15, new TimerListener());

		if(pvp == 1)
		{
			lock();
			list = new InternetListener(getIp());
		}
		else if(pvp == 2)
			ai = new AI(difficulty, rows, cols, p2Board, checked2, cLogic, ships2, this, btnLoc2, sh);
	}

	/*ButtonListener for the class*/
	private class buttonListener implements EventHandler<ActionEvent>
	{
		public void handle(ActionEvent e)
		{
			Object s = e.getSource();
			int locX, locY;

			//System.out.println("ghghghgh");
			if(pvp == 2)
			{
				for(int x = 0; x < rows; x++)
					for(int y = 0; y < cols; y++)
						if(s == btnLoc[x][y])
							if(p1Board[x][y] && !checked[x][y])
							{
								checked[x][y] = true;
								System.out.println("You Have Hit: " + ships[x][y]);
								if(pLogic.shipHit(x, y))
								{
									sinkShip1(ships[x][y]);
									System.out.println("You Sank My "+ships[x][y]+"! Im Gonna Get You!");
								}
								else
									btnLoc[x][y].setStyle(red);
							}
							else if(checked[x][y])
							{
								System.out.println("You Have Fired There Before, The General Has Taken You Out Of Command For A Turn!");
							}
							else
							{
								btnLoc[x][y].setStyle(white);
								checked[x][y] = true;
							}
					ai.move();
					if(!pLogic.anyShipsRemain())
						endGame(true);
					else if(!cLogic.anyShipsRemain())
						endGame(false);
				}
				else if(pvp == 0)
				{
					lock();
					for(int i = 0; i < rows; i++)
						for(int j = 0; j < cols; j++)
							if(s == btnLoc[i][j])
							{
								locX = i;
								locY = j;
								list.fire(locX, locY);
							}
					if(!pLogic.anyShipsRemain())
					{
						list.endGame();
						new EndGame(false);
					}
				}
						//ai.move();

		}

	}
	/*anyLeft(boolean[][] spots) returns true if all the spots on the board are hit, false otherwise*/
	public boolean anyLeft(boolean[][] spots)
	{
		for(boolean[] t : spots)
			for(boolean f : t)
				if(!f)
					return true;
		return false;
	}
	/*endGame(boolean winner) fires the EndGame code if all the ships are sunk or all spots are fired upon*/
	public void endGame(boolean winner)
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc[i][j].setDisable(true);
				btnLoc2[i][j].setDisable(true);
			}
		EndGame ender = new EndGame(winner);
		//this.setVisible(false);
		//this.dispose();
	}
	/*sinkShip1 sinks the ship for the computer on user board*/
	public void sinkShip1(String str)
	{
		for(int x = 0; x < rows; x++)
			for(int y = 0; y < cols; y++)
				if(ships[x][y]!=null)
					if(ships[x][y].equals(str))
						btnLoc[x][y].setStyle(black);
	}
	/*sinkShip2 sinks the ship for the user on the computer board*/
	public void sinkShip2(String str)
	{
		for(int x = 0; x < rows; x++)
			for(int y = 0; y < cols; y++)
				if(ships2[x][y]!=null)
					if(ships2[x][y].equals(str))
						btnLoc2[x][y].setStyle(black);
	}
	public void handler(String input)
	{
		Scanner chop = new Scanner(input);
		switch(chop.nextInt())
		{
			case 0:	incoming(chop.nextInt()); break;
			case 1: hitOrMiss(chop.nextInt(), chop.nextInt()); break;
			case 2: new EndGame(true); break;
			case 3: unlock(); break;
		}
	}
	/*Private class for the AI*/
	public boolean getIsValid(int x, int y)//is on the board at position [x][y]
	{
		try
		{
			if(p2Board[x][y])
				return true;
		} catch(Exception e){ return false;}
		return false;
	}
	/*Option Pane at start of program,
	lets user choose if the user wants to have it place the ships for the user or place ships manually*/
	public boolean getPlacement()
	{
		//Object[] options = {"Automatic", "Manual"};
		//int res = JOptionPane.showOptionDialog(null, "Would You Like To Place Your Ships Automatically, Or Manually? ",
		//	"Ship Placement", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		//if(res == JOptionPane.YES_OPTION)
			return true;
		//else
		//	return false;
	}
	/*Option Pane at start of program,
	lets user choose which difficulty of AI they want to face*/
	public int getDifficulty()
	{
		Object[] options = {"Basic Ai(easy)", "Smart Ai(medium)", "Cheat Ai(hard)"};
		return (int) JOptionPane.showOptionDialog(null, "What Ai Difficulty Would You Like To Verse? ",
			"Difficulty Level", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		//return 0;
	}
	public int getOpponent()
	{
		Object[] options = {"Player v Player(local)", "Plaver v Player(lan)", "Player v AI"};
		return (int) JOptionPane.showOptionDialog(null, "Who would you like to play against? ",
			"Opponent Select", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
	}
	public String getIp()
	{
		return JOptionPane.showInputDialog(null, "What Is The Server Ip?");
	}
	public void lock()
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc[i][j].setDisable(true);
			}
	}
	public void unlock()
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc[i][j].setDisable(false);
			}
	}
	public void incoming(int i)
	{
		unlock();

		if(i == 1)
			btnLoc2[locX][locY].setStyle(red);
		else
			btnLoc2[locX][locY].setStyle(white);
	}
	public void hitOrMiss(int x, int y)
	{
		if(p2Board[x][y])
			list.sendMsg("1 1");
		else
			list.sendMsg("1 0");
	}
	private class TimerListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(list.newMsg())
				handler(list.getMsg());
		}
	}
}

