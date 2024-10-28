import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.util.Random;
import javafx.application.Application;

public class loader
{
	public static void main(String [] args) throws Exception
	{
		JWindow window = new JWindow();
		ImageIcon imgSinkMyBattleship = new ImageIcon("images/maxresdefault.jpg");
		JLabel lblSinkMyBattleship = new JLabel();
		lblSinkMyBattleship.setIcon(imgSinkMyBattleship);
		window.getContentPane().add(lblSinkMyBattleship, BorderLayout.CENTER);

		//window.setLocationRelativeTo(null);
		window.setSize(640,360);

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		Thread.sleep(2500);

		window.setVisible(false);
		Application.launch(BattleShipApplet.class, args);
	}
}