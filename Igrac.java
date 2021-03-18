package game;
import java.awt.*;

public class Igrac extends Figura {

	public Igrac(Polje field) {
		super(field);
		
	}

	@Override
	public void draw() {
		Graphics g = field.getGraphics();
		g.setColor(Color.RED);
		g.drawLine(field.getWidth() / 2, 0, field.getWidth() / 2, field.getHeight());
		g.drawLine(0, field.getHeight() / 2, field.getWidth(), field.getHeight() / 2);
	}

}
