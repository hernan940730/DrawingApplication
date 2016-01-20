package model;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;

import view.ControlPoint;

public interface IFigure extends Serializable{
	
	void paint( Graphics2D g );
	boolean contains( Point pt );
	boolean contained( BoundBox bb );
	void setSelected( boolean b );
	boolean isSelected();
	BoundBox getBoundBox( );
	boolean needsNormalize();
	LinkedList<IFigure> getChildrens();
	ControlPoint ctrlPointInBoundBox( final BoundBox bbox );
	void move( int x, int y );
	IFigure clone();
	void accept( IVisitorDrawing visitor );
}
