import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;


public class Grab {
	
	private float radius;
	private int scroll;
	private int frameIncr = 0;
	
	public Grab() {
	}	
	
	public void doGrab(Robot rob, Frame frame) {
		if (!frame.hands().empty()) {
			// Get the first hand
            Hand hand = frame.hands().get(0);
            radius = hand.sphereRadius();
			
            frameIncr++;
			if (frameIncr == 5) {
				frameIncr = 0;
				radius = hand.sphereRadius();
				rob.keyPress(KeyEvent.VK_CONTROL);
				rob.mouseWheel(scroll);
				rob.keyRelease(KeyEvent.VK_CONTROL);
			}
			
            float newradius = hand.sphereRadius();
            System.out.println(newradius);
            float change = newradius - radius;
            
            if (change > 1.5) {
            	scroll = 1;
            } else if (change < -1.5) {
            	scroll = -1;
            }
            
            scroll = 0;
		}
	}

}
