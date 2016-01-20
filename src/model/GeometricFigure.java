package model;

import java.awt.Graphics2D;

public abstract class GeometricFigure extends Figure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int lineWidth;
	
	protected GeometricFigure(BoundBox bbox) {
		super( bbox );
		lineWidth = 1;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
	
	@Override
	public abstract IFigure clone();

	@Override
	protected void doPaint( Graphics2D g ) {
		System.out.println("adsdad");
	}

	@Override
	public abstract void accept( IVisitorDrawing vd );

}
