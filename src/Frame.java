import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Frame extends JFrame{

	public static String title = "Tower Defense Game";
	public static Dimension size = new Dimension(700, 550);//This make it look pro ;) lol
	
	public Frame(){
		setTitle(title);
		setSize(size);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();
	}
	
	public void init(){
		setLayout(new GridLayout(1, 1, 0, 0));//Preps the frame for a grid.
		
		Screen screen = new Screen(this);
		add(screen);
		setVisible(true);
		System.out.println("got Frame");
	}
	
	public static void main(String args[]) {
		Frame frame = new Frame();
	}
	

	
}
