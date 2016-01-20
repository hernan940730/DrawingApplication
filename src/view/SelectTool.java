package view;

import java.awt.Point;
import java.awt.event.MouseEvent;

import controller.App;

public class SelectTool extends Tool {
	
	private static SelectTool instance = null;
	private static Mode mode;
	private static Point currentpt;
	private static Point newpt;
		
	protected static enum Mode{
		MOVE, SELECT, RE_SIZE
	}
	
	private SelectTool( ){
		super( );
	}
	
	protected static synchronized SelectTool getInstance( ){
		if( instance == null ){
			instance = new SelectTool();
		}
		return instance;
	}
	
	@Override
	public void mousePressed( MouseEvent e ) {
		super.mousePressed(e);
		switch( mode ){
			case SELECT:
				App.getInstance().putRubberBand( getP1() );
				break;
			case MOVE:
				App.getInstance().setFigureToMove( getP1() );
				break;
			case RE_SIZE:
				break;
			default:
				throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void mouseReleased( MouseEvent e ) {
		super.mouseReleased(e);
		switch( mode ){
			case SELECT:
				if( getP1().equals( getP2() ) ){
					App.getInstance().select( getP1() );
				}
				else{
					App.getInstance().select( getP1(), getP2() );
				}
				break;
			case MOVE:
				App.getInstance().moved( );
				break;
			case RE_SIZE:
				break;
			default:
				throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public void mouseDragged( final MouseEvent e ) {
		currentpt = getP2();
		super.mouseDragged( e );
		newpt = getP2();
		switch( mode ){
			case SELECT:
				App.getInstance().drawRubberBand( getP2() );
				break;
			case MOVE:
				App.getInstance().moveFigure( currentpt, newpt );
				break;
			case RE_SIZE:
				
				break;
			default:
				throw new UnsupportedOperationException();
		}
		
	}
	
	protected static void setMode( Mode mod ){
		mode = mod;
	}
}
