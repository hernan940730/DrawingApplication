package controller;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import view.ControlPoint;
import view.MainFrame;
import model.BoundBox;
import model.Drawing;
import model.Ellipse;
import model.IDrawingListener;
import model.IFigure;
import model.Line;
import model.Rectangle;
import model.Text;
import model.Triangle;

public class App{
	
	private static App application = null;
	private Drawing drawing;
	private MainFrame mainFrame;
	private BoundBox rubberBand;
	private Ellipse ellipse;
	private Rectangle rectangle;
	private Triangle triangle;
	private Line line;
	private Text text;
	
	private App(){
		drawing = new Drawing();
		rubberBand = new BoundBox( );
		mainFrame = new MainFrame( "Editor Gráfico Hernán" );
		drawing.addListener( mainFrame );
	}
	
	public synchronized static App getInstance(){
		if( application == null ){
			application = new App( );
		}
		return application;
	}

	public static void main( String ...args ){
		getInstance().run();
	}
	
	public void paint( Graphics2D g ){
		drawing.paint( g );
	}

	private void run() {
		mainFrame.setVisible( true );
	}
	
	public void addLine( Point p1, Point p2 ){
		Line figure = new Line( new BoundBox( p1, p2 ) );
		drawing.add( figure );
	}
	
	public void addEllipse( Point p1, Point p2 ){
		Ellipse figure = new Ellipse( new BoundBox( p1, p2 ) );
		drawing.add( figure );
	}
	
	public void addRectangle( Point p1, Point p2 ){
		Rectangle figure = new Rectangle( new BoundBox( p1, p2 ) );
		drawing.add( figure );
	}
	
	public void addTriangle( Point p1, Point p2 ){
		Triangle figure = new Triangle( new BoundBox( p1, p2 ) );
		drawing.add( figure );
	}

	public void addText( Point p1, Point p2 ){//TODO add text
		//Text figure = new Text( new BoundBox( p1, p2 ) );
		//drawing.add( figure );
	}
	
	public void putFigure(){
		BoundBox bbox = new BoundBox();
		ellipse = new Ellipse( bbox );
		rectangle = new Rectangle( bbox );
		line = new Line( bbox );
		triangle = new Triangle( bbox );
		text = new Text( bbox );
	}
	
	public void drawEllipse( Point p1, Point p2 ){
		
		IFigure figure = ellipse;
		
		Graphics2D g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();	
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		
		drawing.changeSize( new BoundBox( p1, p2 ), figure );
		
		g2d = ( Graphics2D )mainFrame.getCanvas().getGraphics();		
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		g2d.dispose();
		
	}
	
	public void drawRectangle( Point p1, Point p2 ){
		
		IFigure figure = rectangle;
		
		Graphics2D g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();	
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		
		drawing.changeSize( new BoundBox( p1, p2 ), figure );
		
		g2d = ( Graphics2D )mainFrame.getCanvas().getGraphics();		
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		g2d.dispose();
		
	}
	
	public void drawTriangle( Point p1, Point p2 ){
		
		IFigure figure = triangle;
		
		Graphics2D g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();	
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
//		
		drawing.changeSize( new BoundBox( p1, p2 ), figure );
//		
		g2d = ( Graphics2D )mainFrame.getCanvas().getGraphics();		
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		g2d.dispose();
		
	}
	
	public void drawLine( Point p1, Point p2 ){
		
		IFigure figure = line;
		
		Graphics2D g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();	
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		
		drawing.changeSize( new BoundBox( p1, p2 ), figure );
		
		g2d = ( Graphics2D )mainFrame.getCanvas().getGraphics();		
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		g2d.dispose();
		
	}
	
	public void drawText( Point p1, Point p2 ){
		
		IFigure figure = text;
		
		Graphics2D g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();	
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		
		drawing.changeSize( new BoundBox( p1, p2 ), figure );
		
		g2d = ( Graphics2D )mainFrame.getCanvas().getGraphics();		
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		figure.paint(g2d);
		g2d.dispose();
		
	}
	
	public void putRubberBand( Point p ){
		rubberBand.setBounds( p.x, p.y, 0, 0 );
	}
	
	public void drawRubberBand( Point p ){
		
		BoundBox bbox = rubberBand;
		Graphics2D g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();	
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		bbox.paint(g2d);
		
		rubberBand.setBounds( new BoundBox( rubberBand.x, rubberBand.y, p.x - rubberBand.x, p.y - rubberBand.y ) );
		
		g2d = (Graphics2D)mainFrame.getCanvas().getGraphics();		
		g2d.setXORMode( mainFrame.getCanvas().getBackground() );
		bbox.paint(g2d);
		g2d.dispose();

	}
	
	public void deselectAll(){
		drawing.deselectAll();
		repaint();
	}
	
