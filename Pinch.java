import java.awt.Robot;
import java.awt.event.KeyEvent;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Vector;


public class Pinch extends Gesture {
	
	private Frame _firstFrame;
	private float _lastScale;
	
	public Pinch(int numFingers, int numFrames) {
		super(numFingers, numFrames, true, 3);
		_lastScale = 1.0f;
	}

	public boolean isMovingTowards(Finger f1, Finger f2) {
		Vector dir = f2.tipPosition().minus(f1.tipPosition());
		return (dir.angleTo(f1.tipVelocity()) < 10);
	}
	
	@Override
	public boolean detectedInFrame(Frame frame) {
		if (!frame.hands().empty()) {
            // Get the first hand
            Hand hand = frame.hands().get(0);
            float scale = _lastScale;
            _lastScale = hand.scaleFactor(_firstFrame);
            //System.out.println(hand.sphereRadius());
            //System.out.println(_lastScale);
            return (_lastScale < scale);
		}
		return false;
//		boolean b = false;
//		for (Hand h : frame.hands()) {
//			if (h.fingers().count() < 2) {
//				continue;
//			}
//			Finger f1 = h.fingers().get(0);
//			Finger f2 = h.fingers().get(1);
//			b = b || (isMovingTowards(f1, f2) && isMovingTowards(f2, f1));
//		}
		
	}

	@Override
	public void execute(Robot rob) {
//		rob.keyPress(KeyEvent.VK_CONTROL);
//		rob.keyPress(KeyEvent.VK_MINUS);
//		rob.keyRelease(KeyEvent.VK_MINUS);
//		rob.keyRelease(KeyEvent.VK_CONTROL);
	}

	@Override
	public boolean initialDetection(Frame frame) {
		if (_detected) {
			return true;
		}
		_firstFrame = frame;
		_detected = true;
		return true;
	}

}
