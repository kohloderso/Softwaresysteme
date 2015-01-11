
public class Main {

	public static int score;
	
	public static void main(String[] args) {
		Environment e = new Environment();
		Agent a = new Agent(e);
		a.run();
		
		// deduct 1000 points if the robot isn't home
		if(!e.checkHome()) {
			score = score - 1000;
		}
		e.prettyPrint();
		System.out.println("Final score: " + score);
	}
}
