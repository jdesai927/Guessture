import java.awt.Robot;

import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import java.util.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Keyboard {

	private final int WHITE_KEY_WIDTH = 24;
	private final int WHITE_KEY_LENGTH = 70;
	private final int BLACK_KEY_LENGTH = 50;
	private final int BLACK_KEY_WIDTH = 15;
	private final int KEY_START = -150;
	
	protected HashMap<Integer, Rectangle> _keyboard;
	protected HashMap<Integer, Boolean> _keysPressed;
	protected Integer currentKey;
	protected float x;
	protected float z;
	
	public Rectangle makeKey(boolean white, int startX, int startZ) {
		if (white) {
			return new Rectangle(startX, startZ, WHITE_KEY_WIDTH, WHITE_KEY_LENGTH);
		} else {
			return new Rectangle(startX, startZ, BLACK_KEY_WIDTH, BLACK_KEY_LENGTH);
		}
	}
	
	Keyboard() {
		_keysPressed = new HashMap<Integer, Boolean>();
		_keyboard = new HashMap<Integer, Rectangle>();
		/*Keys*/
		
		_keyboard.put(new Integer(KeyEvent.VK_W), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (2 * WHITE_KEY_WIDTH/3)), (KEY_START - BLACK_KEY_LENGTH)));
		_keysPressed.put(new Integer(KeyEvent.VK_W), false);
		_keyboard.put(new Integer(KeyEvent.VK_W), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (3 * WHITE_KEY_WIDTH/3) + 1 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
		_keysPressed.put(new Integer(KeyEvent.VK_E), false);
		_keyboard.put(new Integer(KeyEvent.VK_W), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (7 * WHITE_KEY_WIDTH/3) + 2 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
		_keysPressed.put(new Integer(KeyEvent.VK_T), false);
		_keyboard.put(new Integer(KeyEvent.VK_W), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (8 * WHITE_KEY_WIDTH/3) + 3 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
		_keysPressed.put(new Integer(KeyEvent.VK_Y), false);
		_keyboard.put(new Integer(KeyEvent.VK_W), makeKey (false, (int) ((-4 * WHITE_KEY_WIDTH) + (9 * WHITE_KEY_WIDTH/3) + 4 * BLACK_KEY_WIDTH), (KEY_START - BLACK_KEY_LENGTH)));
		_keysPressed.put(new Integer(KeyEvent.VK_U), false);
		
		_keyboard.put(new Integer(KeyEvent.VK_A), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (0 * WHITE_KEY_WIDTH)), KEY_START));
		System.out.println((int) ((-4 * WHITE_KEY_WIDTH) + (0 * WHITE_KEY_WIDTH)));
		_keysPressed.put(new Integer(KeyEvent.VK_A), false);
		_keyboard.put(new Integer(KeyEvent.VK_S), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (1 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_S), false);
		_keyboard.put(new Integer(KeyEvent.VK_D), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (2 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_D), false);
		_keyboard.put(new Integer(KeyEvent.VK_F), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (3 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_F), false);
		_keyboard.put(new Integer(KeyEvent.VK_G), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (4 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_G), false);
		_keyboard.put(new Integer(KeyEvent.VK_H), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (5 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_H), false);
		_keyboard.put(new Integer(KeyEvent.VK_J), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (6 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_J), false);
		_keyboard.put(new Integer(KeyEvent.VK_K), makeKey (true, (int) ((-4 * WHITE_KEY_WIDTH) + (7 * WHITE_KEY_WIDTH)), KEY_START));
		_keysPressed.put(new Integer(KeyEvent.VK_K), false);
	}

	public void doKeyPress(Robot rob, Frame frame) {
		boolean b = false;
		for (Finger f : frame.fingers()) {
			if (f.tipPosition().getY() < 100 && Helpers.closeTo(f.tipVelocity(), Dir.DOWN, 20)){
				b = true;
				x = f.tipPosition().getX();
				z = f.tipPosition().getZ();
				System.out.println(x);
				System.out.println(z);
			}
			if (b) {
				System.out.println("below Y!");
				break;
			}
		}
		//if (b){
			for (Integer i : _keyboard.keySet()){
				if (b && _keyboard.get(i).contains(x,z)){
					System.out.println("Found key!");
					if (!_keysPressed.get(i)) {
						rob.keyPress(i);
						_keysPressed.put(i, true);
					}
					break;
				} else {
					if (_keysPressed.get(i)) {
						rob.keyRelease(i);
						_keysPressed.put(i, false);
					}	
				}
			//}
			
		}
	}

}
