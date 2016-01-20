package model;


public interface IDrawingListener {
	
	public static enum DrawingEvent {
		MODIFIED, SAVED, SELECTED, UNDO
	}
	public void processDrawingEvent( DrawingEvent de ); 
}
