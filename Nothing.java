import java.awt.Robot;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;


public class Nothing extends Gesture {

	public boolean mode;
	
	public Nothing() {
		super(0, 5, true, -1);
		mode = false;
	}

	@Override
	public boolean detectedInFrame(Frame frame) {
		for (Finger f : frame.fingers()) {
			if (f.tipVelocity().magnitude() > 10) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void execute(Robot rob) {
		
	}

	@Override
	public boolean initialDetection(Frame frame) {
		// TODO Auto-generated method stub
		return false;
	}

}
