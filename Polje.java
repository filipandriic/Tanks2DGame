package game;
import java.awt.*;
import java.awt.event.*;

public abstract class Polje extends Canvas {
	protected Mreza terrain;

	
	public Polje(Mreza terrain) {
		this.terrain = terrain;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent ev) {
				terrain.requestFocus();
				changeField();
			}
			
			@Override
			public void mouseClicked(MouseEvent ev) {
				terrain.requestFocus();
			}
		});
	}
	
	public Mreza getTerrain() {
		return this.terrain;
	}
	
	public int[] getPosition() {
		return terrain.getPosition(this);	
	}
	
	public Polje getField(int x, int y) {
		return terrain.getField(this, x, y);
	}
	
	public void changeField() {
		terrain.changeField(this);
	}
	
	public abstract boolean getPossibleFigure();

	
}
