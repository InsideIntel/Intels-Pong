package me.insideintel.pong;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/*
 Author: InsideIntel
 */
public class KeyInput extends KeyAdapter {

	private Paddle lp;
	private boolean lup = false;
	private boolean ldown = false;

	private Paddle rp;
	private boolean rup = false;
	private boolean rdown = false;

	public KeyInput(Paddle p1, Paddle p2) {

		lp = p1;
		rp = p2;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_UP:
				rp.switchDirections(-1);
				rup = true;
				break;
			case KeyEvent.VK_DOWN:
				rp.switchDirections(1);
				rdown = true;
				break;
			case KeyEvent.VK_W:
				lp.switchDirections(-1);
				lup = true;
				break;
			case KeyEvent.VK_S:
				lp.switchDirections(1);
				ldown = true;
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(1);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		switch(key) {
			case KeyEvent.VK_UP:
				rup = false;
				break;
			case KeyEvent.VK_DOWN:
				rdown = false;
				break;
			case KeyEvent.VK_W:
				lup = false;
				break;
			case KeyEvent.VK_S:
				ldown = false;
				break;
		}
		if (!lup && !ldown)
			lp.stop();
		if (!rup && !rdown)
			rp.stop();
	}

}
