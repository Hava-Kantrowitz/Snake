import java.util.Scanner;

public class Console {

	public static void main(String[] args) {
		
		final int UP = 1;
		final int DOWN = 2;
		final int RIGHT = 3;
		final int LEFT = 4;
		
		Snake mySnake = new Snake(5, 5, 5, 4, 6, 4, UP);
		Game newGame = new Game(10, 10, mySnake);//height, then length
		newGame.render();
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		if (input == "w") {
			mySnake.take_step(UP);
		}
		else if (input == "s") {
			mySnake.take_step(DOWN);
		}
		else if (input == "d") {
			mySnake.take_step(RIGHT);
		}
		else if(input == "a") {
			mySnake.take_step(LEFT);
		}
		newGame.render();

	}

}
