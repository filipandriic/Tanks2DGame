package game;

import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class Mreza extends Panel implements Runnable {
	
	private Thread myThread;
	private Polje terrain[][];
	private Igrac player;
	private ArrayList<Novcic> coins;
	private ArrayList<Tenk> tanks;
	private int rows;
	private int columns;
	boolean inGame;
	private Igra game;
	boolean flag = true;
	private int numOfPoints;
	private long startTime;
	private long endTime;
	
	public Mreza(Igra game) {
		rows = 17;
		columns = 17;
		this.game = game;
		inGame = false;
		
		
		
		this.setBackground(Color.GREEN);
		this.addComponents();
		
		
	}
	
	public Mreza(int rows, int columns, Igra game) {
		this.rows = rows;
		this.columns = columns;
		this.game = game;
		inGame = false;
		
		
		
		
		this.setBackground(Color.GREEN);
		this.addComponents();
	}
	
	public void addComponents() {
		this.setLayout(new GridLayout(rows, columns));
		
		terrain = new Polje[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				double help = Math.random();
				if (help <= 0.2) {
					terrain[i][j] = new Zid(this);
				} else {
					terrain[i][j] = new Trava(this);
				}
				this.add(terrain[i][j]);
			}
		}
		this.setFocusable(true);

		
		this.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent ke) {
				
				if (!Mreza.this.inGame) return;
				Polje next;
				if (ke.getKeyCode() == KeyEvent.VK_A) {
					next = player.field.getField(0, -1);
					if (next != null && !(next instanceof Zid)) {
						player.field.repaint();
						player.moveFigure(next);
					}
					
				}
				if (ke.getKeyCode() == KeyEvent.VK_D) {
				
					next = player.field.getField(0, 1);
					if (next != null && !(next instanceof Zid)) {
						player.field.repaint();
						player.moveFigure(next);
					}
				
				}
				if (ke.getKeyCode() == KeyEvent.VK_W) {
				
					next = player.field.getField(-1, 0);
					if (next != null && !(next instanceof Zid)) {
						player.field.repaint();
						player.moveFigure(next);
					}
				
				}
				if (ke.getKeyCode() == KeyEvent.VK_S) {
					
					next = player.field.getField(1, 0);
					if (next != null && !(next instanceof Zid)) {
						player.field.repaint();
						player.moveFigure(next);
					}
					
				}
				
			}
		});
	}
	
	public int[] getPosition(Polje p) {
		int ret[] = new int[2];
		
		boolean exists = false;
		iter: 
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (p == terrain[i][j]) {
					exists = true;
					ret[0] = i;
					ret[1] = j;
					break iter;
				}
			}
		}
		
		if (exists) {
			return ret;
		} else {
			return null;
		}
	}
	
	public Polje getField(Polje field, int x, int y) {
		int[] pos1 = field.getPosition();
		if (pos1[0] + x < 0 || pos1[1] + y < 0) return null;
		if (pos1[0] + x >= rows || pos1[1] + y >= columns) return null;
		return terrain[pos1[0] + x][pos1[1] + y];
	}
	
	public void changeField(Polje p) {
		if (game.editMode) {
			f:
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (p == terrain[i][j]) {
						this.remove(i * rows + j);				
						if (game.grassChange) {
							terrain[i][j] = new Trava(this);
						}
						else {
							terrain[i][j] = new Zid(this);
						}
						
						this.add(terrain[i][j], i * rows + j);
						this.revalidate();
						
						
						break f;
					}
				}
			}
		}
	}
	
	
	public synchronized void start() {
		inGame = true;
		numOfPoints = 0;
		myThread = new Thread(this);
		myThread.start();
		startTime = System.currentTimeMillis();
		for (Tenk t : tanks) {
			t.start();
		}
	}
	
	public synchronized void stop() {
		endTime = System.currentTimeMillis();
		double allTime = (endTime - startTime) / 1000;
		System.out.println(allTime + " sekundi");
		inGame = false;
		if (myThread == null) return;
		myThread.interrupt();
		for (Tenk t : tanks) {
			t.stop();
			t.field.repaint();
		}
		for (Novcic n : coins) {
			n.field.repaint();
		}
		
		
		
		coins.clear();
		player.field.repaint();
		
		myThread = null;
		System.out.println("Game stopped.");
	}
	
	@Override
	public void run() {
		try {
			while (!myThread.interrupted()) {
				myThread.sleep(40);
				
				player.draw();
				
				synchronized(tanks) {
					for (Tenk t : tanks) {
						t.draw();
					}
				}
				
				synchronized(tanks) {
					for (Tenk t : tanks) {		
						if (player.sameField(t)) {
							this.stop();
						}
						
					}
				}
						
				synchronized(coins) {
					for (Novcic n : coins) {
						n.draw();
					}
				}
				
				for (Novcic n : coins) {		
					if (player.sameField(n)) {
						coins.remove(n);
						n.field.repaint();
						
						numOfPoints++;
						game.setNumPoints(numOfPoints);
						if (coins.isEmpty()) {
							this.stop();
						}
						break;
					}
					
				}
				
					
					
				
				
			
			}
		}
		catch(InterruptedException e) {
			
		}
		
	}

	public Igrac getPlayer() {
		return player;
	}

	public ArrayList<Novcic> getCoins() {
		return coins;
	}


	public ArrayList<Tenk> getTanks() {
		return tanks;
	}

	public void initialize(int numCoins) {
		coins = new ArrayList<Novcic>();
		tanks = new ArrayList<Tenk>();
		
		int freeSpace[][] = new int[rows][columns];
		
		
		for (int i = 0; i < numCoins; i++) {
			int xRand;
			int yRand;
			do {
				xRand = (int) (Math.random() * rows);
				yRand = (int) (Math.random() * columns);
			} while ((terrain[xRand][yRand] instanceof Zid) || (freeSpace[xRand][yRand] != 0));
			
			freeSpace[xRand][yRand] = 1;
			Novcic n = new Novcic(terrain[xRand][yRand]);
			coins.add(n);
		}
		
		freeSpace = new int[rows][columns];
		for (int i = 0; i < numCoins / 3; i++) {
			int xRand;
			int yRand;
			
			do {
				xRand = (int) (Math.random() * rows);
				yRand = (int) (Math.random() * columns);
			} while ((terrain[xRand][yRand] instanceof Zid) || (freeSpace[xRand][yRand] != 0));
			
			freeSpace[xRand][yRand] = 1;
			Tenk t = new Tenk(terrain[xRand][yRand]);
			tanks.add(t);
		}
		
		freeSpace = new int[rows][columns];
		int xRand;
		int yRand;
		do {
			xRand = (int) (Math.random() * rows);
			yRand = (int) (Math.random() * columns);
		} while ((terrain[xRand][yRand] instanceof Zid) || (freeSpace[xRand][yRand] != 0));
		
		freeSpace[xRand][yRand] = 1;
		player = new Igrac(terrain[xRand][yRand]);
	}

}
