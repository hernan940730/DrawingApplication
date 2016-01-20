package view;

import java.awt.event.MouseEvent;

import controller.App;

public abstract class CreationTool extends Tool {

	protected abstract void createFigure();
	protected abstract void drawFigure();
	@Override
	public void mouseDragged( MouseEvent e ) {
		super.mouseDragged( e );	
		drawFigure( );
	};
	
	@Override
	public void mousePressed( MouseEvent e ) {
		super.mousePressed( e );
		App.getInstance().putFigure();
	}
	
	@Override
	public void mouseReleased( MouseEvent e ) {
		super.mouseReleased( e );
		if( !getP1().equals( getP2() ) ){
			createFigure();
		}
	}

}
