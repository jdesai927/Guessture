
public enum Dir {
	UP, DOWN, LEFT, RIGHT;
	
	public Dir opposite() {
		switch (this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		}
		return null;
	}
}