	public void select( Point p ){
		drawing.select( p );
		repaint();
	}
	public void select( Point p1, Point p2 ){
		BoundBox bbox = new BoundBox( p1, p2 );
		bbox.normalize();
		drawing.select( bbox );
		repaint();
	}
	
	public IFigure selectedFigureAt( final Point pt ) {

		return drawing.selectedFigureAt( pt );
	}

	
	public void newDocument(){
		
		if ( drawing.isModified() ) {
			
			int r = JOptionPane.showConfirmDialog( mainFrame, 
					"Doc has been modified, do you want to save it before quitting?",
					"Save Drawing",
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE );
			if ( r == JOptionPane.CANCEL_OPTION ) {
				
				// noop
			}
			else if ( r == JOptionPane.YES_OPTION ) {
				
				if ( saveDocument() ) {
					drawing.clear();
				}
				else {
					
					// noop (user cancelled)
				}
			}
			else if ( r == JOptionPane.NO_OPTION ) {
				
				drawing.clear();
			}
		}
		else {
		
			drawing.clear();
		}
		repaint();
	}
	
	public boolean saveDocument(){

		boolean r = true;
		
		String name = drawing.getName(); 
		if ( name.isEmpty() ) {
			
			JFileChooser fc = new JFileChooser( System.getProperty( "user.home" ) );
			fc.showSaveDialog( mainFrame );
			
			File f = fc.getSelectedFile();
			if ( f != null ) {

				name = f.getAbsolutePath();
			}
		}
		
		if ( !name.isEmpty() ) {
			
			try {
				
				File f = new File( name );
				// TODO if ( f.canWrite() ) 
				ObjectOutputStream oos = new ObjectOutputStream(
											new FileOutputStream( f )
						);
				drawing.save( oos );
				oos.close();
				
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
		return r;
	}

	public boolean loadDocument(){
		JFileChooser fc = new JFileChooser( System.getProperty( "user.home" ) );
		fc.showOpenDialog( mainFrame );
		
		File f = fc.getSelectedFile();
		if ( f != null ) {
		
			try {
				
				ObjectInputStream ois = new ObjectInputStream(
											new FileInputStream( f )
						);
				drawing.load( ois );
				ois.close();
				
				repaint();
				
			} catch ( Exception e ) {
			
				
			}
		}
		else {
			
			// noop
		}
		return true;
	}
	
	public void quitDocument(){
		if ( drawing.isModified() ) {
			
			int r = JOptionPane.showConfirmDialog( mainFrame, 
					"Doc has been modified, do you want to save it before quitting?",
					"Save Drawing",
					JOptionPane.YES_NO_CANCEL_OPTION, 
					JOptionPane.QUESTION_MESSAGE );
			if ( r == JOptionPane.CANCEL_OPTION ) {
				
				// noop
			}
			else if ( r == JOptionPane.YES_OPTION ) {
				
				if ( saveDocument() ) {
					System.exit( 0 );
				}
				else {
					
					// noop (user cancelled)
				}
			}
			else if ( r == JOptionPane.NO_OPTION ) {
				
				System.exit( 0 );
			}
		}
		else {
			System.exit( 0 );
		}
	}
	
	public void addDrawingListener( final IDrawingListener listener ){
		drawing.addListener( listener );
	}
	
	public void removeDrawingListener( final IDrawingListener listener ){
		drawing.removeListener( listener );
	}
	
	public void group(){
		drawing.group( );
		repaint();
	}
	
	public void unGroup(){
		drawing.unGroup( );
		repaint();
	}
	
	public void setFigureToMove( Point p ){
		drawing.setFigureToMove( p );
	}
	
	public void moveFigure( Point currentpt, Point newpt ){
		int x = newpt.x - currentpt.x;
		int y = newpt.y - currentpt.y;
		drawing.moveFigure( x, y );
		repaint();
	}
	
	public void moved( ){
		drawing.moved( );
	}
	
	public ControlPoint controlPointAt( final Point pt ) {
		return drawing.controlPointAt( pt );
	}
	
	public void removeSelectedFigures( ){
		drawing.removeSelectedFigures( );
		repaint();
	}
	
	public boolean canUndo(){
		return drawing.canUndo();
	}
	
	public void undo(){
		drawing.undo();
		repaint();
	}
	
	public boolean canRedo(){
		return drawing.canRedo();
	}
	
	public void redo(){
		drawing.redo();
		repaint();
	}
	
	public void setStatusBar(String name, String posx, String posy, String width, String heigth, 
			String elements, String length){
		mainFrame.getStatusBar().setStatusBar(name, posx, posy, width, heigth, elements, length);
		repaint();
	}
	
	public void repaint(){
		mainFrame.refreshUndoRedo();
		mainFrame.repaint();
	}

}
