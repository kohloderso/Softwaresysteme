import java.util.Random;

public class Environment {

	private Square[][] grid;
	private int agentPosX;
	private int agentPosY;
	private Direction agentDirection;

	/**
	 * Create a vacuum-cleaner world consisting of a at least 5 x 5 grid of
	 * squares, at least 8 squares contain obstacles and at most 2 squares will
	 * contain dirt. Start the vacuum-agent from upper-left corner facing right.
	 */
	public Environment() {
		int size = 10;
		grid = new Square[size][size];
		// pad the grid with a border of obstacles
		for (int i = 0; i < size; i++) {
			grid[i][0] = new Square(false, true, false);
			grid[0][i] = new Square(false, true, false);
			grid[i][size-1] = new Square(false, true, false);
			grid[size-1][i] = new Square(false, true, false);
		}
		// place 8 obstacles in the grid
		int obstacleCounter = 0;
		Random rand = new Random();
		while(obstacleCounter < 8) {
			int i = rand.nextInt(size-1);
			int j = rand.nextInt(size-1);
			if(grid[i][j] == null) {
				grid[i][j] = new Square(false, true, false);
				obstacleCounter++;
			}
		}
		// make at 2 squares in the grid dirty
		int dirtCounter = 0;
		while(dirtCounter < 5) {
			int i = rand.nextInt(size-1);
			int j = rand.nextInt(size-1);
			if(grid[i][j] == null) {
				grid[i][j] = new Square(true, false, false);
				dirtCounter++;
			}
		}
		// place homebase in the grid
		boolean success = false;
		while(!success) {
			int i = rand.nextInt(size-1);
			int j = rand.nextInt(size-1);
			if(grid[i][j] == null) {
				grid[i][j] = new Square(false, false, true);
				success = true;
			}
		}
		// fill up the rest of the grid, with regular squares
		for(int i = 1; i < size-1; i++) {
			for(int j = 1; j < size-1; j++) {
				if(grid[i][j] == null) {
					grid[i][j] = new Square(false, false, false);
				}
			}
		}
		// place the agent in the upper left corner facing right
		agentPosX = 1;
		agentPosY = size-2;
		agentDirection = Direction.E;
		
		// let's see the result :)
		prettyPrint();
	}

	public Square getCurrentSquare() {
		return grid[agentPosX][agentPosY];
	}

	public void goForward() {
		if (!getNextSquare().hasObstacle()) {
			switch (agentDirection) {
			case N:
				agentPosY++; break; // agent is facing upwards
			case E:
				agentPosX++; break; // agent is facing right
			case S:
				agentPosY--; break; // agent is facing down
			case W:
				agentPosX--; break; // agent is facing left
			}
		}
	}

	public Square getNextSquare() {
		switch (agentDirection) {
		case N:
			return grid[agentPosX][agentPosY + 1]; // agent is facing upwards
		case E:
			return grid[agentPosX + 1][agentPosY]; // agent is facing right
		case S:
			return grid[agentPosX][agentPosY - 1]; // agent is facing down
		case W:
			return grid[agentPosX - 1][agentPosY]; // agent is facing left
		default:
			return null;
		}
	}

	public void turnDirectionLeft() {
		agentDirection = Direction.values()[(agentDirection.ordinal() - 1 + 4) % 4];
	}

	public void turnDirectionRight() {
		agentDirection = Direction.values()[(agentDirection.ordinal() + 1) % 4];
	}

	public boolean checkHome() {
		return getCurrentSquare().isBase();
	}
	
	/**
	 * Print the grid on standard output. R is the position of the robot 1
	 * symbols dirt + is an obstacle 0 is nothing special
	 */
	public void prettyPrint() {	
		for (int j = grid[0].length-1; j >= 0; j--) {
			for (int i = 0; i < grid.length; i++) {
				if (agentPosX == i && agentPosY == j)
					System.out.print("\t"+agentDirection.name());
				else if (grid[i][j].hasObstacle())
					System.out.print("\t+");
				else if (grid[i][j].isDirty())
					System.out.print("\t1");
				else if(grid[i][j].isBase())
					System.out.print("\tH");
				else
					System.out.print("\t0");
			}
			System.out.print("\n");
		}
	}

	enum Direction {
		N, E, S, W;
	}
}
