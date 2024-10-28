public class BattleShipApplication
{
	private int rows, cols;
	private int difficulty = getDifficulty();
	private boolean placement = getPlacement();
	private boolean[][] p1Board, p2Board, checked, checked2;
	private String[][] ships, ships2; // Ships is players ships, ships2 is ai ships
	private Button[][] btnLoc, btnLoc2;
    	private AI ai;
	private BattleLogic pLogic, cLogic;
	private Ship sh;
	private String red, white, blue, gray;
	private buttonListener listener;

	public void start(String [] args)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		GridPane layout = new GridPane();

		layout.getRowConstraints().add(new RowConstraints(2));

		GridPane player1 = new GridPane();
		GridPane player2 = new GridPane();

		player1.getColumnConstraints().add(new ColumnConstraints(cols));
		player1.getRowConstraints().add(new RowConstraints(rows));

		player2.getColumnConstraints().add(new ColumnConstraints(cols));
		player2.getRowConstraints().add(new RowConstraints(rows));

		//super("Battleship");

		//Instantiate All Data
		rows = 10;
		cols = 10;
		pLogic = new BattleLogic(rows, cols, placement);
		cLogic = new BattleLogic(rows, cols, true);
		ai = new AI();
		red = "-fx-background-color: red; -fx-border-color: black;";
		white = "-fx-background-color: white; -fx-border-color: black;";
		blue = "-fx-background-color: blue; -fx-border-color: black;";
		gray = "-fx-background-color: gray; -fx-border-color: black;";

		//Create Containers, Panels, And Layouts
		//Container cp = getContentPane();
		//cp.setLayout(new GridLayout(0, 3));
		//JPanel player1 = new JPanel();
		//player1.setLayout(new GridLayout(rows, cols));

		//JPanel line = new JPanel();

		//JPanel player2 = new JPanel();
		//player2.setLayout(new GridLayout(rows, cols));

		//cp.setBackground(Color.black);

		//Instantiate Boards
		loader load = new loader();
		p1Board = pLogic.getBoard();
		pLogic.setPlayer(true);
		p2Board = cLogic.getBoard();
		checked = new boolean[rows][cols];
		checked2 = new boolean[rows][cols];
		sh = new Ship();

		//Get Ship Names
		ships = pLogic.getShips();
		ships2 = cLogic.getShips();

		//Set Applet Icon
		//this.setIconImage(new ImageIcon("images/battleship.png").getImage());
		//Set Default Close Operation
		//setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );


		//Instantiate Applet Buttons, Listener, And Colors
		listener = new buttonListener();
		btnLoc = new Button[rows][cols];
		btnLoc2 =  new Button[rows][cols];


		/*Left side of screen: computers fire, users board*/
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc2[i][j] = new Button();
				btnLoc2[i][j].setStyle(blue);
				//btnLoc2[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				player2.add(btnLoc2[i][j], i+1, j+1);
				//btnLoc2[i][j].setOnAction(listener);
				//btnLoc2[i][j].setDisable(true);
			}
		/*Right side of screen: users fire, computers board*/
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
			{
				btnLoc[i][j] = new Button();
				btnLoc[i][j].setStyle(blue);
				//btnLoc[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
				player1.add(btnLoc[i][j], i+1, j+1);
				btnLoc[i][j].setOnAction(listener);
			}

		HBox root = new HBox();

		root.getChildren().addAll(player1,player2);

		primaryStage.getIcons().add(new javafx.scene.image.Image("images/battleship.png"));
		primaryStage.setScene(new Scene(root, dim.width, dim.height));
		primaryStage.show();
	}
}