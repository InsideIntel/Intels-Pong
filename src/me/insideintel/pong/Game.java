package me.insideintel.pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
 /*
 Author: InsideIntel
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public final static int WIDTH = 650;
	public final static int HEIGHT = WIDTH * 9 / 16;

	public boolean running = false;

	private Thread gameThread;
	private Ball ball;
	private Paddle leftPaddle;
	private Paddle rightPaddle;

	private Menu menu;
	public Game() {

		canvasSetup();

		new Window("Intel's Pong", this);

		initialise();

		this.addKeyListener(new KeyInput(leftPaddle, rightPaddle));
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		this.setFocusable(true);

	}
	private void initialise() {
		ball = new Ball();

		leftPaddle = new Paddle(new Color(0,250,154), true);
		rightPaddle = new Paddle(new Color(0,250,154), false);

		menu = new Menu(this);
	}

	private void canvasSetup() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void run() {

		this.requestFocus();

		long lastTime = System.nanoTime();
		double amountOfTicks = 90.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				update();
				delta--;
				draw();
			}
		}

		stop();
	}

	public synchronized void start() {
		gameThread = new Thread(this);
		gameThread.start(); // start thread
		running = true;
	}
	public void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void draw() {
		BufferStrategy buffer = this.getBufferStrategy();
		if (buffer == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = buffer.getDrawGraphics();
		drawBackground(g);

		if (menu.active)
			menu.draw(g);

		ball.draw(g);

		leftPaddle.draw(g);
		rightPaddle.draw(g);
		g.dispose();
		buffer.show();

	}
	private void drawBackground(Graphics g) {
		g.setColor(new Color(50,205,50));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);
	}
	public void update() {

		if (!menu.active) {
			ball.update(leftPaddle, rightPaddle);
			leftPaddle.update(ball);
			rightPaddle.update(ball);
		}
	}
	public static int ensureRange(int value, int min, int max) {
		return Math.min(Math.max(value, min), max);
	}
	public static int sign(double d) {
		if (d <= 0)
			return -1;

		return 1;
	}

	public static void main(String[] args) {
		new Game();
	}

}
