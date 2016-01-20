package view;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.App;

public abstract class Tool extends MouseAdapter{
	
	private Point p1;
	private Point p2;
	
	protected Tool( ){
		super( );
	}
	
	@Override
	public void mousePressed( MouseEvent e ) {
		p1 = e.getPoint();
		p2 = e.getPoint();
		if( App.getInstance().selectedFigureAt( p1 ) == null ){
			App.getInstance().deselectAll();
		}
	}
	
	@Override
	public void mouseReleased( MouseEvent e ) {
		p2 = e.getPoint();
	}
	
	@Override
	public void mouseDragged( MouseEvent e ) {
		p2 = e.getPoint();
	};

	protected Point getP1() {
		return p1;
	}

	protected Point getP2() {
		return p2;
	}
	
}
