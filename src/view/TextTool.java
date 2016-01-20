package view;

import controller.App;

public class TextTool extends CreationTool {
	
	private static TextTool instance = null;
	
	private TextTool( ){
		super( );
	}
	
	protected static synchronized TextTool getInstance( ){
		if( instance == null ){
			instance = new TextTool();
		}
		return instance;
	}
	
	@Override
	protected void createFigure( ) {
		App.getInstance().addText( getP1(), getP2() );
	}
	
	@Override
	protected void drawFigure() {
		App.getInstance().drawText( getP1(), getP2() );
	}

}
