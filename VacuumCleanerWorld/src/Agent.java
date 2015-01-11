import java.util.Random;


public class Agent {
	private Environment e = null;

	private boolean touchSensor;
	private boolean dirtSensor;
	private boolean homeSensor;
	private Random rand = new Random();
	
	/**
	 * initialize the environment and the sensors
	 * @param e the environment
	 */
	public Agent(Environment e) {
		this.e = e;
		touchSensor = false;
		dirtSensor = senseDirt();
		homeSensor = senseHome();
	}
	
	public void run() {
		while(Main.score > -500) {
			if(dirtSensor) {	// if there is dirt, suck up the dirt
				suckUp();
			} else if(touchSensor) {	// if there is an obstacle on the next square, randomly decide in which direction to turn
				turn();
			} else if(homeSensor) { // if the agent is at home, 
				return;				// turn off
			} else { 		// randomly decide whether to turn or go forward
				int x = rand.nextInt(2);
				if(x == 0) {
					turn();
				} else {
					goForward();
				}
			}
			Main.score--;	// every action costs 1
//			e.prettyPrint();
			System.out.println("Score: " + Main.score);
		}
	}
	
	public void goForward() {
		touchSensor = senseTouch();
			if(!touchSensor) {
				System.out.println("Moving forward");
				e.goForward(); // move
				// check sensors
				dirtSensor = senseDirt();
				homeSensor = senseHome();
			} else {
				System.out.println("Bumped into obstacle");
			}
	}
	
	public void turn() {
		int randDir = rand.nextInt(2);
		if(randDir == 0) {
			e.turnDirectionLeft();
			System.out.println("Turning left 90 degrees to avoid obstacles");
		} else {
			e.turnDirectionRight();
			System.out.println("Turning right 90 degrees to avoid obstacles");
		}
		touchSensor = false;
	}
	
	public void suckUp() {
		System.out.println("Sucking up the dirt");
		Main.score = Main.score + 100;	// score +100 for cleaning
		e.getCurrentSquare().cleanSquare();
		dirtSensor = false;
	}
	
	public boolean senseTouch() {
		return e.getNextSquare().hasObstacle();
	}
	
	public boolean senseDirt() {
		return e.getCurrentSquare().isDirty();
	}
	
	public boolean senseHome() {
		return e.getCurrentSquare().isBase();
	}
	
	
}
