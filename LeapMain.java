import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;

import com.leapmotion.leap.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;

class SampleListener extends Listener {
	public Robot rob;
	public boolean leftclick;
	public static int mode;
	public boolean _gestureOngoing;
	public boolean rightclick;
	public int leftheld;
	public Piano _piano;
	public Keyboard _keyboard;
	public Grab _grab;
	public ArrayList<Gesture> _gestures;
	public ArrayList<Gesture> _haloGestures;
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
        try {
			rob = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		leftclick = false;
		rightclick = false;
		_gestures = new ArrayList<Gesture>();
		_haloGestures = new ArrayList<Gesture>();
		_gestureOngoing = false;
		_keyboard = new Keyboard();
		_grab = new Grab();
		leftheld = 0;
		mode = 0;
		_gestures.add(new Swipe(2, 20, Dir.LEFT));
		_gestures.add(new Swipe(2, 20, Dir.RIGHT));
		_gestures.add(new Swipe(2, 15, Dir.UP));
		_gestures.add(new Swipe(2, 15, Dir.DOWN));
		_gestures.add(new Swipe(3, 30, Dir.DOWN));
		//_gestures.add(new Grab(0, 15));
		_piano = new Piano();
		
		
		
		
		//_gestures.add(new Swipe(2, 30, Dir.UP));
		//_gestures.add(new Swipe(2, 20));
		//_gestures.add(new Swipe(3, 20));
		//_gestures.add(new Pinch(2, 2));
    }	

    public void onConnect(Controller controller) {
        System.out.println("Connected");
    }

    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
    public void moveMouse(Frame frame, Screen screen) {
        if (!frame.hands().empty()) {
            // Get the first hand
            Hand hand = frame.hands().get(0);

            // Check if the hand has any fingers
            FingerList tools = hand.fingers();
            
            if (tools.count() >= 1) {
            	Vector vfing = tools.get(0).tipPosition();
            	vfing = screen.intersect(tools.get(0), true);
            	int dx = Math.round(vfing.getX() * screen.widthPixels());
        		int dy = screen.heightPixels() - (Math.round(vfing.getY() * screen.heightPixels()));
        		rob.mouseMove(dx,dy);
            }
        }
    }
    
    public void searchGestures(Frame f) {
    	for (Gesture g : _gestures) {
    		if (g.detect(f)) {
    			g.execute(rob);
    			//gestureOngoing = true;
    		}
    	}
    }
    
    public void mouseClick(Frame frame) {
    	
    	int totalf = 0;
        for (Finger f : frame.fingers()) {
        	if (f.tipPosition().getZ() < -50) {
        		totalf++;
        	}
        }
        if (totalf == 1) {
        	if (!leftclick && !rightclick) {
        		System.out.println("left click");
        		rob.mousePress(MouseEvent.BUTTON1_MASK);
        		leftclick = true;
        	}
        	leftheld++;
        } else if (totalf == 0) {
        	if (leftclick) {
        		rob.mouseRelease(MouseEvent.BUTTON1_MASK);
        		leftclick = false;
        		leftheld = 0;
        	}
        	if (rightclick) {
        		rob.mouseRelease(MouseEvent.BUTTON3_MASK);
        		rightclick = false;
        	}
        }
        
    }
    
    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic informations
	Frame frame = controller.frame();		
        Screen screen = controller.calibratedScreens().get(0);
        searchGestures(frame);
        if (mode == 1) {
        	_piano.doKeyPress(rob, frame);
        }
        if (mode == 0) {
        	mouseClick(frame);
        	if (!(leftclick && leftheld < 25)) {
        		moveMouse(frame, screen);
        	}   
        }
        if (mode == 2) {
        	_grab.doGrab(rob, frame);
        }
        if (mode == 3) {
        	mouseClick(frame);
        	if (!(leftclick && leftheld < 25)) {
        		moveMouse(frame, screen);
        	}
        }
        	
    }
}

class Sample {
    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();
        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
    }
}