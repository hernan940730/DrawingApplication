package view;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import model.IFigure;
import view.SelectTool.Mode;


import controller.App;

public class Canvas extends JPanel {

	private static final long serialVersionUID = 1L;
	private Tool curTool;
	private Tool[] tools;
	protected Point mousept;
	protected static final int SELECT = 0;
	protected static final int LINE = 1;
	protected static final int RECTANGLE = 2;
	protected static final int ELLIPSE = 3;
	protected static final int TEXT = 4;
	protected static final int TRIANGLE = 5;
	protected static final int NUM_TOOLS = 6;
	
	public Canvas(final MainFrame mf) {
		super();
		mousept = new Point();	
		tools = new Tool[NUM_TOOLS];
		tools[SELECT] = SelectTool.getInstance();
		tools[LINE] = LineTool.getInstance();
		tools[RECTANGLE] = RectangleTool.getInstance();
		tools[ELLIPSE] = EllipseTool.getInstance();
		tools[TRIANGLE] = TriangleTool.getInstance();
		tools[TEXT] = TextTool.getInstance();
		
		curTool = tools[SELECT];
		

		addMouseListener( new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				curTool.mousePressed( e );
			}
		
			@Override
			public void mouseReleased( MouseEvent e) {
				curTool.mouseReleased( e );
			};

		} );
		addMouseMotionListener( new MouseAdapter() {
			
			@Override
			public void mouseMoved( MouseEvent e ) {
				setCursor( e.getPoint() );
				mousept = e.getPoint();
				mf.statusBar.setFigMouse( mousept.x + ", " + mousept.y );
				mf.statusBar.repaint();
			}
			
			@Override
			public void mouseDragged( MouseEvent e ) {
				curTool.mouseDragged( e );
				mousept = e.getPoint();
				mf.statusBar.setFigMouse( mousept.x + ", " + mousept.y );
				mf.statusBar.repaint();
			}			
		} );
	}
	
	@Override
	public void paint( Graphics g ){
		//TODO paint background
		super.paint( g );
		App.getInstance().paint( ( Graphics2D ) g );
	}
	
	protected void setCurTool( int type ){
		App.getInstance().deselectAll();
		curTool = tools[type];
	}
	
	protected void setCursor( final Point pt ) {
		
		ControlPoint cp = App.getInstance().controlPointAt( pt );
		if ( cp == null ) {
			
			IFigure f = App.getInstance().selectedFigureAt( pt );
			if ( f == null ) {
				SelectTool.setMode( Mode.SELECT );
				setCursor( Cursor.getDefaultCursor() );
			}
			else {
				SelectTool.setMode( Mode.MOVE );
				setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
			}
		} 
		else {
			SelectTool.setMode( Mode.RE_SIZE );
			setCursor( cp.getCursor() );
		}
	}
}
