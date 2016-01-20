package view;

import controller.App;

public class EllipseTool extends CreationTool {
	
	private static EllipseTool instance = null;
	
	private EllipseTool( ){
		super( );
	}
	
	protected static synchronized EllipseTool getInstance( ){
		if( instance == null ){
			instance = new EllipseTool();
		}
		return instance;
	}
	
	@Override
	protected void createFigure( ) {
		App.getInstance().addEllipse( getP1(), getP2() );
	}

	@Override
	protected void drawFigure() {
		App.getInstance().drawEllipse( getP1(), getP2() );
	}
	
}
