package game;

import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame {

	boolean gameMode;
	boolean editMode;
	private Mreza terrain;
	private Label numPoints;
	boolean grassChange;
	
	
	public Igra() {
		super("Game");
		this.setSize(800, 600);
		this.setResizable(false);
		this.addComponents();
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent ev) {
				
				if (terrain.inGame) {
					terrain.stop();
				}
				System.out.println("Game exitted.");
				dispose();
			}
		});
		
		this.setVisible(true);
	}
	
	public void addComponents() {
		Button startGame = new Button("Pocni");
		terrain = new Mreza(this);
		this.add(terrain, BorderLayout.CENTER);
		
		Panel panel = new Panel();
		panel.setLayout(new GridLayout(1, 2));
		
		Label l = new Label("Podloga: ");
		panel.add(l, BorderLayout.WEST);
		
		Panel grassWall = new Panel();
		grassWall.setLayout(new GridLayout(2, 1));
		
		CheckboxGroup group = new CheckboxGroup();
		Checkbox grass = new Checkbox("Trava", false, group);
		Checkbox wall = new Checkbox("Zid", true, group);
		grass.setBackground(Color.GREEN);
		wall.setBackground(Color.GRAY);
		
		grass.addItemListener((ev) -> {
			grassChange = true;
		});
		wall.addItemListener((ev) -> {
			grassChange = false;
		});
		
		grassWall.add(grass);
		grassWall.add(wall);
		panel.add(grassWall, BorderLayout.EAST);
		
		this.add(panel, BorderLayout.EAST);
		
		MenuBar menu = new MenuBar();
		Menu mode = new Menu("Rezim");
		MenuItem editModeMenu = new MenuItem("Rezim izmena");
		MenuItem gameModeMenu = new MenuItem("Rezim igranje");
		
		gameModeMenu.addActionListener((ev) -> {
			gameMode = true;
			editMode = false;
			startGame.setEnabled(true);
		});
		
		editModeMenu.addActionListener((ev) -> {
			if (this.terrain.inGame) {
				terrain.stop();
			}
			
			gameMode = false;
			editMode = true;
			startGame.setEnabled(false);
		});
		
		mode.add(editModeMenu);
		mode.add(gameModeMenu);
		menu.add(mode);
		this.setMenuBar(menu);
		
		
		Panel downPanel = new Panel();
		
		Label coins = new Label("Novcica: ");
		TextField numCoins = new TextField("10");
		Label points = new Label("Poena: ");
		numPoints = new Label("0");
		startGame.addActionListener((ev) -> {
			numPoints.setText("0");
			terrain.initialize(Integer.parseInt(numCoins.getText()));
			terrain.start();
			
			startGame.setEnabled(false);
		});
		
		downPanel.add(coins);
		downPanel.add(numCoins);
		downPanel.add(points);
		downPanel.add(numPoints);
		downPanel.add(startGame);
		
		this.add(downPanel, BorderLayout.SOUTH);
	}
	
	void setNumPoints(int points) {
		numPoints.setText(Integer.toString(points));
	}
	
	public static void main(String args[]) {
		Igra game = new Igra();
	}
}
