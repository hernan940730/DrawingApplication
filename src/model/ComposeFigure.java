package model;

import java.awt.Graphics2D;
import java.util.LinkedList;


public class ComposeFigure extends Figure implements IFigure {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<IFigure> childrens;
	
	public ComposeFigure( final LinkedList<IFigure> childrens ) {
		super( childrens.get(0).getBoundBox().normalized() );
		this.childrens = childrens;
		
		for( IFigure figure : this.childrens ){
			figure.setSelected( false );
			bbox = bbox.union( figure.getBoundBox().normalized() );
		}
		setSelected( true );
	}

	@Override
	protected void doPaint( Graphics2D g ) {
		for( IFigure tmp : childrens ){
			tmp.paint( g );
		}
	}
	
	@Override
	public void addFigure( IFigure figure ){
		assert( figure != null && figure != this );
		childrens.add( figure );
	}
	
	@Override
	public void removeFigure( IFigure figure ){
		childrens.remove( figure );
	}
	
	@Override
	public LinkedList<IFigure> getChildrens() {
		return childrens;
	}
	
	@Override
	public void move( int x, int y ) {
		super.move( x, y );
		for( IFigure children: childrens ){		
			children.move( x, y );
		}
	}
	
	@Override
	public IFigure clone( ){
		LinkedList<IFigure> childrens = new LinkedList<IFigure>();
		for( IFigure children : this.childrens ){
			childrens.add( children.clone() );
		}
		ComposeFigure clone = new ComposeFigure( childrens );
		return clone;
	}

	@Override
	public void accept(IVisitorDrawing vd) {
		vd.visit(this);
	}
}
