package game;
import java.awt.*;

public class Trava extends Polje {

	private Color color;

	
	public Trava(Mreza terrain) {
		super(terrain);
		color = Color.GREEN;
	}

	@Override
	public boolean getPossibleFigure() {
		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, terrain.getWidth(), terrain.getHeight());
	}
}
