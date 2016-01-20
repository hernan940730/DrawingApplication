package model;


import java.awt.Graphics2D;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;

import model.IDrawingListener.DrawingEvent;
import view.ControlPoint;

public class Drawing{
	
	private List<IDrawingListener> listeners;
	private LinkedList<IFigure> elements;
	private IVisitorDrawing visitor;
	private String name;
	private int version;
	
	private boolean modified;
	private boolean moved;
	private Pair prePos;
	private final int VERSION = 1;
	public static final int CP_AREA = 5;
	private IFigure selectedFigure;
	
	private UndoManager m_undoManager;
	private UndoableEditSupport m_undoSupport;

	
	public Drawing(){
		super();
        elements = new LinkedList<IFigure>();
        listeners = new LinkedList<IDrawingListener>();
        visitor = new StateVisitor();
        name = "";
        version = VERSION;
        moved = true;
        
        m_undoManager = new UndoManager();
		m_undoSupport = new UndoableEditSupport();
		m_undoSupport.addUndoableEditListener( new UndoAdapter() );

    }
	
	public void add( IFigure figure ){
		assert( figure != null );
		setModified( elements.add( figure ), new AddMemento( figure ));
	}
	
	public boolean remove( IFigure figure ){
		assert( figure != null );
		FigIndex fi = new FigIndex(elements.indexOf( figure ), figure );
		setModified( elements.remove( figure ), new RemoveMemento( fi ) );
		return true;
	}
	
	public boolean removeSelectedFigures( ){
		boolean remove = false;
		for( int i = elements.size() - 1; i >= 0; i-- ){
			IFigure figure = elements.get( i );
			if( figure.isSelected() ){
				remove = remove( figure ); 
			}
		}
		return remove;

	}
	
	public void pop( ){
		if( elements.size() != 0 ){
			IFigure figure = elements.get( elements.size() - 1 );
			elements.remove( figure );
			setModified( true, new RemoveMemento(new FigIndex(elements.size(), figure ) ) );
		}
	}
	
	public void changeSize( BoundBox bbox, IFigure figure ){
		BoundBox b = figure.getBoundBox();
		b.setBounds( bbox.x, bbox.y, bbox.width, bbox.height );
		if( figure.needsNormalize() ){
			b.normalize();
		}
	}
	
	public void addListener( IDrawingListener dl ){
		assert( dl != null );
		listeners.add(dl);
	}
	
	public void removeListener( IDrawingListener dl ){
		assert( dl != null );
		listeners.remove(dl);
	}
	
