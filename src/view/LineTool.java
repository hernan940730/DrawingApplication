package view;

import controller.App;

public class LineTool extends CreationTool {
	
	private static LineTool instance = null;
	
	private LineTool( ){
		super( );
	}
	
	protected static synchronized LineTool getInstance( ){
		if( instance == null ){
			instance = new LineTool();
		}
		return instance;
	}
	
	@Override
	protected void createFigure( ) {
		App.getInstance().addLine( getP1(), getP2() );
	}

	@Override
	protected void drawFigure() {
		App.getInstance().drawLine( getP1(), getP2() );
	}
}
