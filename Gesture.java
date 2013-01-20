import java.awt.Robot;

import com.leapmotion.leap.Frame;

public abstract class Gesture {

	protected int _numFingers;
	protected int _numFrames;
	protected int _currentFrames;
	protected boolean _ends;
	protected boolean _detected;
	protected int _id;
	
	public Gesture(int numFingers, int numFrames, boolean ends, int id) {
		_numFingers = numFingers;
		_numFrames = numFrames;
		_ends = ends;
		_currentFrames = 0;
		_id = id;
	}
	
	public abstract boolean detectedInFrame(Frame frame);
	
	public abstract boolean initialDetection(Frame frame);
	
	public boolean detect(Frame frame) {
		initialDetection(frame);
		if (!_detected) {
			return false;
		}
		if (detectedInFrame(frame)) {
			_currentFrames++;
		} else {
			_currentFrames = 0;
		}
		boolean b = false;
		if (_currentFrames >= _numFrames) {
			b = true;
			if (_ends) {
				_currentFrames = 0;
			}
		}
		return b;
	}
	
	public abstract void execute(Robot rob);
	
}
