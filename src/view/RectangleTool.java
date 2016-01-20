package view;

import controller.App;

public class RectangleTool extends CreationTool {
	
	private static RectangleTool instance = null;
	
	private RectangleTool( ){
		super( );
	}
	
	protected static synchronized RectangleTool getInstance( ){
		if( instance == null ){
			instance = new RectangleTool();
		}
		return instance;
	}
	
	@Override
	protected void createFigure( ) {
		App.getInstance().addRectangle( getP1(), getP2() );
	}

	@Override
	protected void drawFigure() {
		App.getInstance().drawRectangle( getP1(), getP2() );
	}
}
