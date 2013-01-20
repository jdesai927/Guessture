import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;


public class Grab extends Gesture {
	
	private float radius;
	private int scroll;
	private int frameIncr = 0;
	private Robot _rob;
	
	public Grab(int numFingers, int numFrames) {
		super(numFingers, numFrames, true, 3);
		try {
			_rob = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean detectedInFrame(Frame frame) {
		if (!frame.hands().empty()) {
			// Get the first hand
            Hand hand = frame.hands().get(0);
			
			frameIncr++;
			if (frameIncr == 7) {
				frameIncr = 0;
				radius = hand.sphereRadius();
				execute(_rob);
			}
			
            float newradius = hand.sphereRadius();
            float change = newradius - radius;
            
            //System.out.println(change);
            if (change > 5) {
            	scroll = 1;
            } else if (change < -2.5) {
            	scroll = -1;
            }
            
            //System.out.println(scroll);
            return true;
		}
		return false;
	}

	@Override
	public void execute(Robot rob) {
		rob.keyPress(KeyEvent.VK_CONTROL);
		rob.mouseWheel(scroll);
		rob.keyRelease(KeyEvent.VK_CONTROL);
	}

	@Override
	public boolean initialDetection(Frame frame) {
		if (_detected) return true;
		
		if (!frame.hands().empty()) {
            // Get the first hand
            Hand hand = frame.hands().get(0);
            radius = hand.sphereRadius();
            _detected = true;
            return true;
		}
		return true;
	}

}
