package model;

import java.awt.Graphics2D;

public class Line extends GeometricFigure implements IFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Line( BoundBox bbox ) {
		super( bbox );
	}

	@Override
	protected void doPaint( Graphics2D g ) {
		g.drawLine( getBoundBox().x, getBoundBox().y, getBoundBox().x + getBoundBox().width, getBoundBox().y + getBoundBox().height );
	}
	
	@Override
	public boolean needsNormalize(){
		return false;
	}

	@Override
	public IFigure clone(){
		return new Line( new BoundBox( bbox ) );
	}

	@Override
	public void accept(IVisitorDrawing vd) {
		vd.visit( this );
		
	}
	
}
