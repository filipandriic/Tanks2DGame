package game;
import java.awt.*;

public class Zid extends Polje {

	private Color color;
	
	public Zid(Mreza terrain) {
		super(terrain);
		color = Color.LIGHT_GRAY;
	}

	@Override
	public boolean getPossibleFigure() {
		return false;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, terrain.getWidth(), terrain.getHeight());
		
	}
}
