import java.util.ArrayList;

public class Game {
	
	int height;
	int width;
	Snake snake;
	
	/*
	 * Constructs the game board
	 * @param height: board height
	 * @param width: board width
	 */
	public Game(int height, int width, Snake snake) {
		this.height = height;
		this.width = width;
		this.snake = snake;
	}
	
	/*
	 * Creates the matrix
	 * @return the created matrix
	 */
	public char[][] make_matrix(){
		char[][] board = new char[width][height];
		board[snake.orig_x1][snake.orig_y1] = 'X';
		board[snake.orig_x2][snake.orig_y2] = 'O';
		board[snake.orig_x3][snake.orig_y3] = 'O';
		return board;
	}
	
	/*
	 * Renders the matrix, including with a border
	 */
	public void render() {
		char[][] board = make_matrix();
		for (int i = 0; i < width + 2; i++) {
			System.out.print("-");
		}
		
		for(char[] x : board) {
			System.out.println();
			System.out.print("|");
			for(char y : x) {
				System.out.print(y);
			}
			System.out.print("|");
			System.out.println();
		}
		
		for (int i = 0; i < width + 2; i++) {
			System.out.print("-");
		}
	}

}
