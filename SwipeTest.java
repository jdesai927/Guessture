import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.leapmotion.leap.*;

public class SwipeTest extends Gesture {
	
	private int fingers;
	
	private Dir dir;
	
	public SwipeTest(int numFingers, int numFrames, Dir _dir) {
		super(numFingers, numFrames, true, 2);
		dir = _dir;
	}
	
	public boolean detectedInFrame(Frame frame) {
		/*
		boolean b = false;
		for (Hand hand : frame.hands()) {
			b = b || ((hand.fingers().count() > 1) && (hand.palmVelocity().getX() < -50));
		}
		return b;
		*/
		
		int totalf = 0;
		for (Finger f : frame.fingers()) {
    		float swipeX = f.tipVelocity().getX();
    		float swipeY = f.tipVelocity().getY();
    		if (swipeX < -50 && dir == Dir.LEFT) {
    			totalf++;
    		}
    		if (swipeX > 50 && dir == Dir.RIGHT) {
    			totalf++;
    		}
    		if (swipeY < -75 && dir == Dir.DOWN) {
    			totalf++;
    		}
    		if (swipeY > 50 && dir == Dir.UP) {
    			totalf++;
    		}
    	}
    	
    	fingers = totalf;
    	_detected = (totalf == _numFingers);
    	return (totalf == _numFingers);
    	
	}
	
	private void switchWindow(Robot rob) {
		System.out.println("Switch windows (BACKWARDS!)");
		rob.keyPress(KeyEvent.VK_WINDOWS);
		rob.keyPress(KeyEvent.VK_SHIFT);
		
		rob.keyPress(KeyEvent.VK_TAB);
		rob.keyRelease(KeyEvent.VK_TAB);
		
		rob.keyPress(KeyEvent.VK_TAB);
		rob.keyRelease(KeyEvent.VK_TAB);
		
		rob.keyRelease(KeyEvent.VK_SHIFT);
		rob.keyRelease(KeyEvent.VK_WINDOWS);
	}
	
	private void openCharms(Robot rob) {
		System.out.println("Charms");
		rob.keyPress(KeyEvent.VK_WINDOWS);
		rob.keyPress(KeyEvent.VK_C);
		
		rob.keyRelease(KeyEvent.VK_C);
		rob.keyRelease(KeyEvent.VK_WINDOWS);
		
	}
	
	private void closeWindow(Robot rob) {
		System.out.println("Close Window");
		rob.keyPress(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_F4);
		
		rob.keyRelease(KeyEvent.VK_ALT);
		rob.keyRelease(KeyEvent.VK_F4);
	}
	
	private void openSettings(Robot rob) {
		System.out.println("Open Settings");
		rob.mousePress(MouseEvent.BUTTON3_MASK);
		rob.mouseRelease(MouseEvent.BUTTON3_MASK);
	}
	
	@Override
	public void execute(Robot rob) {
		if (fingers == 4) {
			SampleListener.mode++;
			if (SampleListener.mode == 2) {
				SampleListener.mode = 0;
			}
		}
		if (fingers == 3) {
			if (dir == Dir.UP || dir == Dir.LEFT) rob.mouseWheel(-3);
			else rob.mouseWheel(3);
			return;
		}
		
		if (fingers == 2) {
			switch (dir) {
				case UP: {
					openSettings(rob);
					break;
				}
				case DOWN: {
					closeWindow(rob);
					break;
				}
				case LEFT: {
					openCharms(rob);
					break;
				}
				case RIGHT: {
					switchWindow(rob);
					break;
				}
			}
		}
		return;
	}

	@Override
	public boolean initialDetection(Frame frame) {
		if (_detected) {
			return true;
		}
		int numfing = 0;
		boolean comp;
		boolean x;
		comp = !(dir == Dir.UP || dir == Dir.RIGHT);
		x = (dir == Dir.LEFT || dir == Dir.RIGHT);
		float val;
		for (Finger f : frame.fingers()) {
			if (x) {
				if (comp) {
					if (f.tipPosition().getX() > 40) {
						numfing++;
					}	
				} else {
					if (f.tipPosition().getX() < -40) {
						numfing++;
					}
				}
			} else {
				if (comp) {
					if (f.tipPosition().getY() > 270) {
						numfing++;
					}
				} else {
					if (f.tipPosition().getY() < 60) {
						numfing++;
					}
				}
			}
		}
		_detected = (numfing >= _numFingers);
		return _detected;
	}
}
