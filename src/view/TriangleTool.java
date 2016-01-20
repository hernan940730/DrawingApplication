package view;

import controller.App;

public class TriangleTool extends CreationTool {
	
	private static TriangleTool instance = null;
	
	private TriangleTool( ){
		super( );
	}
	
	protected static synchronized TriangleTool getInstance( ){
		if( instance == null ){
			instance = new TriangleTool();
		}
		return instance;
	}
	
	@Override
	protected void createFigure( ) {
		App.getInstance().addTriangle( getP1(), getP2() );
	}

	@Override
	protected void drawFigure() {
		App.getInstance().drawTriangle( getP1(), getP2() );
	}
	
}
