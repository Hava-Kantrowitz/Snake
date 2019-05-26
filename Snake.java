import java.util.LinkedList;

public class Snake {
	
	int orig_x1;
	int orig_y1;
	int orig_x2;
	int orig_y2;
	int orig_x3;
	int orig_y3;
	int orig_dir; //1 is up, 2 is down, 3 is right, 4 is left
	Integer[] orig_coords1 = {orig_x1, orig_y1};
	Integer[] orig_coords2 = {orig_x2, orig_y2};
	Integer[] orig_coords3 = {orig_x3, orig_y3};
	LinkedList<Integer[]> snake = new LinkedList<Integer[]>();
	
	/*
	 * Creates the snake
	 * @param orig_x: the original x coordinate
	 * @param orig_y: the original y coordinate
	 * @param orig_dir: the original direction
	 */
	public Snake(int x1, int y1, int x2, int y2, int x3, int y3, int orig_dir) {
		this.orig_x1 = x1;
		this.orig_y1 = y1;
		this.orig_x2 = x2;
		this.orig_y2 = y2;
		this.orig_x3 = x3;
		this.orig_y3 = y3;
		this.orig_dir = orig_dir;
		snake.add(orig_coords1);
		snake.add(orig_coords2);
		snake.add(orig_coords3);
	}
	
	public void take_step(int dir) {
		Integer[] currSeg = findHead();
		int new_x = 0;
		int new_y = 0;
		if (dir == 1) {//go up
			new_x = currSeg[0];
			new_y = currSeg[1] + 1;
		}
		else if (dir == 2) {//go down
			new_x = currSeg[0];
			new_y = currSeg[1] - 1;
		}
		else if (dir == 3) {//go right
			new_x = currSeg[0] + 1;
			new_y = currSeg[1];
		}
		else if(dir == 4) {//go left
			new_x = currSeg[0] - 1;
			new_y = currSeg[1];
		}
		Integer[] newSeg = {new_x, new_y};
		snake.add(newSeg);
		snake.removeFirst();
	}
	
	/*
	 * Sets the direction to a new direction
	 * @param newDir: the new direction
	 */
	public void setDirection(int newDir) {
		this.orig_dir = newDir;
	}
	
	public Integer[] findHead() {
		return snake.getFirst();
	}
	
	public Integer[] findButt() {
		return snake.getLast();
	}

}
