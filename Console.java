import java.awt.EventQueue;

import javax.swing.JFrame;

public class Console extends JFrame{
	
	public Console() {
		initUI();
	}
	
	private void initUI() {
		add(new SnakeBoard());//entry point of game
		
		setResizable(false);
		pack();//puts the board in center of JFrame container
		
		setTitle("Snake");//sets title of frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exits the screen when closed
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {//makes code visible onscreen
		EventQueue.invokeLater(() ->{
			Console ex = new Console();
			ex.setVisible(true);
		});
	}
}
