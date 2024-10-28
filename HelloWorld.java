import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.awt.*;
import java.util.*;

public class HelloWorld extends Application {
	private Button[][] btn;
	private buttonListener b;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

		primaryStage.setTitle("Hello");

		try
		{
			String green = "-fx-border: 12px solid; -fx-border-color: black;";
			primaryStage.setTitle("Hello World!");
			btn = new Button[10][10];
			b = new buttonListener();

			GridPane root = new GridPane();
			root.getColumnConstraints().add(new ColumnConstraints(10));
			root.getRowConstraints().add(new RowConstraints(10));

			for(int i = 0; i < btn.length; i++)
				for(int j = 0; j <btn[i].length; j++)
				{
					btn[i][j] = new Button("h");
					btn[i][j].setStyle(green);
					root.add(btn[i][j], i+1, j+1);
					btn[i][j].setOnAction(b);
				}

			Scene s = new Scene(root, 300, 300);

			//s.setStyle("fx-background-color: black;");

			primaryStage.setScene(s);
			primaryStage.show();
		} catch(Exception e) {
			//System.err.print(e.printStackTrace());
			e.printStackTrace();
		}
    }
    private class buttonListener implements EventHandler<ActionEvent>
    {
		public void handle(ActionEvent e)
		{
			Object src = e.getSource();
			for(int i = 0; i < btn.length; i++)
				for(int j = 0; j < btn[i].length; j++)
					if(btn[i][j] == src)
					{
						System.out.println(i + ", " + j);
					}
		}
	}

}