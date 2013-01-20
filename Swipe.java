import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.leapmotion.leap.*;

public class Swipe extends Gesture {
	
	public Swipe(int numFingers, int numFrames) {
		super(numFingers, numFrames, true, 2);
	}
	
	public boolean detectedInFrame(Frame frame) {
		
		int totalf = 0;
    	for (Finger f : frame.fingers()) {
    		if (f.tipVelocity().getX() < -50) {
    			totalf++;
    		}
    	}
    	return (totalf >= _numFingers);
    	
	}

	@Override
	public void execute(Robot rob) {
		System.out.println("Switch windows");
		rob.keyPress(KeyEvent.VK_WINDOWS);
		rob.keyPress(KeyEvent.VK_TAB);
		rob.keyRelease(KeyEvent.VK_TAB);
		rob.keyRelease(KeyEvent.VK_WINDOWS);
	}

	@Override
	public boolean initialDetection(Frame frame) {
		// TODO Auto-generated method stub
		_detected = true;
		return false;
	}
}
