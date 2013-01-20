import com.leapmotion.leap.Vector;


public class Helpers {
	
	/**
	 * Checks if a vector is close enough to a given direction.
	 * @param v The vector being evaluated
	 * @param dir The direction being compared to
	 * @param eps The angle tolerance, in degrees
	 * @return Whether or not the vector is close to the direction as per the given tolerance.
	 */
	public static boolean closeTo(Vector v, Dir dir, float eps) {
		switch(dir) {
		case UP:
			return (v.angleTo(new Vector(0, 1, 0)) < eps);
		case DOWN:
			return (v.angleTo(new Vector(0, -1, 0)) < eps);
		case LEFT:
			return (v.angleTo(new Vector(-1, 0, 0)) < eps);
		case RIGHT:
			return (v.angleTo(new Vector(1, 0, 0)) < eps);
		}
		return false;
	}
	
}
