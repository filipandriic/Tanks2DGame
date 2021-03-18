package game;
import java.awt.*;

public class Novcic extends Figura {

	public Novcic(Polje field) {
		super(field);
	}

	@Override
	public void draw() {
		Graphics g = field.getGraphics();
		g.setColor(Color.YELLOW);
		g.fillOval(field.getWidth() / 4, field.getHeight() / 4, field.getWidth() / 2, field.getHeight() / 2);
	}

}
