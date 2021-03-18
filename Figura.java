package game;

public abstract class Figura {
	protected Polje field;
	
	public Figura(Polje field) {
		this.field = field;
	}
	
	public void moveFigure(Polje p) {
		if (p instanceof Trava) {
			field = p;
		}
	}
	
	public boolean sameField(Figura f) {
		return (this.field.getPosition()[0] == f.field.getPosition()[0] && this.field.getPosition()[1] == f.field.getPosition()[1]);
	}
	
	public abstract void draw();
}
