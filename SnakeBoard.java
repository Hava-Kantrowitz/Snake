import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Plays the game snake
 * @author havak
 *
 */
public class SnakeBoard extends JPanel implements ActionListener{
	
	private final int B_WIDTH = 400;//board width
	private final int B_HEIGHT = 400;//board height
	private Timer timer;//the timer to run the animation
	private final int DELAY = 150;//the speed of the animation in ms
	private final int ALL_DOTS = 1600;//the max number of dots that can be on the board
	private final int RAND_POS_DOT = 27;//random number for random dot positioning
	private final int DOT_SIZE = 10;//size of the dots
	
	private final int[] x = new int[ALL_DOTS];//the x coordinates of the snake
	private final int[] y = new int[ALL_DOTS];//the y coordinates of the snake
	
	private int dots;//number of dots
	private int apple_x;//x coordinate of apple
	private int apple_y;//y coordinate of apple
	
	private boolean leftDir = false;
	private boolean rightDir = true;//starting going right
	private boolean upDir = false;
	private boolean downDir = false;
	
	//The images
	private Image dot;
	private Image apple;
	private Image head_dot;
	
	private boolean inGame = true;//whether the game is running or not
	private int applesEaten = 0;//the number of apples eaten by snake
	
	/**
	 * Constructor
	 * When the snake game starts, the board is initialized
	 */
	public SnakeBoard() {
		initBoard();
	}
	
	/**
	 * Initializes the board
	 * Adds a key listener
	 * Sets the focus, background color, and size
	 * Loads images
	 */
	public void initBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}
	
	/**
	 * Loads the images
	 * Three images are in the game: green dot for body, red dot for head, and apple
	 */
	protected void loadImages() {
		ImageIcon dotIm = new ImageIcon("src/dot.png");
		dot = dotIm.getImage();
		
		ImageIcon appleIm = new ImageIcon("src/apple.png");
		apple = appleIm.getImage();
		
		ImageIcon headIm = new ImageIcon("src/head.png");
		head_dot = headIm.getImage();
	}
	
	/**
	 * Initializes the game
	 */
	private void initGame() {
		dots = 3;//starts with the 3 dots
		
		//initializes their starting locations
		for(int i = 0; i < dots; i++) {
			x[i] = 50 - i * 10;
			y[i] = 50;
		}
		
		locateApple();//puts the apple in place
		timer = new Timer(DELAY, this);//initializes the timer
		timer.start();//starts the animation
	}
	
	/**
	 * Paints the screen
	 * @param g the input of the graphics class
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}
	
	/**
	 * Performs the actual drawing
	 * @param g the input of the graphics class
	 */
	private void doDrawing(Graphics g) {
		if(inGame) {//if the game is going on, draw
			g.drawImage(apple, apple_x, apple_y, this);//draw the apple at its coordinates
			for(int i = 0; i < dots; i++) {//draw the dots at their coordinates
				if(i == 0) {
					g.drawImage(head_dot, x[i], y[i], this);//the head should be at the first location
				}
				else {
					g.drawImage(dot, x[i], y[i], this);
				}
			}
			
			Toolkit.getDefaultToolkit().sync();//smooth out the animation
		}
		
		else {
			gameOver(g);//if the game isn't going on, go into game over animation
		}
	}
	
	/**
	 * Animation that occurs when the game is over
	 * @param g the input of the graphics class
	 */
	private void gameOver(Graphics g) {
		Font small = new Font("Helvetica", Font.BOLD, 20);
		
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString("Game Over! Apples eaten: " + applesEaten, 70, 200);
	}
	
	/**
	 * Determines whether the apple can be eaten
	 */
	private void eatApple() {
		if((x[0] == apple_x) && (y[0] == apple_y)) {//if the apple coordinates are the same as the coordinates of the head
			dots++;//increments the dots count
			applesEaten++;
			locateApple();//relocates a new apple
		}
	}
	
	/**
	 * Moves the snake
	 */
	private void move() {
		for(int i = dots; i > 0; i--) {//moves the whole snake up
			x[i] = x[(i-1)];
			y[i] = y[(i-1)];
		}
		
		//moves the pixels in the proper direction to get the "wiggling snake" look
		if(leftDir) {
			x[0] -= DOT_SIZE;
		}
		
		if(rightDir) {
			x[0] += DOT_SIZE;
		}
		
		if(upDir) {
			y[0] -= DOT_SIZE;
		}
		
		if(downDir) {
			y[0] += DOT_SIZE;
		}
	}
	
	/**
	 * Determines if the snake has collided with itself or the wall
	 */
	private void checkCollision() {
		for(int i = dots; i > 0; i--) {//goes through the snake body, determining if the head is at the same coordinates as any body dot
			if((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
				inGame = false;
			}
		}
		
		//Determines if the snake has gone off any of the board corners
		if(y[0] >= B_HEIGHT) {
			inGame = false;
		}
		
		if(y[0] < 0) {
			inGame = false;
		}
		
		if(x[0] >= B_WIDTH) {
			inGame = false;
		}
		
		if(x[0] < 0) {
			inGame = false;
		}
		
		if(!inGame) {//if any is hit, stop the timer
			timer.stop();
		}
	}
	
	/**
	 * Places the apple at a random square
	 */
	private void locateApple() {
		int r = (int) (Math.random() * RAND_POS_DOT);//random x location
		apple_x = ((r*DOT_SIZE));//set it to the x coordinate
		
		r = (int) (Math.random() * RAND_POS_DOT);//random y location
		apple_y = ((r*DOT_SIZE));//set it to the y coordinate
	}
	
	/**
	 * The action performed every delay miliseconds of the timed animation
	 * @param arg0 the input to action performed
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//if the game is still going on
		if(inGame) {
			eatApple();//eat an apple if you can
			checkCollision();//check for collisions
			move();//move
		}
		
		repaint();//repaint the board
		
	}
	
	/**
	 * This class handles all key press events
	 * @author havak
	 *
	 */
	private class TAdapter extends KeyAdapter{
		
		/**
		 * Determies course of action when the key is pressed
		 * If you are going in one direction, you can't go in the immediate opposite direction and walk in on yourself
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			//What happens at left key
			if((key == KeyEvent.VK_LEFT) && (!rightDir)) {
				leftDir = true;
				upDir = false;
				downDir = false;
			}
			
			//What happens at right key
			if((key == KeyEvent.VK_RIGHT) && (!leftDir)) {
				rightDir = true;
				upDir = false;
				downDir = false;
			}
			
			//What happens at right key
			if((key == KeyEvent.VK_UP) && (!downDir)) {
				upDir = true;
				rightDir = false;
				leftDir = false;
			}
			
			//what happens at down key
			if((key == KeyEvent.VK_DOWN) && (!upDir)) {
				downDir = true;
				rightDir = false;
				leftDir = false;
			}
			
		}
	}
	

}