	public void save( ObjectOutputStream oos ) {

		try {
			oos.writeInt( version );
			oos.writeObject( elements );
			oos.writeObject( name );
			
			setModified( false, null );
			
			m_undoSupport = new UndoableEditSupport();
			m_undoManager = new UndoManager();
			m_undoSupport.addUndoableEditListener( new UndoAdapter() );
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void load( ObjectInputStream ois ) {
		
		try {
			version = ois.readInt();
			
			if ( version >= 1 ) {
			
				elements = (LinkedList<IFigure>)ois.readObject();
				name = (String)ois.readObject();
			}else{
				if(version >= 2){
					//...
				}
			}
		
			m_undoSupport = new UndoableEditSupport();
			m_undoManager = new UndoManager();
			m_undoSupport.addUndoableEditListener( new UndoAdapter() );
			
		} catch ( Exception e ) {

			e.printStackTrace();
		}
	}
	
	public void clear(){
		
		m_undoSupport = new UndoableEditSupport();
		m_undoManager = new UndoManager();
		m_undoSupport.addUndoableEditListener( new UndoAdapter() );
		
		elements.clear();
		setModified( false, null );
	}
	
	public void group( ){
		
		LinkedList<IFigure> group = new LinkedList<>();
		LinkedList<IFigure> newElements = new LinkedList<>();
		LinkedList<FigIndex> figIndexList = new LinkedList<FigIndex>();
		
		for(IFigure figure : elements){
			if( figure.isSelected() ){
				group.add( figure );
				figIndexList.add(new FigIndex(elements.indexOf(figure), figure));
			}else{
				newElements.add( figure );
			}
		}
		elements = newElements;
		if( !group.isEmpty() ){
			IFigure figure = new ComposeFigure( group );
			elements.add( figure );
			setModified( true, new GroupMemento( figIndexList, elements.size() - 1 ) );				
		}
		
	}
	
	public void unGroup( ){
		LinkedList<FigIndex> figIndexList = new LinkedList<>();
		
		for( int i = 0; i < elements.size(); i++){
			IFigure figure = elements.get(i);
			if( figure.isSelected() ){
				if( figure.getChildrens() != null ){
					LinkedList<IFigure> childrens = figure.getChildrens();
					int c = 0;
					for( IFigure children : childrens ){
						children.setSelected( true );
						figIndexList.add(new FigIndex( elements.indexOf( figure ) + c++, children ) );
					}
					elements.addAll( elements.indexOf( figure ) + 1, childrens );
					elements.remove( i );
					
					setModified( true, new UnGroupMemento( figIndexList ) );
					
				}
			}
		}
	}
	
	public void setFigureToMove( Point p ){
		selectedFigure = selectedFigureAt( p );
	}
	
	public void moveFigure( int x, int y ){	
		if( moved  && selectedFigure != null ){
			prePos = new Pair( selectedFigure.getBoundBox().x, selectedFigure.getBoundBox().y );
		}
		if( selectedFigure != null && (x != selectedFigure.getBoundBox().x || y != selectedFigure.getBoundBox().x )){
			selectedFigure.move( x, y );
			selectedFigure.accept( visitor );
			moved = false;
		}
	}
	
	public void moved( ){
		setModified( true, new MoveMemento( prePos, new Pair( selectedFigure.getBoundBox().x, selectedFigure.getBoundBox().y ), elements.indexOf( selectedFigure ) ));
		moved = true;
	}
	
	public void select( Point pt ){
		Iterator<IFigure> iterator = elements.descendingIterator();
		while( iterator.hasNext() ){
			IFigure figure = iterator.next();
			if( figure.contains( pt ) ){
				figure.setSelected( true );
				figure.accept(visitor);
				notifyListeners( DrawingEvent.SELECTED );
				break;
			}
		}
		
	}
	
	public void select( final BoundBox bbox ){
		for( IFigure figure : elements ){
			if( figure.contained( bbox ) ){
				figure.setSelected( true );
			}
		}
		notifyListeners( DrawingEvent.SELECTED );
	}
	
	public void deselectAll( ){
		for( IFigure tmp : elements ){
			tmp.setSelected( false );
		}
	}
	
	public IFigure selectedFigureAt( final Point pt ) {

		IFigure r = null;
		
		for ( IFigure f : elements ) {
			
			if ( f.isSelected() && f.contains( pt ) ) {
				
				r = f;
				break;
			}
		}
		return r;
	}
	
	public ControlPoint controlPointAt( final Point pt ) {

		assert pt != null;
		
		ControlPoint cp = null;
		BoundBox bbox = new BoundBox( pt.x - CP_AREA, pt.y - CP_AREA,  2 * CP_AREA, 2 * CP_AREA );
		
		for ( IFigure f : elements ) {
			
			if ( f.isSelected() ) {
				
				cp = f.ctrlPointInBoundBox( bbox );
				if ( cp != null ) {
					break;
				}
			}
		}
		return cp;
	}
	
	private void notifyListeners( DrawingEvent de ) {
		for( IDrawingListener tmp : listeners  ){
			tmp.processDrawingEvent( de );
		}
	}
	
	public void paint( Graphics2D g ){
		for( IFigure tmp : elements ){
			tmp.paint( ( Graphics2D ) g );
		}
	}
	
	public List<IFigure> getList(){
		return elements;
	}
	
	public String getName(){
		return name;
	}

	public void setName( String name ){
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified( boolean modified, UndoableEdit edit ) {
		if( modified && edit != null ){
			m_undoSupport.postEdit( edit );
		}
		
		if( !this.modified && modified ){
			notifyListeners( DrawingEvent.MODIFIED );
		}else{
			if( this.modified && !modified ){
				notifyListeners( DrawingEvent.SAVED );
			}
		}
		this.modified = modified;
		notifyListeners( DrawingEvent.UNDO );
	}
	
	public boolean canUndo(){
		return m_undoManager.canUndo();
	}
	
	public void undo(){
		for( IFigure element : elements ){
			element.setSelected( false );
		}
		
		if( canUndo() ){
			m_undoManager.undo();
			if( !canUndo() ){
				setModified( false, null );
			}
			notifyListeners( DrawingEvent.UNDO );
		}
	}
	
	public boolean canRedo(){
		return m_undoManager.canRedo();
	}
	
	public void redo( ){
		for( IFigure element : elements ){
			element.setSelected( false );
		}
		
		if( canRedo() ){
			m_undoManager.redo();
			setModified( true, null );
			notifyListeners( DrawingEvent.UNDO );
		}
	}
	
	
	private class RemoveMemento extends AbstractUndoableEdit{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private FigIndex state;
		
		
		private RemoveMemento( FigIndex state ){
			this.state = state; 
		}
		
		public void undo() throws CannotUndoException{
			elements.add(state.index, state.figure);
		}

		public void redo() throws CannotRedoException
		{
			elements.remove( state.index );
		}

		public boolean canUndo()
		{
			return true;
		}

		public boolean canRedo()
		{
			return true;
		}

		public String getPresentationName()
		{
			return "Remove";
		}
	}
	
	
	
	private class AddMemento extends AbstractUndoableEdit{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private IFigure state;
		
		private AddMemento( IFigure state ){
			this.state = state.clone(); 
		}
		
		public void undo() throws CannotUndoException{
			elements.remove(elements.size() - 1);
		}

		public void redo() throws CannotRedoException
		{
			elements.add( state );
		}

		public boolean canUndo()
		{
			return true;
		}

		public boolean canRedo()
		{
			return true;
		}

		public String getPresentationName()
		{
			return "Add";
		}
		
	}
	
	private class UnGroupMemento extends AbstractUndoableEdit{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private LinkedList<FigIndex> state;
		
		
		private UnGroupMemento( LinkedList<FigIndex> state ){
			this.state = state; 
		}
		
		public void undo() throws CannotUndoException{
			int c = 0;
			for( IFigure element : elements ){
				if( state.get(c).index == elements.indexOf( element ) ){
					element.setSelected( true );
					c++;
					if( c == state.size()){
						break;
					}
				}
			}
			
			LinkedList<IFigure> group = new LinkedList<>();
			LinkedList<IFigure> newElements = new LinkedList<>();
			
			for(IFigure figure : elements){
				if( figure.isSelected() ){
					group.add( figure );
				}else{
					newElements.add( figure );
				}
			}
			elements = newElements;
			if( !group.isEmpty() ){
				IFigure figure = new ComposeFigure( group );
				elements.add( state.get(0).index, figure );				
			}
		}

		public void redo() throws CannotRedoException{
			LinkedList<IFigure> childrens = new LinkedList<IFigure>();
			for( FigIndex fig : state ){
				IFigure figure = fig.figure.clone();
				figure.setSelected( true );
				childrens.add( figure );
			}
			elements.remove( state.get( 0 ).index );
			elements.addAll( state.get(0).index, childrens );
		}

		public boolean canUndo()
		{
			return true;
		}

		public boolean canRedo()
		{
			return true;
		}

		public String getPresentationName()
		{
			return "Ungroup";
		}
		
	}

	private class GroupMemento extends AbstractUndoableEdit{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private LinkedList<FigIndex> state;
		private int index;
		
		private GroupMemento( LinkedList<FigIndex> state, int index ){
			this.state = state; 
			this.index = index;
		}
		
		public void undo() throws CannotUndoException{
			elements.remove( index );
			for( FigIndex fi: state ){
				IFigure figure = fi.figure.clone();
				figure.setSelected( true );
				elements.add( fi.index, figure );
				
			}
		}
	
		public void redo() throws CannotRedoException{
			int c = 0;
			for( IFigure element : elements ){
				if( state.get(c).index == elements.indexOf( element ) ){
					element.setSelected( true );
					c++;
					if( c == state.size() ){
						break;
					}
				}
			}
			
			LinkedList<IFigure> group = new LinkedList<>();
			LinkedList<IFigure> newElements = new LinkedList<>();
			
			for( IFigure figure : elements ){
				if( figure.isSelected() ){
					group.add( figure );
				}else{
					newElements.add( figure );
				}
			}
			elements = newElements;
			if( !group.isEmpty() ){
				IFigure figure = new ComposeFigure( group );
				elements.add( figure );				
			}	
		}
	
		public boolean canUndo()
		{
			return true;
		}
	
		public boolean canRedo()
		{
			return true;
		}
	
		public String getPresentationName()
		{
			return "Group";
		}
		
	}
	
	private class MoveMemento extends AbstractUndoableEdit{
		
		/**
		 * 
		 */
		
		private static final long serialVersionUID = 1L;
		
		private Pair mov1;
		private Pair mov2;
		private int index;
		
		
		private MoveMemento( Pair mov1, Pair mov2, int index ){
			this.mov1 = mov1;
			this.mov2 = mov2;
			this.index = index;
		}
		
		public void undo( ) throws CannotUndoException{
			IFigure figure = elements.get( index ); 
			figure.move( mov1.x - figure.getBoundBox().x, mov1.y - figure.getBoundBox().y );
		}
	
		public void redo( ) throws CannotRedoException{
			IFigure figure = elements.get( index ); 
			figure.move( mov2.x - figure.getBoundBox().x, mov2.y - figure.getBoundBox().y );
		}
	
		public boolean canUndo( ){
			return true;
		}
	
		public boolean canRedo( ){
			return true;
		}
	
		public String getPresentationName(){
			return "Move";
		}
		
	}
	
	private class FigIndex{
		
		private int index;
		private IFigure figure;
		
		private FigIndex( int index, IFigure figure ){
			this.index = index;
			this.figure = figure.clone();
		}
	}
	
	private class Pair{
		private int x;
		private int y;
		Pair( int x, int y ){
			this.x = x;
			this.y = y;
		}
	}
	
	
	private class UndoAdapter implements UndoableEditListener
	{
		public void undoableEditHappened( UndoableEditEvent evt )
		{
			UndoableEdit edit = evt.getEdit();
			m_undoManager.addEdit( edit );
		}
	}
	
}

