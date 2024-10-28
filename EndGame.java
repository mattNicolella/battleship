import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.util.Random;

public class EndGame
{

	public static void main(String [] args)
	{
		EndGame ender = new EndGame(Math.random() < 0.5);
	}

	public EndGame(boolean didWin)
	{
		ImageIcon imgSinkMyBattleship;
		JWindow win = new JWindow();
		if(didWin)
			imgSinkMyBattleship = new ImageIcon("images/youWin.png");
		else
			imgSinkMyBattleship = new ImageIcon("images/youLose.png");
		JLabel lblSinkMyBattleship = new JLabel();
		lblSinkMyBattleship.setIcon(imgSinkMyBattleship);
		Container cp = win.getContentPane();
		win.setIconImage(imgSinkMyBattleship.getImage());
		Panel p = new Panel();

		JButton b = new JButton("Try Again");
		b.setBackground(new Color(59, 89, 182));
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
        b.setFont(new Font("Tahoma", Font.BOLD, 12));
        b.setSize(50,50);

		//p.add(b);


		cp.add(lblSinkMyBattleship, BorderLayout.CENTER);
		//cp.add(b);

		//b.setLocation(50, 50);
		//b.setPreferredSize(new Dimension(50, 50));


		//window.setLocationRelativeTo(null);
		win.setSize(640,360);

		win.setLocationRelativeTo(null);
		win.setVisible(true);
		win.toFront();
		//System.exit(0);
	}
}