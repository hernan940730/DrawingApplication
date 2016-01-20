package model;

import java.awt.Graphics2D;

public class Rectangle extends GeometricFigure implements IFigure{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rectangle( BoundBox bbox ){
		super( bbox );
	}

	@Override
	protected void doPaint( Graphics2D g ) {
		BoundBox bbox = getBoundBox();
		g.drawRect( bbox.x, bbox.y, bbox.width, bbox.height );
	}
	
	@Override
	public IFigure clone(){
		return new Rectangle( new BoundBox( bbox ) );
	}

	@Override
	public void accept(IVisitorDrawing vd) {
		vd.visit( this );
		
	}
}
