package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;

import view.ControlPoint;
import view.ControlPoint.Cardinal;

public abstract class Figure implements IFigure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean selected;
	protected BoundBox bbox;
	protected Color color = Color.BLACK;
	private static final int NUM_CPOINTS = Cardinal.values().length;
	protected ControlPoint[] ctrlPoints;

	protected abstract void doPaint( Graphics2D g );
	
	protected Figure( BoundBox bbox ){
		super();
		this.bbox = bbox;
		if( bbox != null ){
			if( needsNormalize() ){
				this.bbox.normalize();
			}
		}
		buildControlPoints();
	}
	
	@Override
	public final void paint( Graphics2D g ) {
		//TODO template method
		//1. paint children
		//2. paint itself
		//3. If selected paint boundbox
		
		g.setColor( color );
		
		doPaint( g );
		
		if( selected ){
			paintBoundBox( g );
			paintControlPoints( g );
		}
		
	}
	
	private void paintBoundBox( Graphics2D g ){
		if( bbox != null ){
			bbox.paint( g );
		}
		
	}
	
	@Override
	public boolean contains( Point p ){
		BoundBox bbox = this.bbox.normalized();
		return bbox.contains( p );
	}

	@Override
	public boolean contained( BoundBox bb ) {
		
		return bb.normalized().contains( bbox.normalized() );
	}

	@Override
	public boolean isSelected() {
		return selected;
	}
	
	public void addFigure( IFigure figure ){
		
	}
	
	public void removeFigure( IFigure figure ){
		
	}

	@Override
	public void setSelected( boolean selected ) {
		this.selected = selected;
	}
	
	public boolean needsNormalize(){
		return true;
	}
	
	protected void buildControlPoints() {
		
		ctrlPoints = new ControlPoint[ NUM_CPOINTS ];
		Cardinal[] ca = Cardinal.values();
		
		for ( int i = 0; i < NUM_CPOINTS; i++ ) {
			
			ctrlPoints[ i ] = new ControlPoint( ca[ i ] );
		}
	}
	
	private void paintControlPoints( final Graphics2D g ) {

		for ( int i = 0; i < NUM_CPOINTS; i++ ) {

			ctrlPoints[ i ].paint( g, bbox.normalized() );
		}
	}
	
	public ControlPoint ctrlPointInBoundBox( final BoundBox bb ) {
		
		assert bb != null;
		assert bb.isNormalized();
	
		ControlPoint cp = null;
		
		for ( int i = 0; i < NUM_CPOINTS; i++ ) {

			Point pt = ctrlPoints[ i ].getPosition( bbox.normalized() );
			if ( bb.contains( pt ) ) {
				
				cp = ctrlPoints[ i ];
				break;
			}
		}
		
		return cp;
	}
	
	@Override
	public BoundBox getBoundBox(){
		return bbox;
	}
	
	@Override
	public LinkedList<IFigure> getChildrens(){
		return null;
	}
	
	@Override
	public void move( int x, int y ) {
		bbox.setLocation( bbox.x + x, bbox.y + y );
	}
	
	@Override
	public abstract IFigure clone();
	
	public abstract void accept( IVisitorDrawing vd );

}
