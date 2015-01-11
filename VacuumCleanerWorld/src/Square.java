
public class Square {
	private boolean isDirty;
	private boolean hasObstacle;
	private boolean isBase;
	
	public Square(boolean isDirty, boolean hasObstacle, boolean isBase) {
		super();
		this.isDirty = isDirty;
		this.hasObstacle = hasObstacle;
		this.isBase = isBase;
	}
	
	public void cleanSquare() {
		isDirty = false;
	}

	public boolean isDirty() {
		return isDirty;
	}

	public boolean hasObstacle() {
		return hasObstacle;
	}

	public boolean isBase() {
		return isBase;
	}

	
}
