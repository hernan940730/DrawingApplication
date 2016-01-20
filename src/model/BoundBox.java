package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

public class BoundBox extends Rectangle{

	private static final long serialVersionUID = 1L;
	private static final Color COLOR = new Color( 150, 150, 250 );
	private static final BasicStroke DASHED = new BasicStroke(
			1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{ 4.0f, 4.0f }, 0.0f );

	public BoundBox( ){
		super(0, 0, 0, 0);
	}
	
	public BoundBox( int x, int y, int width, int height ){
		super(x, y, width, height);
	}
	
	public BoundBox( Point pt1, Point pt2 ) {
		super( pt1.x, pt1.y, pt2.x - pt1.x, pt2.y - pt1.y );
	}
	
	public BoundBox( final BoundBox bb ) {
		super(bb.x, bb.y, bb.width, bb.height);
	}
	
	public BoundBox normalize(){
		if( width < 0 ){
			x += width;
			width *= -1; 
		}
		if( height < 0 ){
			y += height;
			height *= -1; 
		}
		return this;
	}
	
	public BoundBox normalized(){
		return ( isNormalized() ? this : new BoundBox( this ).normalize() );
	}
	
	public boolean isNormalized(){
		return( width >= 0 && height >= 0 );
	}
	public BoundBox union( BoundBox bbox ){
		Rectangle r = super.union( bbox );
		BoundBox b = new BoundBox( r.x, r.y, r.width, r.height );
		return b;
	}
	
	public void paint( Graphics2D g ){
		Stroke stroke = g.getStroke();
		BoundBox bbox = normalized();
		
		g.setStroke( DASHED );
		g.setColor( COLOR );
		g.drawRect( bbox.x, bbox.y, bbox.width, bbox.height );

		// recover stroke
		g.setStroke( stroke );
	}	
}
