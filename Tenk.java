package game;
import java.awt.*;

public class Tenk extends Figura implements Runnable {
	private Thread myThread;
	private enum toMove { UP, DOWN, LEFT, RIGHT };

	public Tenk(Polje field) {
		super(field);
	}

	@Override
	public void draw() {
		Graphics g = field.getGraphics();
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, field.getWidth(), field.getHeight());
		g.drawLine(field.getWidth(), 0, 0, field.getHeight());
	}
	
	public synchronized void start() {
		myThread = new Thread(this);
		myThread.start();
	}
	
	public synchronized void stop() {
		if (myThread == null) return;
		myThread.interrupt();
		myThread = null;
		
	}

	@Override
	public void run() {
		try {
			while(!myThread.isInterrupted()) {
				myThread.sleep(500);
				int move = (int) (Math.random() * 4);
				Polje next;
				if (move == toMove.UP.ordinal()) {
					next = field.getField(1, 0);
				} else if (move == toMove.DOWN.ordinal()) {
					next = field.getField(-1, 0);
				} else if (move == toMove.LEFT.ordinal()) {
					next = field.getField(0, -1);
				} else {
					next = field.getField(0, 1);
				}
				
				if (next != null && !(next instanceof Zid)) {
					this.field.repaint();
					this.moveFigure(next);
				}
		
			}
		} catch (InterruptedException e) {
			
		}
	}

}
