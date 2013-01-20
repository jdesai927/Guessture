import java.awt.Rectangle;
import java.awt.Robot;

	import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.sound.midi.*;
	
	
public class Piano {
		private final int WHITE_KEY_WIDTH = 30;
		private final int WHITE_KEY_LENGTH = 150;
		private final int BLACK_KEY_LENGTH = 50;
		private final int BLACK_KEY_WIDTH = 20;
		private final int KEY_START = -50;

		protected HashMap<Integer, Rectangle> _keyboard;
		protected HashMap<Integer, Boolean> _keysPressed;
		protected Integer currentKey;
		protected float x;
		protected float z;
		protected int velocity;
		protected Receiver device;
		private ShortMessage myMsg;
		
		
		public Rectangle makeKey(boolean white, int startX, int startZ) {
			if (white) {
				return new Rectangle(startX, startZ, WHITE_KEY_WIDTH, WHITE_KEY_LENGTH);
			} else {
				return new Rectangle(startX, startZ, BLACK_KEY_WIDTH, BLACK_KEY_LENGTH);
			}
		}
		
		Piano() {
			try {
				device = MidiSystem.getReceiver();
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			_keysPressed = new HashMap<Integer, Boolean>();
			_keyboard = new HashMap<Integer, Rectangle>();
			/*Keys*/

			/*
			_keyboard.put(new Integer(61), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (2 * WHITE_KEY_WIDTH/3)), (KEY_START - BLACK_KEY_LENGTH)));
			_keysPressed.put(new Integer(61), false);
			_keyboard.put(new Integer(63), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (3 * WHITE_KEY_WIDTH/3) + 1 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
			_keysPressed.put(new Integer(63), false);
			_keyboard.put(new Integer(66), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (7 * WHITE_KEY_WIDTH/3) + 2 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
			_keysPressed.put(new Integer(66), false);
			_keyboard.put(new Integer(68), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (8 * WHITE_KEY_WIDTH/3) + 3 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
			_keysPressed.put(new Integer(68), false);
			_keyboard.put(new Integer(70), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (9 * WHITE_KEY_WIDTH/3) + 4 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
			_keysPressed.put(new Integer(70), false);
			*/
			_keyboard.put(new Integer(60), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (0 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(60), false);
			_keyboard.put(new Integer(62), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (1 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(62), false);
			_keyboard.put(new Integer(64), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (2 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(64), false);
			_keyboard.put(new Integer(65), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (3 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(65), false);
			_keyboard.put(new Integer(67), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (4 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(67), false);
			_keyboard.put(new Integer(69), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (5 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(69), false);
			_keyboard.put(new Integer(71), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (6 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(71), false);
			_keyboard.put(new Integer(72), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (7 * WHITE_KEY_WIDTH)), KEY_START));
			_keysPressed.put(new Integer(72), false);
		}
		
		

		public void doKeyPress(Robot rob, Frame frame) {
			
			boolean b = false;
			for (Finger f : frame.fingers()) {
				if (f.tipPosition().getY() < 120) {// && Helpers.closeTo(f.tipVelocity(), Dir.DOWN, 20)){
					b = true;
					x = f.tipPosition().getX();
					z = f.tipPosition().getZ();
					velocity = (int) f.tipVelocity().magnitude();
				}
				if (b) {
					//System.out.println("below Y!");
					break;
				}
			}
			//if (b){
				for (Integer i : _keyboard.keySet()){
					if (b && _keyboard.get(i).contains(x,z)){
						if (!_keysPressed.get(i)) {
							/*Generate the Note*/
							ShortMessage note = new ShortMessage ();
							
							try {
								note.setMessage(ShortMessage.NOTE_ON, 1, i, Math.min(Math.max(velocity, 40), 125));
							} catch (InvalidMidiDataException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							/*Send the note*/
							long timeStamp = -1;
							device.send(note, timeStamp);	
							_keysPressed.put(i, true);
						}
						break;
					} else {
						if (_keysPressed.get(i)) {
							//rob.keyRelease(i);
							_keysPressed.put(i, false);
						}	
					}
				//}
				
			}
		}

	}



