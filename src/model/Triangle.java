package model;

import java.awt.Graphics2D;

public class Triangle extends GeometricFigure implements IFigure {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Triangle( BoundBox bbox ) {
		super( bbox );
	}

	@Override
	protected void doPaint( Graphics2D g ) {
		g.drawLine(bbox.x, bbox.y + bbox.height, bbox.x + bbox.width, bbox.y + bbox.height);
		g.drawLine(bbox.x, bbox.y + bbox.height, bbox.x + bbox.width / 2, bbox.y);
		g.drawLine(bbox.x + bbox.width, bbox.y + bbox.height, bbox.x + bbox.width / 2, bbox.y);
	}
	
	@Override
	public boolean needsNormalize(){
		return false;
	}

	@Override
	public IFigure clone(){
		return new Triangle( new BoundBox( bbox ) );
	}

	@Override
	public void accept(IVisitorDrawing vd) {
		vd.visit( this );
		
	}
	
}
